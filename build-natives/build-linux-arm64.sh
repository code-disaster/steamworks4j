#!/usr/bin/env bash
premake5 --file=build-linux.lua gmake
make clean config=release_arm64
make config=release_arm64
mv bin/arm64/release/libsteamworks4j.so ../java-wrapper/src/main/resources/libsteamworks4j.so
mv bin/arm64/release/libsteamworks4j-server.so ../server/src/main/resources/libsteamworks4j-server.so
mv bin/arm64/release/libsteamworks4j-encryptedappticket.so ../server/src/main/resources/libsteamworks4j-encryptedappticket.so
