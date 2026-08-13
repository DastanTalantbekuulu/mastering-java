package org.jqurantree.arabic.encoding;
public interface ArabicEncoder {
	public String encode(byte[] buffer, int offset, int characterCount,
			EncodingOptions options);
}
