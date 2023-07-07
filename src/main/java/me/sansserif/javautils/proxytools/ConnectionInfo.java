package me.sansserif.javautils.proxytools;

public record ConnectionInfo(String countryName, String cityName, String ip, float latitude, float longitude, String organization, boolean blacklisted) {
}
