# steamworks4j

A thin wrapper which allows Java applications to access the Steamworks C++ API. It can be easily integrated with other frameworks, such as [libGDX](http://libgdx.badlogicgames.com/).

## Introduction

The wrapper is written as minimal as possible without sacrificing ease of use. Its goal is to provide *just* an accessible Java API to Valve's C++ interfaces.

> **In its current state, the wrapper only publishes *some* interfaces. Some of them are not exposing the full API. In basic, I've added everything we need for our own games right now, and what I've been asked to. Feel free to send requests, or even better, participate to add the functions and interfaces still missing.**

**steamworks4j** is currently built against Steamworks SDK v1.31.

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
ISteamFriends
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

## Building the Java package

Just use Maven to *mvn package* in the root directory to compile a jar ready to be used in your application.

## Sample application

`com.codedisaster.steamworks.test.SteamAPITestApplication` contains some test code which shows basic usage of the Java wrapper.

## Debugging

For development you need to add a text file *steam_appid.txt* to the working directory, which just contains the appID of your Steam application.

### Prebuilt native libraries

Building the native libraries yourself isn't required if you don't plan to *modify* the native code part of the wrapper. There are prebuilt versions located in the *steamworks4j/natives/libs/* folder. The Maven build packs them all into *steamworks4j-natives.jar* which is then bundled with the primary JAR file.

## Building native libraries

### Dependencies

To build the native libraries, download the latest Steamworks SDK. Unzip and copy the following folders into the **steamworks4j** root directory:

- *steamworks4j/*
    - *sdk/*
        - *public/*
        - *redistributable_bin/*

We use [jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) to generate parts of the native code, and [premake4](http://industriousone.com/premake) to compile native code into dynamic libraries.

### Build environments

- Windows

  - The **Visual Studio 2010** command line environment must be available. It's sufficient to use the VS2010 (or VS2012) Express edition. The default location for the batch file to setup the build environment is *c:\Program Files (x86)\Microsoft Visual Studio 10.0\VC\vcvarsall.bat*
  - **premake4.exe** must be available in the path, e.g. put into the *steamworks4j/build-natives/* folder.

- Linux

  - **GCC** must be installed in a fairly recent version with C++11 support.
  - **premake4** needs to be installed. It should be available through your favorite package manager, e.g. per *apt-get install premake4*.

- Mac OS

  - **XCode** must be installed.
  - **premake4** needs to be available. One possible option is to install it as a **Mac Ports** package.

### Build steps

The first step is to let **jnigen** generate C++ source files for the embedded JNI functions. The `com.codedisaster.steamworks.jnigen.JNICodeGenerator` class contains the code to do that.

- The working directory must be the *steamworks4j/java-wrapper/* folder.
- Simply run `java -jar /path/to/steamworks4j-${version}-jar-with-dependencies.jar`. This will run the JNICodeGenerator.main() application.
- You can just run this as a plain Java application from inside your favorite IDE. No arguments are required.
- The generated code is written to *steamworks4j/java-wrapper/src/main/native/*.

If everything is setup correctly, you now only need to run the right `build-[osx|linux|win].[sh|bat]` shell script in the *steamworks4j/build-natives/* folder to compile and link the native libraries on each target platform.

In case of errors you should be able to open the workspace/project files created by **premake4** in their respective IDE for troubleshooting.
