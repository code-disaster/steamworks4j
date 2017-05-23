solution "steamworks4j"
	configurations { "ReleaseDLL" }

	buildoptions {
		"-std=c++0x",
		"-Wall",
		"-mmacosx-version-min=10.6"
	}

	includedirs {
		"../java-wrapper/src/main/native/include/jni",
		"../java-wrapper/src/main/native/include/jni/mac",
		"../sdk/public/steam",
		"/System/Library/Frameworks/JavaVM.framework/Headers"
	}

	defines {
		"NDEBUG",
		"MACOSX"
	}

	flags {
		"Optimize"
	}

	linkoptions {
		"-framework CoreFoundation",
		"-mmacosx-version-min=10.6"
	}

	platforms {
		"universal"
	}

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		files {
			"../java-wrapper/src/main/native/**.h",
			"../java-wrapper/src/main/native/**.cpp"
		}

		includedirs {
			"../java-wrapper/src/main/native"
		}

		libdirs {
			"../sdk/redistributable_bin/osx32"
		}

		links {
			"steam_api"
		}

	project "steamworks4j-encryptedappticket"

		kind "SharedLib"
		language "C++"

		files {
			"../server/src/main/native/**.h",
			"../server/src/main/native/**.cpp"
		}

		includedirs {
			"../server/src/main/native"
		}

		libdirs {
			"../sdk/redistributable_bin/osx32",
			"../sdk/public/steam/lib/osx32"
		}

		links {
		    "steam_api",
			"sdkencryptedappticket"
		}
