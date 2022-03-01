---
layout: default
---

## Getting started

### Project setup

To add Steamworks support, you just have to download and add the appropriate JAR files to your Java project.

- ```steamworks4j-{{ site.steamworks4j.version }}.jar``` should be sufficient for most uses
- ```steamworks4j-server-{{ site.steamworks4j.version }}.jar``` if you need to support game servers or encrypted app tickets.

#### Release versions

Major updates are released on Maven Central, so the easiest way is to add the library as an external dependency, using your favorite build environment.

Maven:

{% highlight xml %}
<dependencies>
  <dependency>
    <groupId>com.code-disaster.steamworks4j</groupId>
    <artifactId>steamworks4j</artifactId>
    <version>{{ site.steamworks4j.version }}</version>
  </dependency>
  <dependency>
    <groupId>com.code-disaster.steamworks4j</groupId>
    <artifactId>steamworks4j-server</artifactId>
    <version>{{ site.steamworks4j.version }}</version>
  </dependency>
</dependencies>
{% endhighlight xml %}

Gradle:

{% highlight groovy %}
apply plugin: 'java-library'
implementation "com.code-disaster.steamworks4j:steamworks4j:{{ site.steamworks4j.version }}"
implementation "com.code-disaster.steamworks4j:steamworks4j-server:{{ site.steamworks4j.version }}"
{% endhighlight groovy %}

If you don't use any build tools, [direct downloads of .jar files](http://mvnrepository.com/artifact/com.code-disaster.steamworks4j/) are also available.

#### Snapshot versions

I've started releasing snapshot builds on [Sonatype](https://oss.sonatype.org/content/repositories/snapshots/).

Maven:

{% highlight xml %}
<repositories>
  <repository>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.code-disaster.steamworks4j</groupId>
    <artifactId>steamworks4j</artifactId>
    <version>{{ site.steamworks4j.snapshot-version }}-SNAPSHOT</version>
  </dependency>
  <dependency>
    <groupId>com.code-disaster.steamworks4j</groupId>
    <artifactId>steamworks4j-server</artifactId>
    <version>{{ site.steamworks4j.snapshot-version }}-SNAPSHOT</version>
  </dependency>
</dependencies>
{% endhighlight xml %}

Gradle:

{% highlight groovy %}
repositories {
    maven {
        url "https://oss.sonatype.org/content/repositories/snapshots/"
    }
}

dependencies {
  compile "com.code-disaster.steamworks4j:steamworks4j:{{ site.steamworks4j.snapshot-version }}-SNAPSHOT"
  compile "com.code-disaster.steamworks4j:steamworks4j-server:{{ site.steamworks4j.snapshot-version }}-SNAPSHOT"
}
{% endhighlight groovy %}

#### Build from source

To learn how to build the library from source code, please refer to the [build instructions]({{ '/build-instructions.html' | prepend: site.baseurl | prepend: site.url }}).

### Basic usage

#### Preparation

> You'll notice that the library source code is documented very scarcely. That's a deliberate choice. I assume that you are a **registered Steam developer** and have access to the Steamworks documentation as your primary source of information.

Please refer to the official documentation to learn about the steps needed to prepare your application for use with Steam. Here's a very brief checklist:

- You need a properly configured SteamApp depot, e.g. you should be able to upload to and run a development build from Steam.
- The Steam client must be running.
- For development, a valid steam_appid.txt needs to be present in the working directory of your application.

#### Initialization

As a first step, to load the native libraries, you need to call ```SteamAPI.loadLibraries()```.

Second, to initialize the Steamworks client API, you call ```SteamAPI.init()```.

> Before *steamworks4j* v1.8.0, both these steps were condensed in ```SteamAPI.init()```. This has been changed to allow applications to call certain functions, like ```SteamAPI.restartAppIfNecessary()```, before the Steam API is initialized.

{% highlight java %}
try {
    SteamAPI.loadLibraries();
    if (!SteamAPI.init()) {
        // Steamworks initialization error, e.g. Steam client not running
    }
} catch (SteamException e) {
    // Error extracting or loading native libraries
}
{% endhighlight java %}

