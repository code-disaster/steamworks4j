# steamworks4j

A thin wrapper which allows Java applications to access the Steamworks C++ API. It can be easily integrated with other frameworks, such as [libGDX](http://libgdx.badlogicgames.com/).

## Getting started

You'll notice that this library is documented very scarcely. That's a deliberate choice. I assume that you are a **registered Steam developer** and have access to the Steamworks documentation.

Please refer to the official documentation to learn about the steps needed to prepare your application for use with Steam. Here's a very brief checklist:

- You need a properly configured SteamApp depot, e.g. you should be able to upload to and run a development build from Steam.
- The Steam client must be running.
- A steam_appid.txt needs to be present in the working directory.

## Maven releases

I plan to release major updates of **steamworks4j** on Maven Central. Please refer to the build instructions [below](#building-the-java-package) if you want to use features which are not deployed yet.

Maven:

```
<dependency>
  <groupId>com.code-disaster.steamworks4j</groupId>
  <artifactId>steamworks4j</artifactId>
  <version>1.0.2</version>
</dependency>
```

Gradle:

```
dependencies {
	compile "com.code-disaster.steamworks4j:steamworks4j:1.0.2"
}
```

## Introduction

The wrapper is written as minimal as possible without sacrificing ease of use. Its goal is to provide *just* an accessible Java API to Valve's C++ interfaces.

> **In its current state, the wrapper only publishes *some* interfaces. Most of them do not expose the full API. In basic, I've added everything we need for our own games, plus what I've been asked to. Feel free to send requests, or even better, participate to add the functions and interfaces still missing.**

**steamworks4j** is currently compiled with Steamworks SDK v1.33b.

This project is released under the MIT license. The **steamworks4j** package does not contain any source/header files owned by Valve. The only files included from the Steamworks SDK are the redistributable steam_api runtime libraries to accompany the prebuilt native libraries.

## Implementation overview

### Technical concept

The *public* Java interfaces are more or less 1:1 mapped to their native counterparts, as long as it doesn't impede usability and type safety. In general, each public function does simple type conversion only, before calling its native function.

Callbacks are dealt with in a similar manner. Native callbacks are received by *package private callback adapters*, which only do type conversion, then forward the callback to their *public* interface.

### Proper handling of callbacks

The Steamworks API provides two different mechanics to deal with callbacks: `STEAM_CALLBACK()` and `CCallResult<>`. Both are handled internally by **steamworks4j** for you, but this abstraction imposes one drawback:

> It is only guaranteed that, at any time, the user application receives callbacks related to the **latest** respective API call.

> In practice this means that the caller shouldn't "batch-execute" the same API function, then wait for a bunch (of the same type) of callbacks. Instead, only one single API call should be issued. Then the application should wait for the callback, process it, then execute the next API call.

> This is actually true for CCallResult<> style callbacks only, but the application will require more knowledge about the inner workings of Steamworks to differentiate, as the Java API doesn't tell.

### Implemented interfaces

The following interfaces have been *partially* implemented so far:

```
ISteamApps
ISteamFriends
ISteamGameServer
ISteamGameServerStats
ISteamNetworking
ISteamRemoteStorage (cloud saves, workshop files)
ISteamUGC
ISteamUser
ISteamUserStats (user stats, achievements, leaderboards)
ISteamUtils
```

## Requirements

First of all, you need to be registered with, and have a project set up at Steamworks to use this wrapper.

### Technical requirements

#### Java

The Java code is compatible to Java 6.

#### Mac OS

OS X 10.6 and above is supported.

## Sample application

The `com.codedisaster.steamworks.test.*` package contains some test applications which show basic usage of some of the Java interfaces.

## Debugging

For development you need to add a text file *steam_appid.txt* to the working directory, which just contains the appID of your Steam application.

## Building the Java package

Just use Maven to `mvn package` in the root directory to compile a jar ready to be used in your application, or `mvn install` to deploy it to your local Maven cache.

Run `mvn package -P with-dependencies` to compile an additional jar with dependencies ([jnigen](https://github.com/libgdx/libgdx/wiki/jnigen)) included.

## Building native libraries

### Prebuilt native libraries

Building the native libraries yourself isn't required if you don't plan to *modify* the native code part of the wrapper. There are prebuilt versions located in the *steamworks4j/natives/libs/* folder. The Maven build packs them all into *steamworks4j-natives.jar* which is then bundled with the primary JAR file.

### Dependencies

To build the native libraries, download the latest Steamworks SDK. Unzip and copy the following folders into the **steamworks4j** root directory:

- *steamworks4j/*
    - *sdk/*
        - *public/*
        - *redistributable_bin/*

[jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) is used to generate parts of the native code, and [premake4/5](http://industriousone.com/premake) to compile native code into dynamic libraries.

### Build environments

- Windows

  - The **Visual Studio 2013** command line environment must be available. It's sufficient to use the Community or Express editions. The default location for the batch file to setup the build environment is *c:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\vcvarsall.bat*
  - **premake5.exe** must be available in the path, e.g. in the *steamworks4j/build-natives/* folder.

- Linux

  - **GCC** must be installed in a fairly recent version with C++11 support.
  - **premake4** needs to be installed. It should be available through your favorite package manager, e.g. per *apt-get install premake4*.

- Mac OS

  - **XCode** must be installed.
  - **premake4** needs to be available. One possible option is to install it as a **Mac Ports** package.

### Build steps

The first step is to let [jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) generate C++ source files for the embedded JNI functions. The `com.codedisaster.steamworks.jnigen.JNICodeGenerator` class contains the code to do that.

- The working directory must be the *steamworks4j/java-wrapper/* folder.
- Simply run `java -jar /path/to/steamworks4j-${version}-jar-with-dependencies.jar`. This will run the JNICodeGenerator.main() application.
- You can just run this as a plain Java application from inside your favorite IDE. No arguments are required.
- The generated code is written to *steamworks4j/java-wrapper/src/main/native/*.

If everything is setup correctly, you now only need to run the right `build-[osx|linux|win].[sh|bat]` shell script in the *steamworks4j/build-natives/* folder to compile and link the native libraries on each target platform.

In case of errors you should be able to open the workspace/project files created by **premake4/5** in their respective IDE for troubleshooting.
