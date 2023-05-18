package me.sansserif.javautils;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class MullvadProxy {
    private List<Server> servers = new ArrayList<>();

    public MullvadProxy() throws MullvadProxyException {
        HttpsURLConnection getData = null;
        try {
            getData = (HttpsURLConnection) new URL("https://api.mullvad.net/www/relays/wireguard/").openConnection();
        } catch (Exception err) {
            throw new MullvadProxyException(MullvadProxyException.Type.INVALID_URL);
        }
        getData.setReadTimeout(60000);
        getData.setConnectTimeout(60000);
        try {
            getData.connect();
            if (getData.getResponseCode() != 200) throw new Exception();
        } catch (Exception err) {
            throw new MullvadProxyException(MullvadProxyException.Type.COULD_NOT_FETCH);
        }

        JSONArray data;
        try {
            data = new JSONArray(IOUtils.toString(getData.getInputStream(), StandardCharsets.UTF_8));
        } catch (Exception err) {
            throw new MullvadProxyException(MullvadProxyException.Type.INVALID_JSON);
        }

        for (int i = 0; i < data.length(); i++) {
            JSONObject current = data.getJSONObject(i);
            if (current.has("active") && current.getBoolean("active") && current.has("country_name") && current.has("owned") && current.has("socks_name"))
                servers.add(new Server(current.getString("country_name"), current.getBoolean("owned"), current.getString("socks_name")));
        }
    }
    public List<Proxy> getProxies(Optional<String> country_name, Optional<Boolean> owned) {
        if (servers.isEmpty()) return null;
        return servers.stream()
                .filter(server -> country_name.isEmpty() || country_name.get().equalsIgnoreCase(server.getCountry()))
                .filter(server -> owned.isEmpty() || owned.get().equals(server.isOwned()))
                .map(server -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(server.getSocksUrl(), 1080)))
                .toList();
    }
    public Proxy getRandomProxy(Optional<String> country_name, Optional<Boolean> owned) {
        if (servers.isEmpty()) return null;
        List<Proxy> proxies = getProxies(country_name, owned);
        return proxies.get(ThreadLocalRandom.current().nextInt(0, proxies.size()));
    }
    public List<String> getCountries() {
        return servers.stream().map(Server::getCountry).distinct().sorted().toList();
    }

    public static class Server {
        private final String country, socksUrl;
        private final boolean owned;

        private Server(String country, boolean owned, String socksUrl) {
            this.country = country;
            this.socksUrl = socksUrl;
            this.owned = owned;
        }

        public String getCountry() {
            return country;
        }

        public boolean isOwned() {
            return owned;
        }

        public String getSocksUrl() {
            return socksUrl;
        }
    }

    public static class MullvadProxyException extends Exception {
        public enum Type {
            INVALID_URL,
            COULD_NOT_FETCH,
            INVALID_JSON
        }

        private final Type type;

        public MullvadProxyException(Type type) {
            this.type = type;
        }

        public Type getType() {
            return this.type;
        }

        public String getError() {
            switch (this.type) {
                case INVALID_URL -> {
                    return "Impossible happened. Another universe perhaps?";
                }
                case COULD_NOT_FETCH -> {
                    return "Could not load proxies. Check your internet?";
                }
                case INVALID_JSON -> {
                    return "Invalid data received.";
                }
                default -> {
                    return null;
                }
            }
        }
    }
}
