premake4 --file=build-osx.lua xcode3
xcodebuild -alltargets clean
xcodebuild -configuration release
cp build/ReleaseDLL/libsteamworks4j.dylib ../natives/libs/libsteamworks4j.dylib
cp ../sdk/redistributable_bin/osx32/libsteam_api.dylib ../natives/libs/libsteam_api.dylib