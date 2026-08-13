package org.jqurantree.core.error;

public class Errors {

	static {
		new Errors();
	}

	private Errors() {
	}
	public static int getErrorCount() {
		return 10;
	}
	public static final String INVALID_TANZIL_XML = "Failed to read Tanzil XML.";
	public static final String INVALID_CHAPTER_NUMBER = "Chapter numbers should be between 1 and 114 inclusive.";
	public static final String INVALID_VERSE_NUMBER = "The verse number is out of range.";
	public static final String IMMUTABLE_COLLECTION = "The collection is immutable and cannot be modified.";
	public static final String INVALID_ENCODING_TYPE = "The specified encoding type is not supported.";
	public static final String INVALID_COLUMN_NAME = "The specific column could not be found.";
	public static final String RESOURCE_NOT_FOUND = "The embedded JAR resource could not be found.";
	public static final String RESOURCE_CLOSE_FAILED = "Failed to close embedded JAR resource.";
	public static final String RESOURCE_READ_FAILED = "Failed to read embedded JAR resource.";
	public static final String FILE_NOT_FOUND = "The specified file could not be found.";
}
