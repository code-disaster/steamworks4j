---
layout: default
---

## About

The *steamworks4j* library allows Java applications to access the Steamworks C++ API. It can be easily integrated with other frameworks, such as [libGDX](http://libgdx.badlogicgames.com/).

### Introduction

The wrapper is written as minimal as possible without sacrificing ease of use. Its goal is to provide *just* an accessible Java API to Valve's C++ interfaces.

The *public* Java interfaces are - more or less - 1:1 mapped to their native counterparts, as long as it doesn't impede usability and type safety. In general, each public function does simple Java to C++ type conversion only, then calls its native function.

Callbacks are dealt with in a similar manner. Native callbacks are received by *package private* callback adapters, which only do type conversion, then forward the callback to their *public* Java interface.

### Technical limitations

#### SDK version

The prebuilt native libraries of *steamworks4j* are currently compiled with Steamworks SDK **v{{ site.steamworks4j.sdk-version }}**.

#### Java version

The Java code compiles with Java 6.

#### Mac OS

OS X 10.6 and later are supported.

### Copyright notice

The *steamworks4j* project is released under the MIT license. The *steamworks4j* package does not contain any source/header files owned by Valve. The only files included from the Steamworks SDK are the redistributable steam_api runtime libraries to accompany the prebuilt native libraries.
