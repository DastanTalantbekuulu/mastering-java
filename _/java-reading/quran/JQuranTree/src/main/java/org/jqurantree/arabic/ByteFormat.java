package org.jqurantree.arabic;

public class ByteFormat {

	private ByteFormat() {
	}

	static {
		new ByteFormat();
	}

	public static final int CHARACTER_WIDTH = 3;

	public static final byte WHITESPACE = -1;

	private static final int[] diacriticOffsets = { 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 2, 2, 2 };
	private static final int[] diacriticMasks = { 1, 2, 4, 8, 16, 32, 64, 128,
			1, 2, 4, 8, 16 };

	public static void setDiacritic(byte[] buffer, int offset,
			DiacriticType diacriticType) {

		int value = diacriticType.ordinal();

		int byteOffset = offset + diacriticOffsets[value];

		int bitMask = diacriticMasks[value];

		buffer[byteOffset] |= bitMask;
	}

	public static boolean isDiacritic(byte[] buffer, int offset,
			DiacriticType diacriticType) {

		int value = diacriticType.ordinal();

		int byteOffset = offset + diacriticOffsets[value];

		int bitMask = diacriticMasks[value];

		return (buffer[byteOffset] & bitMask) != 0;
	}

	public static boolean isFatha(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 1) != 0;
	}

	public static boolean isDamma(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 2) != 0;
	}

	public static boolean isKasra(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 4) != 0;
	}

	public static boolean isFathatan(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 8) != 0;
	}

	public static boolean isDammatan(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 16) != 0;
	}

	public static boolean isKasratan(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 32) != 0;
	}

	public static boolean isShadda(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 64) != 0;
	}

	public static boolean isSukun(byte[] buffer, int offset) {
		return (buffer[offset + 1] & 128) != 0;
	}

	public static boolean isMaddah(byte[] buffer, int offset) {
		return (buffer[offset + 2] & 1) != 0;
	}

	public static boolean isHamzaAbove(byte[] buffer, int offset) {
		return (buffer[offset + 2] & 2) != 0;
	}

	public static boolean isHamzaBelow(byte[] buffer, int offset) {
		return (buffer[offset + 2] & 4) != 0;
	}

	public static boolean isHamzatWasl(byte[] buffer, int offset) {
		return (buffer[offset + 2] & 8) != 0;
	}

	public static boolean isAlifKhanjareeya(byte[] buffer, int offset) {
		return (buffer[offset + 2] & 16) != 0;
	}

	public static boolean isSingleDiacritic(byte[] buffer, int offset,
			DiacriticType diacriticType) {

		int ordinal = diacriticType.ordinal();
		int diacriticOffset = diacriticOffsets[ordinal];
		int bitMask = diacriticMasks[ordinal];

		return buffer[offset + diacriticOffset] == bitMask
				&& buffer[offset + 3 - diacriticOffset] == 0;
	}

	public static int getDiacriticCount(byte[] buffer, int offset) {

		int count = 0;

		for (int i = 0; i < 13; i++) {
			if ((buffer[offset + diacriticOffsets[i]] & diacriticMasks[i]) != 0) {
				count++;
			}
		}
		return count;
	}

	public static boolean isLetter(byte[] buffer, int offset) {
		return buffer[offset] <= CharacterType.Tatweel.ordinal();
	}
}
