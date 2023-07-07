# random-java-utilities
Some utilities to make my (and possibly your) coding sessions easier.

## Documentation rework
***___I do not currently have enough time for this, but when I do, I'm going to add everything you need to know to use this package. Until then, you can learn yourself from the source code :)___***

Some of the cool features:
- a simple CLI with support for custom commands
- simple logger, functions for colored output
- simple database connection manager for JDBC
- **proxy tools for internet scraping.** (SOCKS5-only) 

Manager for loading and selecting proxies (by country and city) and selectable sources of these proxies.
Available sources:
- [Mullvad](https://mullvad.net/en) (Requires connection to one of their VPN servers)
- [TheSpeedX's proxies](https://github.com/TheSpeedX/PROXY-List)
- [ShiftyTR's proxies](https://github.com/ShiftyTR/Proxy-List)
- [Jetkai's proxies](https://github.com/jetkai/proxy-list)
- [Monosans' proxies](https://github.com/monosans/proxy-list)
- [Hookzof's proxies](https://github.com/hookzof/socks5_list)
- A custom one of yours, can be added by implementing a specific interface.

Cya!


## Download and Use
- Add this to your pom:
```
<repositories>
        <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
        </repository>
</repositories>
<dependencies>
        <dependency>
                <groupId>com.github.SansSerif1</groupId>
                <artifactId>random-java-utilities</artifactId>
                <version>[VERSION]</version>
        </dependency>
</dependencies>
```
- profit
