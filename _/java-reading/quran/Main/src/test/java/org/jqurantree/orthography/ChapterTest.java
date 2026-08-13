/* Copyright (C) Kais Dukes, 2009.
 * 
 * This file is part of JQuranTree.
 * 
 * JQuranTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JQuranTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JQuranTree. If not, see <http://www.gnu.org/licenses/>.
 */

package org.jqurantree.orthography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;
import org.junit.Test;

public class ChapterTest {

	@Test
	public void testLocation() {

		// Test location.
		Location location = Quran.getSura(82).getLocation();
		assertEquals(82, location.getSuraNumber());
	}

	@Test
	public void testVerseCount() {

		// Count verses.
		assertEquals(7, Quran.getSura(1).getAyaCount());
		assertEquals(286, Quran.getSura(2).getAyaCount());
		assertEquals(29, Quran.getSura(81).getAyaCount());
	}

	@Test
	public void testTokenCount() {

		// Count tokens.
		assertEquals(29, Quran.getSura(1).getTokenCount());
		assertEquals(6116, Quran.getSura(2).getTokenCount());
		assertEquals(104, Quran.getSura(81).getTokenCount());
	}

	@Test
	public void testName() {

		// Test chapter names.
		for (Sura sura : Quran.getSuras()) {
			assertTrue(sura.getName().getLength() > 0);
		}
	}

	@Test
	public void testBismillah() {

		// Test bismillah for each chapter.
		for (Sura sura : Quran.getSuras()) {
			if (sura.getSuraNumber() == 1
					|| sura.getSuraNumber() == 9) {
				assertNull(sura.getBismillah());
			} else {
				assertTrue(sura.getBismillah().getLength() > 0);
			}
		}
	}

	@Test
	public void testEnumerateVerses() {

		// Test that we can enumerate through each verse for chapter 2.
		int verseNumber = 0;
		for (Aya aya : Quran.getSura(2)) {
			assertEquals(++verseNumber, aya.getAyaNumber());
		}

		// We should have read 286 verses.
		assertEquals(286, verseNumber);
	}

	@Test
	public void testGetInvalidVerse() {

		// Initiate chapter.
		Sura sura = Quran.getSura(81);

		// Get invalid verse.
		try {
			sura.getAya(0);
			fail();
		} catch (QuranException exception) {
			assertEquals(Errors.INVALID_VERSE_NUMBER, exception.getMessage());
		}
	}

	@Test
	public void testGetInvalidLocation() {

		// Initiate chapter and location.
		Sura sura = Quran.getSura(81);
		Location location = new Location(81, 100);

		// Get invalid location.
		try {
			sura.getAya(location);
			fail();
		} catch (QuranException exception) {
			assertEquals(Errors.INVALID_VERSE_NUMBER, exception.getMessage());
		}
	}
}
