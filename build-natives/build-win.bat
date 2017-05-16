SET VCTargetsPath=C:\Program Files (x86)\MSBuild\Microsoft.Cpp\v4.0\V120
premake5 --file=build-win.lua vs2013
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=Win32 /t:Rebuild
move bin\x32\ReleaseDLL\steamworks4j.dll ..\java-wrapper\src\main\resources\steamworks4j.dll
move bin\x32\ReleaseDLL\steamworks4j-encryptedappticket.dll ..\server\src\main\resources\steamworks4j-encryptedappticket.dll
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=x64 /t:Rebuild
move bin\x64\ReleaseDLL\steamworks4j.dll ..\java-wrapper\src\main\resources\steamworks4j64.dll
move bin\x64\ReleaseDLL\steamworks4j-encryptedappticket.dll ..\server\src\main\resources\steamworks4j-encryptedappticket64.dll