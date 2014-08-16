# steamworks4j

A thin wrapper which allows Java applications to access the Steamworks C++ API.

## About

The wrapper is written as minimal as possible without sacrificing ease of usability. Its goal is to provide *just* an accessible Java interface to Valve's C++ interfaces.

In its current state, the wrapper only publishs very few interfaces. In basic, everything we put into use with our own games right now. Feel free to participate to extend it with functions and interfaces still missing.

**steamworks4j** is currently built against Steamworks SDK 1.30.

This project is released under the MIT license. The **steamworks4j** package does not contain any source/header files owned by Valve.

## Requirements

First of all, you need to be registered with Steamworks to use this wrapper.

## Building native libraries

### Prebuilt libraries

Building the native libraries yourself isn't required if you only plan to *use* the wrapper. There are prebuilt versions included in the *steamworks4j/src/main/resources* folder.

### Preparations

To build the native libraries, download the latest Steamworks SDK. Unzip and copy the following folders into the **steamworks4j** root directory:

- *steamworks4j/*
    - *sdk/*
        - *public/*
        - *redistributable_bin/*

We use [jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) to generate parts of the native code, and [premake4](http://industriousone.com/premake) to compile native code into dynamic libraries.

### Build environments

#### Windows

- The **Visual Studio 2012** command line environment must be available. It's sufficient to use the VS2012 Express edition. The default location for the batch file to setup the build environment is *c:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\vcvarsall.bat*
- **premake4.exe** must be available in the path, e.g. put into the *steamworks4j/* root folder.

#### Linux

- **GCC** must be installed in a fairly recent version with C++11 support.
- **premake4** needs to be installed. It should be available through your favorite package manager, e.g. per *apt-get install premake4*.

#### Mac OS

- **XCode** must be installed.
- **premake4** needs to be available. One possible option is to install as a **Mac Ports** package.

### Build steps

The first step is to let **jnigen** generate C++ source files for the embedded JNI functions. The `com.codedisaster.steamworks.jnigen.JNICodeGenerator` class contains the code to do that.

- The working directory must be the *steamworks4j/* root folder.
- Simply run `java -jar /path/to/steamworks4j-???.jar`. This will run the JNICodeGenerator.main() application.
- You can just run this as a plain Java application from inside your favorite IDE. No arguments are required.
- The generated code is written to *steamworks4j/src/main/native/*.

If everything is setup correctly, you now only need to run the right `./build-???` bash file to compile and link the native libraries on each target platform.

In case of errors you should be able to open the workspace/project files created by **premake4**, to open them in the respective IDE for troubleshooting.

## Building the Java package

Just use Maven to *mvn package* or *mvn install* to compile a jar ready to be used in your application.

## Using the wrapper

`com.codedisaster.steamworks.SteamAPITestApplication` contains some test code which shows basic usage of the Java wrapper.

## Debugging

For development you'll need to put a text file *steam_appid.txt*, which just contains the appID of your Steam application, into the working directory of your game.