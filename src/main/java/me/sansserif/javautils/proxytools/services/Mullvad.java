package me.sansserif.javautils.proxytools.services;

import me.sansserif.javautils.proxytools.Server;
import me.sansserif.javautils.proxytools.ServerService;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Mullvad implements ServerService {
    @Override
    public Set<Server> getServers() throws ServerLoadException {
        String raw = ServerService.getValidResponse(ServerService.buildCall(new Request.Builder().url("https://api.mullvad.net/www/relays/wireguard/").get().build()));
        JSONArray serversJson;
        try {
            assert raw != null;
            serversJson = new JSONArray(raw);
        } catch (Exception err) {
            throw new ServerLoadException();
        }

        Set<Server> servers = new HashSet<>();
        for (int serverNo = 0; serverNo < serversJson.length(); serverNo++) {
            JSONObject serverData = serversJson.getJSONObject(serverNo);

            servers.add(new Server(serverData.getString("socks_name"), serverData.getInt("socks_port"), serverData.getString("country_code"), serverData.getString("city_name")));
        }
        return servers;
    }
}
