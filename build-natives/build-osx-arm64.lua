solution "steamworks4j-arm64"
	configurations { "ReleaseDLL" }

	buildoptions {
		"-std=c++0x",
		"-Wall",
		"--target=aarch64-apple-macos10.9"
	}

	sysincludedirs {
		"../java-wrapper/src/main/native/include/jni",
		"../java-wrapper/src/main/native/include/jni/mac",
		"../sdk/public/steam",
		"/System/Library/Frameworks/JavaVM.framework/Headers"
	}

	defines {
		"NDEBUG",
		"MACOSX"
	}

	optimize "On"

	linkoptions {
		"-framework CoreFoundation",
		"--target=aarch64-apple-macos10.9"
	}

	architecture "aarch64"

	project "steamworks4j-arm64"

		kind "SharedLib"
		language "C++"

		files {
			"../java-wrapper/src/main/native/**.cpp"
		}

		sysincludedirs {
			"../java-wrapper/src/main/native"
		}

		libdirs {
			"../sdk/redistributable_bin/osx"
		}

		links {
			"steam_api"
		}

	project "steamworks4j-arm64-server"

		kind "SharedLib"
		language "C++"

		files {
			"../server/src/main/native/**.cpp"
		}

		excludes {
			"../server/src/main/native/**EncryptedAppTicket*.cpp"
		}

		sysincludedirs {
			"../server/src/main/native"
		}

		libdirs {
			"../sdk/redistributable_bin/osx"
		}

		links {
		    "steam_api"
		}

	project "steamworks4j-arm64-encryptedappticket"

		kind "SharedLib"
		language "C++"

		files {
			"../server/src/main/native/**EncryptedAppTicket*.cpp"
		}

		sysincludedirs {
			"../server/src/main/native"
		}

		libdirs {
			"../sdk/public/steam/lib/osx"
		}

		links {
			"sdkencryptedappticket"
		}
