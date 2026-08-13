package org.jqurantree.core.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;

public class FileWriter {

	private final PrintStream printStream;

	public FileWriter(String filename) {

		FileOutputStream stream;
		try {
			stream = new FileOutputStream(filename);
		} catch (FileNotFoundException exception) {
			throw new QuranException(Errors.FILE_NOT_FOUND, exception);
		}

		printStream = new PrintStream(stream);
	}

	public void write(char ch) {
		printStream.print(ch);
	}

	public void write(String text) {
		printStream.print(text);
	}

	public void writeLine() {
		printStream.println();
	}

	public void writeLine(String text) {
		printStream.println(text);
	}

	public void close() {
		printStream.close();
	}
}
