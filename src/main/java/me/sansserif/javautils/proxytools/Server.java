package me.sansserif.javautils.proxytools;

import java.net.InetSocketAddress;
import java.net.Proxy;

public record Server(String host, int port, String countryCode, String city) {
    public Proxy getProxy() throws Exception {
        if (host == null || host.isEmpty()) throw new Exception("Invalid host.");
        if (port < 1 || port > 65535) throw new Exception("Invalid port.");
        return new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port));
    }
}
