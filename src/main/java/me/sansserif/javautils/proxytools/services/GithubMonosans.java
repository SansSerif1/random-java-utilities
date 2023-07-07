package me.sansserif.javautils.proxytools.services;

import me.sansserif.javautils.proxytools.ServerManager;
import me.sansserif.javautils.proxytools.ServerService;
import me.sansserif.javautils.proxytools.Server;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record GithubMonosans() implements ServerService {
    @Override
    public Set<Server> getServers() throws Exception {
        Call call = new OkHttpClient.Builder()
                .callTimeout(Duration.ofMinutes(1))
                .connectTimeout(Duration.ofSeconds(15))
                .readTimeout(Duration.ofSeconds(15))
                .writeTimeout(Duration.ofSeconds(15))
                .build()
                .newCall(
                        new Request.Builder()
                                .url("https://raw.githubusercontent.com/monosans/proxy-list/main/proxies_geolocation/socks5.txt")
                                .get()
                                .build()
                );

        String serversTxt;
        try (Response response = call.execute()) {
            if (!response.isSuccessful() || response.body() == null) throw new Exception();
            serversTxt = response.body().string();
        } catch (Exception err) {
            throw new Exception("Failed to retrieve servers.");
        }

        Matcher matcher = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5}\\|.+?\\|.+?\\|.+)").matcher(serversTxt);
        HashMap<String, String> reverseCountryList = new HashMap<>();
        ServerManager.getCountries().forEach((code, name) -> reverseCountryList.put(name, code));

        Set<Server> servers = new HashSet<>();
        while (matcher.find()) {
            String match = matcher.group().trim(),
                    address = match.substring(0, match.indexOf("|")),
                    geolocation = match.substring(match.indexOf("|") + 1);

            servers.add(new Server(
                    address.substring(0, match.indexOf(":")),
                    Integer.parseInt(address.substring(match.indexOf(":") + 1)),
                    reverseCountryList.get(geolocation.substring(0, geolocation.indexOf("|"))),
                    geolocation.substring(geolocation.lastIndexOf("|") + 1)
            ));
        }

        return servers;
    }
}
