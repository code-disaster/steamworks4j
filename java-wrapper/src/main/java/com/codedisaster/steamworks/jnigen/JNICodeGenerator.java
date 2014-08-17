package com.codedisaster.steamworks.jnigen;

import com.badlogic.gdx.jnigen.NativeCodeGenerator;

public class JNICodeGenerator {

	public static void main(String[] arguments) {

		try {

			new NativeCodeGenerator().generate("src/main/java", "target/classes", "src/main/native",
					new String[] { "**/*.java" }, new String[] { "**/JNICodeGenerator.java", "**/SteamSharedLibraryLoader.java" });

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
