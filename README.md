# random-java-utilities
Some utilities to make my (and possibly your) coding sessions easier.

## 🚨 EOL 🚨
GitHub has recently shadowbanned (public couldn't see my profile, but I could) for using a ***mail forwarding service, not a temporary mail service***. That means I now cannot trust GitHub with my projects as they can take these down anytime, so now I'm self-hosting my own maven repo.

___***THIS PACKAGE HAS BEEN FULLY REWRITTEN FROM SCRATCH, NOW BEING WAY FASTER, SMALLER AND OPTIMIZED ON MY OWN MAVEN REPO - CREATE AN ISSUE OR MESSAGE ME SOMEHOW ELSE IF YOU WANT ACCESS***___

## Features
Some of the cool features:
- a simple CLI with support for custom commands
- simple logger, functions for colored output
- simple database connection manager for JDBC
- **proxy tools for internet scraping.** (SOCKS5-only) 

Manager for loading and selecting proxies (by country and city) and selectable sources of these proxies.
Available sources:
| Service | Servers | Working | Working % |
| --- | --- | --- | --- |
| [@monosans](https://github.com/monosans/proxy-list) | 190 | 9 | 4.74% |
| [spys.one](https://spys.one/en/) | 399 | 25 | 6.27% |
| [proxyscrape.com](https://proxyscrape.com/free-proxy-list) | 251 | 8 | 3.19% |
| [@TheSpeedX](https://github.com/TheSpeedX/PROXY-List) | 4860 | 16 | 0.33% |
| [@jetkai](https://github.com/jetkai/proxy-list) | 347 | 5 | 1.44% |
| [mullvad.net](https://mullvad.net/en) | 411 | 382 | 92.95% |
| [@hookzof](https://github.com/hookzof/socks5_list) | 240 | 5 | 2.08% |
| [@ShiftyTR](https://github.com/ShiftyTR/Proxy-List) | 158 | 2 | 1.27% |
| Your own! | TBA | TBA | 100%! |

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
