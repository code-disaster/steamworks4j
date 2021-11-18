solution "steamworks4j"
	configurations { "ReleaseDLL" }
	platforms { "x32", "x64" }

	includedirs {
		"../java-wrapper/src/main/native/include/jni",
		"../java-wrapper/src/main/native/include/jni/win32",
		"../sdk/public/steam"
	}

	defines {
		"NDEBUG",
		"WINDOWS"
	}

    optimize "On"
    staticruntime "On"

	buildoptions {
		"/wd4800",
		"/wd4996"
	}

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		files {
			"../java-wrapper/src/main/native/**.cpp"
		}

		includedirs {
			"../java-wrapper/src/main/native",
			"../sdk/public"
		}

		filter "platforms:x32"
			libdirs {
				"../sdk/redistributable_bin"
			}
			links {
				"steam_api"
			}

		filter "platforms:x64"
			libdirs {
				"../sdk/redistributable_bin/win64"
			}
			links {
				"steam_api64"
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
			"../server/src/main/native"
		}

		filter "platforms:x32"
			libdirs {
				"../sdk/redistributable_bin"
			}
			links {
				"steam_api"
			}

		filter "platforms:x64"
			libdirs {
				"../sdk/redistributable_bin/win64"
			}
			links {
				"steam_api64"
			}

	project "steamworks4j-encryptedappticket"

		kind "SharedLib"
		language "C++"

		files {
			"../server/src/main/native/**EncryptedAppTicket*.cpp"
		}

		includedirs {
			"../server/src/main/native"
		}

		filter "platforms:x32"
			libdirs {
				"../sdk/public/steam/lib/win32"
			}
			links {
				"sdkencryptedappticket"
			}

		filter "platforms:x64"
			libdirs {
				"../sdk/public/steam/lib/win64"
			}
			links {
				"sdkencryptedappticket64"
			}
