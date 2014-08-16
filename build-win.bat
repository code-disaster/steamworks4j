premake4 --file=build-win.lua vs2010
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=Win32 /t:Clean,Build
move steamworks4j.dll src\main\resources\steamworks4j.dll
copy sdk\redistributable_bin\steam_api.dll src\main\resources\steam_api.dll
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=x64 /t:Clean,Build
move steamworks4j.dll src\main\resources\steamworks4j64.dll
copy sdk\redistributable_bin\win64\steam_api64.dll src\main\resources\steam_api64.dll