By default, ```SteamAPI.loadLibraries()``` detects the operating system it runs on, then extracts the appropriate native libraries from your application's resource path to a temporary folder. There's a second function which allows to specify the path to a directory containing the native libraries.

{% highlight java %}
SteamAPI.loadLibraries("./libs");
{% endhighlight java %}

#### Update ticks

Add a call to ```SteamAPI.runCallbacks()``` to your game loop. Steamworks recommends it to be called at least 15 times per second, but I haven't seen any performance impact if e.g. called each frame at 60 fps.

{% highlight java %}
if (SteamAPI.isSteamRunning()) {
    SteamAPI.runCallbacks();
}
{% endhighlight java %}

#### Creating interfaces

The *steamworks4j* library follows the Steamworks C++ API as close as feasible. In general, for each interface exposed by the C++ API, there's an equivalent Java class (if implemented). For example, the ```SteamUserStats``` Java class provides access to the ```ISteamUserStats``` C++ API.

> At present, not *all* interfaces are implemented by the Java library. Some of those which are, do not expose the full API. In general, I've added everything I needed for our own games, plus what I've been asked to. Feel free to send feature requests, or even better, pull requests on Github to add the functions and interfaces still missing.

If a C++ interface contains functions which trigger ```STEAM_CALLBACK()``` or ```CCallResult<>``` style callbacks (say, most of them), the Java class is accompanied by a callback interface which must be implemented by the user application.

> It is only guaranteed that, at any time, each instance of an interface does only receive callbacks related to its **latest** respective API call.

> In practice this means that the application shouldn't "batch-execute" the same API function, then wait for a bunch of callbacks. Instead, only one single API call should be issued. Then the application should wait for the callback, process it, then execute the next API call.

> If you really want to execute more of the same callback-triggering function in parallel, you have to create and maintain multiple instances of that interface.

{% highlight java %}
public class MyAppUserStatsCallback
        implements SteamUserStatsCallback {

    // ... application-specific implementation
}

SteamUserStatsCallback callback = new MyAppUserStatsCallback();
SteamUserStats stats = new SteamUserStats(callback);
{% endhighlight java %}

#### Shutdown

Every interface instance (each instance of a class derived from ```SteamInterface```) may allocate a few bytes of native heap memory. You should call ```SteamInterface.dispose()``` on shutdown to prevent memory leaks.

{% highlight java %}
stats.dispose();
{% endhighlight java %}

To shut down the Steamworks API, just call ```SteamAPI.shutdown()```. This may flush (sync) any pending client data, e.g. achievements and user stats, so you should make sure your application exists gracefully.

{% highlight java %}
SteamAPI.shutdown();
{% endhighlight java %}

### The steamworks4j-server module

With *steamworks4j* v1.7.0, server functions were moved to a separate Maven module, *steamworks4j-server*.

#### GameServer API

Basic API use is similar to the client wrapper, just with *SteamGameServerAPI* as the main entry point.

{% highlight java %}
// initialization
try {
    SteamGameServerAPI.loadLibraries();
    if (!SteamGameServerAPI.init((127 << 24) + 1, (short) 27016, (short) 27017,
        SteamGameServerAPI.ServerMode.NoAuthentication, "0.0.1"))) {
        // initialization error
    }
} catch (SteamException e) {
    // Error extracting or loading native libraries
}

// update ticks
while (serverIsAlive) {
  SteamGameServerAPI.runCallbacks();
}

// shutdown
SteamGameServerAPI.shutdown();
{% endhighlight java %}

#### Encrypted app tickets

The ```SteamEncryptedAppTicket``` wrapper is embedded inside the server module, but works as a stand-alone since it only depends on the *sdkencryptedappticket* shared library. Its use is simple - you just need to ensure that the native library is loaded first.

{% highlight java %}
try {
    SteamEncryptedAppTicket.loadLibraries();
} catch (SteamException e) {
    // Error extracting or loading native libraries
}

SteamEncryptedAppTicket encryptedAppTicket = new SteamEncryptedAppTicket();
{% endhighlight java %}

### Sample applications

The `steamworks-tests` module contains some console applications which demonstrate use of the Java API and some of its interfaces.
