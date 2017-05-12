premake5 --file=build-win.lua vs2013
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=Win32 /t:Rebuild
move steamworks4j.dll ..\java-wrapper\src\main\resources\steamworks4j.dll
move steamworks4j-encryptedappticket.dll ..\server\src\main\resources\steamworks4j-encryptedappticket.dll
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=x64 /t:Rebuild
move steamworks4j.dll ..\java-wrapper\src\main\resources\steamworks4j64.dll
move steamworks4j-encryptedappticket.dll ..\server\src\main\resources\steamworks4j-encryptedappticket64.dll
