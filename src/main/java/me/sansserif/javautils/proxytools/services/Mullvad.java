package me.sansserif.javautils.proxytools.services;

import me.sansserif.javautils.proxytools.ServerService;
import me.sansserif.javautils.proxytools.Server;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public record Mullvad() implements ServerService {
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
                                .url("https://api.mullvad.net/www/relays/wireguard/")
                                .get()
                                .build()
                );

        JSONArray serversJson;
        try (Response response = call.execute()) {
            if (!response.isSuccessful() || response.body() == null) throw new Exception();
            serversJson = new JSONArray(response.body().string());
        } catch (Exception err) {
            throw new Exception("Failed to retrieve servers.");
        }

        Set<Server> servers = new HashSet<>();
        for (int serverNo = 0; serverNo < serversJson.length(); serverNo++) {
            JSONObject serverJson = serversJson.getJSONObject(serverNo);

            servers.add(new Server(
                    serverJson.getString("socks_name"),
                    serverJson.getInt("socks_port"),
                    serverJson.getString("country_code"),
                    serverJson.getString("city_name")
            ));
        }
        return servers;
    }
}
