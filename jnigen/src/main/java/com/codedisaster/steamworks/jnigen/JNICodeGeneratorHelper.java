package com.codedisaster.steamworks.jnigen;

import com.badlogic.gdx.jnigen.NativeCodeGenerator;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JNICodeGeneratorHelper extends NativeCodeGenerator {

	private List<String> includes = new LinkedList<String>();
	private List<String> excludes = new LinkedList<String>();

	private String sourceDir;
	private String classpath;
	private String jniDir;

	private static final String INCLUDE_PREFIX = "in:";
	private static final String EXCLUDE_PREFIX = "ex:";

	private static final int SOURCE_DIR = 0;
	private static final int CLASSPATH = 1;
	private static final int JNI_DIR = 2;

	public static void main(String[] arguments) throws Exception {
		new JNICodeGeneratorHelper().parse(arguments).generate();
	}

	@Override
	public void generate() throws Exception {
		generate(
			sourceDir,
			classpath,
			jniDir,
			log("INCLUDES", transplant(sourceDir, includes)),
			log("EXCLUDES", transplant(sourceDir, excludes))
		);
	}

	private static <T> T[] log(String marker, T[] input) {
		if (input == null) {
			System.out.println(marker + ": null");
		} else {
			System.out.println(marker + ": " + Arrays.asList(input));
		}
		return input;
	}

	private static String[] transplant(final String to, final List<String> ptrns) {
		final int size = ptrns.size();
		if (size == 0) {
			return null;
		}
		final StringBuilder sb = new StringBuilder(to);
		for(int i = sb.length(); i > 0 && sb.charAt(--i) == File.separatorChar;) {
			sb.setLength(i);
		}
		if (sb.length() == 0 && !to.equals(File.separator)) {
			throw new IllegalArgumentException("Invalid path");
		}
		final String[] array = new String[size];
		final int pathLen = sb.length();
		int i = 0;
		for (String ptrn : ptrns) {
			array[i++] = sb.append(File.separatorChar).append(ptrn).toString();
			sb.setLength(pathLen);
		}
		return array;
	}

	private JNICodeGeneratorHelper parse(String[] arguments) {
		if (arguments.length < 3) {
			throw new IllegalArgumentException("Insufficient arguments");
		} else if (arguments.length == 3) {
			// no-op
		} else for (int i = 3; i < arguments.length; i++) {
			final String arg = arguments[i];
			if (arg.length() <= 3) {
				throw new IllegalArgumentException("Illegal argument format");
			} else {
				if (arg.startsWith(INCLUDE_PREFIX)) {
					includes.add(arg.substring(3));
				} else if (arg.startsWith(EXCLUDE_PREFIX)) {
					excludes.add(arg.substring(3));
				} else {
					throw new IllegalArgumentException("Illegal argument format");
				}
			}
		}

		sourceDir = arguments[SOURCE_DIR];
		classpath = arguments[CLASSPATH];
		jniDir = arguments[JNI_DIR];

		return this;
	}
}
