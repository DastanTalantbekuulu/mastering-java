package org.jqurantree.orthography;

import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;

public class Location {
	private int suraNumber;
	private int ayaNumber;
	private int tokenNumber;
	public Location(int suraNumber) {
		this(suraNumber, 0, 0);
	}
	public Location(int suraNumber, int ayaNumber) {
		this(suraNumber, ayaNumber, 0);
	}
	public Location(int suraNumber, int ayaNumber, int tokenNumber) {
		validateSuraNumber(suraNumber);
		this.suraNumber = suraNumber;
		this.ayaNumber = ayaNumber;
		this.tokenNumber = tokenNumber;
	}
	public int getSuraNumber() {
		return suraNumber;
	}
	public int getAyaNumber() {
		return ayaNumber;
	}
	public int getTokenNumber() {
		return tokenNumber;
	}
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append('(');
		text.append(suraNumber);
		text.append(':');
		text.append(ayaNumber);
		if (tokenNumber > 0) {
			text.append(':');
			text.append(tokenNumber);
		}
		text.append(')');
		return text.toString();
	}
	static void validateSuraNumber(int suraNumber) {
		if (suraNumber < 1 || suraNumber > Quran.SURA_COUNT) {
			throw new QuranException(Errors.INVALID_CHAPTER_NUMBER);
		}
	}
}
