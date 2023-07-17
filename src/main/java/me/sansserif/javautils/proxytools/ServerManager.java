package me.sansserif.javautils.proxytools;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.Proxy;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerManager {
    private final Set<ServerService> services;
    private final Set<Server> servers = new HashSet<>();

    public ServerManager(ServerService... allowedServices) {
        services = Set.of(allowedServices);
    }

    public Server getRandomServer(@NotNull String countryCode, @NotNull String city) throws IllegalStateException {
        List<Server> servers = getServers(countryCode, city);
        return servers.get(ThreadLocalRandom.current().nextInt(servers.size()));
    }

    public List<Server> getServers(@NotNull String countryCode, @NotNull String city) throws IllegalStateException {
        if (servers.isEmpty()) throw new IllegalStateException("Not loaded.");
        return servers.stream().filter(server -> countryCode.isEmpty() || server.countryCode().contains(countryCode)).filter(server -> city.isEmpty() || server.city().contains(city)).toList();
    }

    public int load(boolean reset) {
        if (reset) reset();
        return load();
    }

    public int load() {
        AtomicInteger failed = new AtomicInteger();

        services.forEach(service -> {
            try {
                servers.addAll(service.getServers());
            } catch (Exception err) {
                failed.incrementAndGet();
            }
        });
        return failed.get();
    }

    public void reset() {
        servers.clear();
    }

    public static HashMap<String, String> getCountriesByCode() {
        HashMap<String, String> countries = new HashMap<>();
        for (String countryCode : Locale.getISOCountries())
            countries.put(countryCode, new Locale("", countryCode).getDisplayCountry());
        return countries;
    }

    public static HashMap<String, String> getCountriesByName() {
        HashMap<String, String> countries = new HashMap<>();
        for (String countryCode : Locale.getISOCountries())
            countries.put(new Locale("", countryCode).getDisplayCountry(), countryCode);
        return countries;
    }

    public static ConnectionInfo getConnectionInfo(Proxy proxy) throws Exception {
        Call call = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(15))
                .proxy(proxy)
                .build()
                .newCall(new Request.Builder().url("https://am.i.mullvad.net/json").get().build());
        String raw = ServerService.getValidResponse(call);
        JSONObject connectionInfoJson;
        try {
            assert raw != null;
            connectionInfoJson = new JSONObject(raw);
        } catch (Exception err) {
            throw new Exception("Failure fetching info.");
        }
        return new ConnectionInfo(connectionInfoJson.getString("country"), connectionInfoJson.isNull("city") ? null : connectionInfoJson.getString("city"), connectionInfoJson.getString("ip"), connectionInfoJson.getFloat("latitude"), connectionInfoJson.getFloat("longitude"), connectionInfoJson.getString("organization"), connectionInfoJson.getJSONObject("blacklisted").getBoolean("blacklisted"));
    }
}
