premake5 --file=build-win.lua vs2013
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=Win32 /t:Rebuild
move steamworks4j.dll ..\natives\libs\steamworks4j.dll
copy ..\sdk\redistributable_bin\steam_api.dll ..\natives\libs\steam_api.dll
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=x64 /t:Rebuild
move steamworks4j.dll ..\natives\libs\steamworks4j64.dll
copy ..\sdk\redistributable_bin\win64\steam_api64.dll ..\natives\libs\steam_api64.dll