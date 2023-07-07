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

public record GithubJetkai() implements ServerService {
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
                                .url("https://raw.githubusercontent.com/jetkai/proxy-list/main/online-proxies/json/proxies.json")
                                .get()
                                .build()
                );

        JSONArray serversJson;
        try (Response response = call.execute()) {
            if (!response.isSuccessful() || response.body() == null) throw new Exception();
            serversJson = new JSONObject(response.body().string()).getJSONArray("socks5");
        } catch (Exception err) {
            throw new Exception("Failed to retrieve servers.");
        }

        Set<Server> servers = new HashSet<>();
        for (int serverNo = 0; serverNo < serversJson.length(); serverNo++) {
            String address = serversJson.getString(serverNo).trim();

            servers.add(new Server(
                    address.substring(0, address.indexOf(":")),
                    Integer.parseInt(address.substring(address.indexOf(":") + 1)),
                    null,
                    null
            ));
        }
        return servers;
    }
}
