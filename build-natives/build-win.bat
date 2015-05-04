PATH=C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\
call "C:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\vcvarsall.bat" 
premake5 --file=build-win.lua vs2013
pause
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=Win32 /t:Rebuild
move steamworks4j.dll ..\natives\libs\steamworks4j.dll
copy ..\sdk\redistributable_bin\steam_api.dll ..\natives\libs\steam_api.dll
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=x64 /t:Rebuild
move steamworks4j.dll ..\natives\libs\steamworks4j64.dll
copy ..\sdk\redistributable_bin\win64\steam_api64.dll ..\natives\libs\steam_api64.dll
pause