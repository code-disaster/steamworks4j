#!/usr/bin/env bash

build_architecture() {
    ./premake5 --file=build-osx-$1.lua xcode4

    xcodebuild -project steamworks4j-$1.xcodeproj -alltargets clean
    xcodebuild -project steamworks4j-$1.xcodeproj -configuration ReleaseDLL
    cp bin/ReleaseDLL/libsteamworks4j-$1.dylib ../java-wrapper/src/main/resources/libsteamworks4j-$1.dylib

    xcodebuild -project steamworks4j-$1-server.xcodeproj -alltargets clean
    xcodebuild -project steamworks4j-$1-server.xcodeproj -configuration ReleaseDLL
    cp bin/ReleaseDLL/libsteamworks4j-$1-server.dylib ../server/src/main/resources/libsteamworks4j-server-$1.dylib

    xcodebuild -project steamworks4j-$1-encryptedappticket.xcodeproj -alltargets clean
    xcodebuild -project steamworks4j-$1-encryptedappticket.xcodeproj -configuration ReleaseDLL
    cp bin/ReleaseDLL/libsteamworks4j-$1-encryptedappticket.dylib ../server/src/main/resources/libsteamworks4j-encryptedappticket-$1.dylib
}

build_architecture "x86_64"
build_architecture "arm64"

# Create FAT archives that contain both x86_64 and arm64 so it runs on intel or m1 macs
lipo -create ../java-wrapper/src/main/resources/libsteamworks4j-arm64.dylib ../java-wrapper/src/main/resources/libsteamworks4j-x86_64.dylib -output ../java-wrapper/src/main/resources/libsteamworks4j.dylib
lipo -create ../server/src/main/resources/libsteamworks4j-encryptedappticket-arm64.dylib ../server/src/main/resources/libsteamworks4j-encryptedappticket-x86_64.dylib -output ../server/src/main/resources/libsteamworks4j-encryptedappticket.dylib
lipo -create ../server/src/main/resources/libsteamworks4j-server-arm64.dylib ../server/src/main/resources/libsteamworks4j-server-x86_64.dylib -output ../server/src/main/resources/libsteamworks4j-server.dylib

# Delete all intermediate libs
find .. | grep x86_64.dylib | xargs rm
find .. | grep arm64.dylib | xargs rm

