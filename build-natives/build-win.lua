solution "steamworks4j"
	configurations { "ReleaseDLL" }

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		-- buildoptions { "-Wall" }
		
		files { "../src/main/native/**.h", "../src/main/native/**.cpp" }
		includedirs {
			"../src/main/native",
			"../src/main/native/include/jni",
			"../src/main/native/include/jni/win32",
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
