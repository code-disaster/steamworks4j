# steamworks4j

A thin wrapper which allows Java applications to access the Steamworks C++ API. It can be easily integrated with other frameworks, e.g. [libGDX](http://libgdx.badlogicgames.com/).

## About

The reason for this project to surface is a rather simple one. For development of our latest game, [Halfway](http://halfwaygame.com), we were very lucky to get hands on work-in-progress Java wrappers from two different indie games developers. This saved us quite some time we would have spent implementing all the JNI fun ourselves, though we had to work around some issues, too.

After release of **Halfway** I've been approached by multiple other developers who expressed interest in finding an available Java library. Odd enough (at least in the library-rich world of Java) there doesn't seem to be any, not least because the Valve NDA seems a little fuzzy about the topic of releasing information about the Steamworks SDK into the public.

After gathering some feedback from developers of wrappers in other languages, e.g. the rather popular [Steamworks.NET](https://github.com/rlabrecque/Steamworks.NET), I now decided to fill the gap, and start an open-source library for anyone to use - and contribute, hopefully.

The wrapper is written as minimal as possible without sacrificing ease of usability. Its goal is to provide *just* an accessible Java interface to Valve's C++ interfaces.

> **In its current state, the wrapper only publishes very few interfaces. In basic, everything we put into use with our own games right now. Feel free to participate to extend it with the many functions and interfaces still missing.**

**steamworks4j** is currently built against Steamworks SDK v1.30.

This project is released under the MIT license. The **steamworks4j** package does not contain any source/header files owned by Valve. The only files included from the Steamworks SDK are the redistributable steam_api runtime libraries to accompany the prebuilt native libraries.

## Requirements

First of all, you need to be registered with, and have a project set up at Steamworks to use this wrapper.

### Technical requirements

#### Java

The Java code is compiled with JDK 1.6.

#### Mac OS

Right now only OS X 10.7 and above is supported.

## Building native libraries

### Prebuilt libraries

Building the native libraries yourself isn't required if you only plan to *use* the wrapper. There are prebuilt versions included in the *steamworks4j/natives/libs/* folder. The Maven build packs them all into *steamworks4j-natives.jar* which is then bundled with the primary JAR file.

### Dependencies

To build the native libraries, download the latest Steamworks SDK. Unzip and copy the following folders into the **steamworks4j** root directory:

- *steamworks4j/*
    - *sdk/*
        - *public/*
        - *redistributable_bin/*

We use [jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) to generate parts of the native code, and [premake4](http://industriousone.com/premake) to compile native code into dynamic libraries.

### Build environments

#### Windows

- The **Visual Studio 2010** command line environment must be available. It's sufficient to use the VS2010 (or VS2012) Express edition. The default location for the batch file to setup the build environment is *c:\Program Files (x86)\Microsoft Visual Studio 10.0\VC\vcvarsall.bat*
- **premake4.exe** must be available in the path, e.g. put into the *steamworks4j/build-natives/* folder.

#### Linux

- **GCC** must be installed in a fairly recent version with C++11 support.
- **premake4** needs to be installed. It should be available through your favorite package manager, e.g. per *apt-get install premake4*.

#### Mac OS

- **XCode** must be installed.
- **premake4** needs to be available. One possible option is to install it as a **Mac Ports** package.

### Build steps

The first step is to let **jnigen** generate C++ source files for the embedded JNI functions. The `com.codedisaster.steamworks.jnigen.JNICodeGenerator` class contains the code to do that.

- The working directory must be the *steamworks4j/java-wrapper/* folder.
- Simply run `java -jar /path/to/steamworks4j-${version}-jar-with-dependencies.jar`. This will run the JNICodeGenerator.main() application.
- You can just run this as a plain Java application from inside your favorite IDE. No arguments are required.
- The generated code is written to *steamworks4j/java-wrapper/src/main/native/*.

If everything is setup correctly, you now only need to run the right `build-[osx|linux|win].[sh|bat]` shell script in the *steamworks4j/build-natives/* folder to compile and link the native libraries on each target platform.

In case of errors you should be able to open the workspace/project files created by **premake4**, to open them in the respective IDE for troubleshooting.

## Building the Java package

Just use Maven to *mvn package* in the root directory to compile a jar ready to be used in your application.

## Using the wrapper

`com.codedisaster.steamworks.SteamAPITestApplication` contains some test code which shows basic usage of the Java wrapper.

## Debugging

For development you'll need to put a text file *steam_appid.txt*, which just contains the appID of your Steam application, into the working directory of your application.