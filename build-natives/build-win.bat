@if [%1]==[] goto usage

premake5 --file=build-win.lua %1
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=Win32 /t:Rebuild
@if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%
copy bin\x32\ReleaseDLL\steamworks4j.dll ..\java-wrapper\src\main\resources\steamworks4j.dll
copy bin\x32\ReleaseDLL\steamworks4j-server.dll ..\server\src\main\resources\steamworks4j-server.dll
copy bin\x32\ReleaseDLL\steamworks4j-encryptedappticket.dll ..\server\src\main\resources\steamworks4j-encryptedappticket.dll
msbuild steamworks4j.sln /p:Configuration=ReleaseDLL /p:Platform=x64 /t:Rebuild
@if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%
copy bin\x64\ReleaseDLL\steamworks4j.dll ..\java-wrapper\src\main\resources\steamworks4j64.dll
copy bin\x64\ReleaseDLL\steamworks4j-server.dll ..\server\src\main\resources\steamworks4j-server64.dll
copy bin\x64\ReleaseDLL\steamworks4j-encryptedappticket.dll ..\server\src\main\resources\steamworks4j-encryptedappticket64.dll

@goto :eof

:usage
@echo call with [action] profile, e.g. "build-win vs2013"
@exit /B 1
