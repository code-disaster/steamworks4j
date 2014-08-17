premake4 --file=build-linux.lua gmake
make clean config=release32
make config=release32
mv libsteamworks4j.so ../natives/libs/libsteamworks4j.so
cp ../sdk/redistributable_bin/linux32/libsteam_api.so ../natives/libs/libsteam_api.so
make clean config=release64
make config=release64
mv libsteamworks4j.so ../natives/libs/libsteamworks4j64.so
cp ../sdk/redistributable_bin/linux64/libsteam_api.so ../natives/libs/libsteam_api64.so
