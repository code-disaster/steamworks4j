solution "steamworks4j"
	configurations { "ReleaseDLL" }

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		files { "../java-wrapper/src/main/native/**.h", "../java-wrapper/src/main/native/**.cpp" }
		includedirs {
			"../java-wrapper/src/main/native",
			"../java-wrapper/src/main/native/include/jni",
			"../java-wrapper/src/main/native/include/jni/win32",
			"../sdk/public",
			"../sdk/public/steam" }

		defines { "NDEBUG", "WINDOWS" }
		flags { "Optimize" }

		configuration "x32"
			libdirs { "../sdk/redistributable_bin" }
			links { "steam_api" }

		configuration "x64"
			libdirs { "../sdk/redistributable_bin/win64" }
			links { "steam_api64" }

		platforms { "x32", "x64" }
