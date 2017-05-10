premake4 --file=build-osx.lua xcode3
xcodebuild -alltargets clean
xcodebuild -configuration release
cp build/ReleaseDLL/libsteamworks4j.dylib ../java-wrapper/src/main/resources/libsteamworks4j.dylib
cp build/ReleaseDLL/libsteamworks4j-server.dylib ../server/src/main/resources/libsteamworks4j-server.dylib
