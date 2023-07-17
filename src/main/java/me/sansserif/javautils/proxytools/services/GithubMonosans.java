package me.sansserif.javautils.proxytools.services;

import me.sansserif.javautils.proxytools.Server;
import me.sansserif.javautils.proxytools.ServerManager;
import me.sansserif.javautils.proxytools.ServerService;
import okhttp3.Request;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubMonosans implements ServerService {
    @Override
    public Set<Server> getServers() throws ServerLoadException {
        String raw = ServerService.getValidResponse(ServerService.buildCall(new Request.Builder().url("https://raw.githubusercontent.com/monosans/proxy-list/main/proxies_geolocation/socks5.txt").get().build()));
        try {
            assert raw != null;
        } catch (Exception err) {
            throw new ServerLoadException();
        }

        // matcher for IPs (v4), countries by name and cities by name
        Matcher matcher = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5}\\|.+?\\|.+?\\|.+)").matcher(raw);
        HashMap<String, String> countries = ServerManager.getCountriesByName();

        Set<Server> servers = new HashSet<>();
        while (matcher.find()) {
            String match = matcher.group().trim(), address = match.substring(0, match.indexOf("|")), geolocation = match.substring(match.indexOf("|") + 1);

            servers.add(new Server(address.substring(0, match.indexOf(":")), Integer.parseInt(address.substring(match.indexOf(":") + 1)), countries.get(geolocation.substring(0, geolocation.indexOf("|"))), geolocation.substring(geolocation.lastIndexOf("|") + 1)));
        }

        return servers;
    }
}
