solution "steamworks4j"
	configurations { "ReleaseDLL" }

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		buildoptions { "-std=c++11", "-stdlib=libc++", "-Wall", "-mmacosx-version-min=10.7" }

		files { "../java-wrapper/src/main/native/**.h", "../java-wrapper/src/main/native/**.cpp" }
		includedirs {
			"../java-wrapper/src/main/native",
			"../java-wrapper/src/main/native/include/jni",
			"../java-wrapper/src/main/native/include/jni/mac",
			"../sdk/public",
			"../sdk/public/steam",
			"/System/Library/Frameworks/JavaVM.framework/Headers" }

		defines { "NDEBUG", "MACOSX" }
		flags { "Optimize" }

		libdirs { "../sdk/redistributable_bin/osx32" }
		links { "steam_api" }
		linkoptions { "-framework CoreFoundation", "-mmacosx-version-min=10.7" }

		platforms { "universal" }
