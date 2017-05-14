premake4 --file=build-linux.lua gmake
make clean config=release32
make config=release32
mv libsteamworks4j.so ../java-wrapper/src/main/resources/libsteamworks4j.so
mv libsteamworks4j-encryptedappticket.so ../server/src/main/resources/libsteamworks4j-encryptedappticket.so
make clean config=release64
make config=release64
mv libsteamworks4j.so ../java-wrapper/src/main/resources/libsteamworks4j64.so
mv libsteamworks4j-encryptedappticket.so ../server/src/main/resources/libsteamworks4j-encryptedappticket64.so
