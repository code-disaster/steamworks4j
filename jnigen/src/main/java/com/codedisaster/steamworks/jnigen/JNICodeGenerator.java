package com.codedisaster.steamworks.jnigen;

import com.badlogic.gdx.jnigen.NativeCodeGenerator;

public class JNICodeGenerator {

	public static void main(String[] arguments) {

		try {
			new NativeCodeGenerator().generate(
					"java-wrapper/src/main/java",
					"java-wrapper/target/classes",
					"java-wrapper/src/main/native",
					new String[] { "**/*.java" },
					new String[] { "**/SteamSharedLibraryLoader.java" });

			new NativeCodeGenerator().generate(
					"server/src/main/java",
					"server/target/classes",
					"server/src/main/native",
					new String[] { "**/*Native.java" },
					null);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
