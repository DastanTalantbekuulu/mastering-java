/**
 *
 * @author pranshu 
 *
 */
package com.programs.challenges.collections.diskBackedMap.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

public class FileUtils {

	protected static final int BUFFER_SIZE = 4096;

	private FileUtils() { }

	protected static String getStringFromReader(Reader in)
			throws IOException {
		StringBuffer outBuffer = new StringBuffer();

		char[] inBuffer = new char[BUFFER_SIZE];
		int read;
		while ((read = in.read(inBuffer, 0, BUFFER_SIZE)) != -1)
			outBuffer.append(inBuffer, 0, read);

		in.close();

		return outBuffer.toString();
	}

	/**
	 * Deletes the file directory
	 * @param dir
	 */
	public static void deleteDirectory(File dir) {
		if (!dir.isDirectory())
			return;

		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory())
				deleteDirectory(f);
			else
				f.delete();
		}

		dir.delete();
	}
}
