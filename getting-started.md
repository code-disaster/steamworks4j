---
layout: default
---

## Getting started

### Project setup

To add Steamworks support, you just have to download and add ```steamworks4j-{{ site.steamworks4j.version }}.jar``` to your Java project.

Major updates are released on Maven Central, so the easiest way is to add the library as an external dependency, using your favorite build environment.

Maven:

```
<dependency>
  <groupId>com.code-disaster.steamworks4j</groupId>
  <artifactId>steamworks4j</artifactId>
  <version>{{ site.steamworks4j.version }}</version>
</dependency>
```

Gradle:

```
dependencies {
	compile "com.code-disaster.steamworks4j:steamworks4j:{{ site.steamworks4j.version }}"
}
```

To learn how to build the library from source code, please refer to the [build instructions]({{ '/build-instructions.html' | prepend: site.url }}).

### Basic usage

#### Preparation

Please refer to the official documentation to learn about the steps needed to prepare your application for use with Steam. Here's a very brief checklist:

- You need a properly configured SteamApp depot, e.g. you should be able to upload to and run a development build from Steam.
- The Steam client must be running.
- A valid steam_appid.txt needs to be present in the working directory of your application.

> You'll notice that the library source code is documented very scarcely. That's a deliberate choice. I assume that you are a **registered Steam developer** and have access to the Steamworks documentation.

#### Initialization

To load the native libraries and initialize the Steamworks client API, you just need to call ```SteamAPI.init()```.

```java
if (!SteamAPI.init()) {
	// report error
}
```

By default, ```SteamAPI.init()``` tries to load the native libraries from *steamworks4j-natives.jar* found in your application's resource path. There's a second function which allows to specify the path to a custom JAR, or a folder containing the native libraries. For example, this is used by the sample applications to load the prebuilt libraries directly from ```steamworks4j/natives/libs/``` for development purposes.

```java
if (!SteamAPI.init("../natives/libs")) {
	// report error
}
```

#### Update ticks

Add a call to ```SteamAPI.runCallbacks()``` to your game loop. Steamworks recommends it to be called at least 15 times per second, but I haven't seen any performance impact if e.g. called each frame at 60 fps.

```java
if (SteamAPI.isSteamRunning()) {
	SteamAPI.runCallbacks();
}
```

#### Creating interfaces

The *steamworks4j* library follows the Steamworks C++ API as close as feasible. In general, for each interface exposed by the C++ API, there's an equivalent Java class (if implemented). For example, the ```SteamUserStats``` Java class provides access to the ```ISteamUserStats``` C++ API.

> At present, not *all* interfaces are implemented by the Java library. Some of them which are, do not expose the full API. In general, I've added everything I needed for our own games, plus what I've been asked to. Feel free to send feature requests, or even better, pull requests on Github to add the functions and interfaces still missing.

If a C++ interface contains functions which trigger ```STEAM_CALLBACK()``` or ```CCallResult<>``` style callbacks (say, most of them), the Java class is accompanied by a callback interface which must be implemented by the user application.

> It is only guaranteed that, at any time, each instance of an interface does only receive callbacks related to its **latest** respective API call.

> In practice this means that the application shouldn't "batch-execute" the same API function, then wait for a bunch of callbacks. Instead, only one single API call should be issued. Then the application should wait for the callback, process it, then execute the next API call.

> If you really want to execute more of the same callback-triggering function in parallel, you have to create and maintain multiple instances of that interface.

```java
public class MyAppUserStatsCallback
        implements SteamUserStatsCallback {

	// ... application-specific implementation
}

SteamUserStatsCallback callback = new MyAppUserStatsCallback();
SteamUserStats stats = new SteamUserStats(callback);
```

#### Shutdown

Each interface instance (each instance of a class derived from ```SteamInterface```) allocates and owns at least a few bytes of native heap memory. You should call ```SteamInterface.dispose()``` on shutdown to prevent memory leaks.

```java
stats.dispose();
```

To shut down the Steamworks API, just call ```SteamAPI.shutdown()```. This may flush (sync) any pending client data, e.g. achievements and user stats, so you should make sure your application exists gracefully.

```java
SteamAPI.shutdown();
```

### Sample applications

The `com.codedisaster.steamworks.test.*` package contains some console applications which show basic usage of the Java API and some of its interfaces.
