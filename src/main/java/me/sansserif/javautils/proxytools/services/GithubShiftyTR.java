package me.sansserif.javautils.proxytools.services;

import me.sansserif.javautils.proxytools.Server;
import me.sansserif.javautils.proxytools.ServerService;
import okhttp3.Request;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubShiftyTR implements ServerService {
    @Override
    public Set<Server> getServers() throws ServerLoadException {
        String raw = ServerService.getValidResponse(ServerService.buildCall(new Request.Builder().url("https://raw.githubusercontent.com/shiftytr/proxy-list/master/socks5.txt").get().build()));
        try {
            assert raw != null;
        } catch (Exception err) {
            throw new ServerLoadException();
        }

        // matcher just for IPs (v4), countryCodes and cities not supplied
        Matcher matcher = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5})").matcher(raw);

        Set<Server> servers = new HashSet<>();
        while (matcher.find()) {
            String match = matcher.group().trim();

            servers.add(new Server(match.substring(0, match.indexOf(":")), Integer.parseInt(match.substring(match.indexOf(":") + 1)), null, null));
        }

        return servers;
    }
}
