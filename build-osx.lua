solution "steamworks4j"
	configurations { "ReleaseDLL" }

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		buildoptions { "-Wall", "-mmacosx-version-min=10.6" }

		files { "src/main/native/**.h", "src/main/native/**.cpp" }
		includedirs { "src/main/native", "sdk/public", "sdk/public/steam", "/System/Library/Frameworks/JavaVM.framework/Headers" }

		defines { "NDEBUG", "MACOSX" }
		flags { "Optimize" }

		libdirs { "sdk/redistributable_bin/osx32" }
		links { "steam_api" }
		linkoptions { "-framework CoreFoundation", "-mmacosx-version-min=10.6" }

		platforms { "universal" }
