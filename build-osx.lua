solution "steamworks4j"
	configurations { "ReleaseDLL" }

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		buildoptions { "-std=c++11", "-stdlib=libc++", "-Wall", "-mmacosx-version-min=10.7" }

		files { "src/main/native/**.h", "src/main/native/**.cpp" }
		includedirs { "src/main/native", "src/main/native/include/jni", "src/main/native/include/jni/mac", "sdk/public", "sdk/public/steam", "/System/Library/Frameworks/JavaVM.framework/Headers" }

		defines { "NDEBUG", "MACOSX" }
		flags { "Optimize" }

		libdirs { "sdk/redistributable_bin/osx32" }
		links { "steam_api" }
		linkoptions { "-framework CoreFoundation", "-mmacosx-version-min=10.7" }

		platforms { "universal" }
