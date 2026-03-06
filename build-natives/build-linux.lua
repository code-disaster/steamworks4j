solution "steamworks4j"
	configurations { "release" }
	platforms { "x64", "arm64" }

	buildoptions {
		"-std=c++11",
		"-Wall"
	}

	includedirs {
		"../java-wrapper/src/main/native/include/jni",
		"../java-wrapper/src/main/native/include/jni/linux",
		"../sdk/public/steam"
	}

	defines {
		"NDEBUG",
		"LINUX"
	}

	steam_arch = {
		x86_64 = "linux64",
		ARM64  = "linuxarm64"
	}

	filter "platforms:x64"
		architecture "x86_64"
	filter "platforms:arm64"
		architecture "ARM64"
	filter {}

	libdirs { "../sdk/redistributable_bin/%{steam_arch[cfg.architecture]}" }

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		files {
			"../java-wrapper/src/main/native/**.cpp"
		}

		includedirs {
			"../java-wrapper/src/main/native",
		}

        	links {
        	    "steam_api"
        	}

	project "steamworks4j-server"

		kind "SharedLib"
		language "C++"

		files {
			"../server/src/main/native/**.cpp"
		}

		excludes {
			"../server/src/main/native/**EncryptedAppTicket*.cpp"
		}

		includedirs {
			"../server/src/main/native",
		}

        	links {
        	    "steam_api"
        	}

	project "steamworks4j-encryptedappticket"

		kind "SharedLib"
		language "C++"

		files {
			"../server/src/main/native/**EncryptedAppTicket*.cpp"
		}

		includedirs {
			"../server/src/main/native",
		}

	        libdirs { "../sdk/public/steam/lib/%{steam_arch[cfg.architecture]}" }

        	links {
        	    "sdkencryptedappticket"
        	}
