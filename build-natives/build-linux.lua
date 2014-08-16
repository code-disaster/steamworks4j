solution "steamworks4j"
	configurations { "release" }

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		buildoptions { "-std=c++11", "-Wall" }

		files { "../src/main/native/**.h", "../src/main/native/**.cpp" }
		includedirs {
			"../src/main/native",
			"../src/main/native/include/jni",
			"../src/main/native/include/jni/linux",
			"../sdk/public",
			"../sdk/public/steam" }

		defines { "NDEBUG", "LINUX" }
		flags { "Optimize" }

		configuration "x32"
			libdirs { "../sdk/redistributable_bin/linux32" }
			links { "steam_api" }

		configuration "x64"
			libdirs { "../sdk/redistributable_bin/linux64" }
			links { "steam_api" }

		platforms { "x32", "x64" }
