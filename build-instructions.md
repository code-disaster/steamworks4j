---
layout: default
---

## Build instructions

### Java

Just use Maven with ```mvn package``` in the root directory to compile a jar ready to be used in your application, or ```mvn install``` to deploy it to your local Maven cache.

Run ```mvn package -P with-dependencies``` to compile an additional jar with dependencies ([jnigen](https://github.com/libgdx/libgdx/wiki/jnigen)) included.

### Native libraries

#### Prebuilt native libraries

You *do not need* to build the native libraries yourself if you don't plan to *modify* the native code of *steamworks4j*. There are prebuilt versions located in the ```steamworks4j/java-wrapper/src/main/resources/``` folder.

#### External dependencies

To build the native libraries, download the latest Steamworks SDK. Unzip and copy the following folders into the **steamworks4j/** root directory:

- *steamworks4j/*
    - *sdk/*
        - *public/*
        - *redistributable_bin/*

[jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) is used to generate parts of the native code, and [premake4/5](http://industriousone.com/premake) to compile native code into dynamic libraries.

#### Build environments

- Windows

  - The **Visual Studio 2013** command line environment must be available. It's sufficient to use the Community or Express editions. The default location for the batch file to setup the build environment is *c:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\vcvarsall.bat*
  - **premake5.exe** must be available in the path, e.g. in the ```steamworks4j/build-natives/``` folder.

- Linux

  - **GCC** must be installed in a fairly recent version with C++11 support.
  - **premake4** needs to be installed. It should be available through your favorite package manager, e.g. per *apt-get install premake4*.

- Mac OS

  - **XCode** must be installed.
  - **premake4** needs to be available. One possible option is to install it as a **Mac Ports** package.

### Build steps

The first step is to let [jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) generate C++ source files for the embedded JNI functions. The `com.codedisaster.steamworks.jnigen.JNICodeGenerator` class in the `jnigen` sub-project contains the code to do that.

- The working directory must be the ```steamworks4j/java-wrapper/``` folder.
- You can just run `JNICodeGenerator.main()` as a plain Java application from inside your favorite IDE. No arguments are required.
- The generated code is written to ```steamworks4j/java-wrapper/src/main/native/```.

If everything is setup correctly, you now only need to run the right `build-osx.sh, build-linux.sh or build-win.bat` shell script in the ```steamworks4j/build-natives/``` folder to compile and link the native libraries on each target platform. The scripts also copy both the compiled and redistributable libraries to the ```steamworks4j/java-wrapper/src/main/resources/``` folder.

In case of errors you should be able to open the workspace/project files created by **premake4/5** in their respective IDE for troubleshooting.
