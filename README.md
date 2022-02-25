# random-java-utilities
Some utilities to make my coding sessions easier.

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
                <version>1.0</version>
        </dependency>
</dependencies>
```
- profit

## License
None, do whatever you want with this, its just a bunch of copied code mixed together.

## Features

### Args Manager
This allows you to simply check length of given arguments, data types, get those arguments, and even mandatory checker, that stops the program if the given argument is not, what you wanted it to be (for example terms of service agreement)\
![Args Overview 1](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/Args_1.png?raw=true)\
![Args Overview 2](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/Args_2.png?raw=true)\
*TIP: Forced methods mean that the programs stops, if the conditions arent true*

### Files
Some functions to return path to jar file, enclosing folder, File.separator() under a new name, and a method to copy resources.\
![Files Overview 1](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/Files_1.png?raw=true)

### Input
Bunch of question dialogs, for example Y/N, Y/N but only Y lets you continue, dialog with custom defined responses, or dialog with any response, great for asking the wonderful end-user their name!\
![Input Overview 1](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/Input_1.png?raw=true)\
![Input Overview 2](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/Input_2.png?raw=true)

### OS
Literally copy of Apache commons SystemUtils, but under a new name with one method lol.\
![OS Overview 1](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/OS_1.png?raw=true)
![OS Overview 2](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/OS_2.png?raw=true)

### TextColor
Allows you to make the boring terminal output colored! Needs a terminal with ASCII colors support.
![TextColor Overview 1](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/TextColor_1.png?raw=true)\
![TextColor Overview 2](https://github.com/SansSerif1/random-java-utilities/blob/main/readme_images/TextColor_2.png?raw=true)
