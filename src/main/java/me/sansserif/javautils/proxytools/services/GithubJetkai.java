package me.sansserif.javautils.proxytools.services;

import me.sansserif.javautils.proxytools.Server;
import me.sansserif.javautils.proxytools.ServerService;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class GithubJetkai implements ServerService {
    @Override
    public Set<Server> getServers() throws ServerLoadException {
        String raw = ServerService.getValidResponse(ServerService.buildCall(new Request.Builder().url("https://raw.githubusercontent.com/jetkai/proxy-list/main/online-proxies/json/proxies.json").get().build()));
        JSONArray serversJson;
        try {
            assert raw != null;
            serversJson = new JSONObject(raw).getJSONArray("socks5");
        } catch (Exception err) {
            throw new ServerLoadException();
        }

        Set<Server> servers = new HashSet<>();
        for (int serverNo = 0; serverNo < serversJson.length(); serverNo++) {
            String address = serversJson.getString(serverNo).trim();
            servers.add(new Server(address.substring(0, address.indexOf(":")), Integer.parseInt(address.substring(address.indexOf(":") + 1)), null, null));
        }
        return servers;
    }
}
