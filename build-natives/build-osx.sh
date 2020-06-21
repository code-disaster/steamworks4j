#!/usr/bin/env bash
./premake5 --file=build-osx.lua xcode4
xcodebuild -project steamworks4j.xcodeproj -alltargets clean
xcodebuild -project steamworks4j.xcodeproj -configuration ReleaseDLL
cp bin/x64/ReleaseDLL/libsteamworks4j.dylib ../java-wrapper/src/main/resources/libsteamworks4j.dylib
xcodebuild -project steamworks4j-server.xcodeproj -alltargets clean
xcodebuild -project steamworks4j-server.xcodeproj -configuration ReleaseDLL
cp bin/x64/ReleaseDLL/libsteamworks4j-server.dylib ../server/src/main/resources/libsteamworks4j-server.dylib
xcodebuild -project steamworks4j-encryptedappticket.xcodeproj -alltargets clean
xcodebuild -project steamworks4j-encryptedappticket.xcodeproj -configuration ReleaseDLL
cp bin/x64/ReleaseDLL/libsteamworks4j-encryptedappticket.dylib ../server/src/main/resources/libsteamworks4j-encryptedappticket.dylib
