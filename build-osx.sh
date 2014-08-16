premake4 --file=build-osx.lua xcode3
xcodebuild -alltargets clean
xcodebuild -configuration release
cp build/ReleaseDLL/libsteamworks4j.dylib src/main/resources/libsteamworks4j.dylib
