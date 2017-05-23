solution "steamworks4j"
	configurations { "release" }
	platforms { "x32", "x64" }

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

	flags {
		"Optimize"
	}

	project "steamworks4j"

		kind "SharedLib"
		language "C++"

		files {
			"../java-wrapper/src/main/native/**.h",
			"../java-wrapper/src/main/native/**.cpp"
		}

		includedirs {
			"../java-wrapper/src/main/native",
		}

		configuration "x32"
			libdirs {
				"../sdk/redistributable_bin/linux32"
			}
			links {
				"steam_api"
			}

		configuration "x64"
			libdirs {
				"../sdk/redistributable_bin/linux64"
			}
			links {
				"steam_api"
			}

	project "steamworks4j-server"

		kind "SharedLib"
		language "C++"

		files {
			"../server/src/main/native/**.h",
			"../server/src/main/native/**.cpp"
		}

		includedirs {
			"../server/src/main/native",
		}

		configuration "x32"
			libdirs {
				"../sdk/redistributable_bin/linux32",
				"../sdk/public/steam/lib/linux32"
			}
			links {
				"steam_api",
				"sdkencryptedappticket"
			}

		configuration "x64"
			libdirs {
				"../sdk/redistributable_bin/linux64",
				"../sdk/public/steam/lib/linux64"
			}
			links {
				"steam_api",
				"sdkencryptedappticket"
			}
