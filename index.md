---
layout: default
---

## About

The *steamworks4j* library allows Java applications to access the Steamworks C++ API. It can be easily integrated with other frameworks, such as [libGDX](http://libgdx.badlogicgames.com/), [LWJGL](http://www.lwjgl.org/) or [Slick2D](http://slick.ninjacave.com/).

### Introduction

The wrapper is written as minimal as possible without sacrificing ease of use. Its goal is to provide *just* an accessible Java API to Valve's C++ interfaces.

The *public* Java interfaces are - more or less - 1:1 mapped to their native counterparts, as long as it doesn't impede usability and type safety. In general, each public function does simple Java to C++ type conversion only, then calls its native function.

Callbacks are dealt with in a similar manner. Native callbacks are received by *package private* callback adapters, which only do type conversion, then forward the callback to their *public* Java interface.

### Technical limitations

> Note: the requirements listed here apply to the most recent version only. Please check the change log for more information.

#### SDK version

The prebuilt native libraries of *steamworks4j* are currently compiled with Steamworks SDK **v{{ site.steamworks4j.sdk-version }}**.

#### Java version

The Java code compiles with Java language level 7.

#### Mac OS

MacOS 64-bit is supported on OS X 10.9 and later. Support for 32-bit via universal binaries has been dropped in *steamworks4j* v1.8.0.

#### Linux

Linux 64-bit is supported. Support for 32-bit versions has been dropped in *steamworks4j* v1.7.0.

#### Windows

Both 32-bit and 64-bit versions are supported.

### Copyright notice

The *steamworks4j* project is released under the MIT license. The *steamworks4j* packages do not contain any source/header files owned by Valve. The only files included from the Steamworks SDK are the redistributable steam_api runtime libraries to accompany the prebuilt native libraries.
