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

package org.jqurantree.examples.analysis;

import org.jqurantree.analysis.AnalysisTable;
import org.jqurantree.orthography.Sura;
import org.jqurantree.orthography.Quran;

/**
 * This example uses an analysis table to tabulate the number of verses in each
 * chapter in the Quran.
 * 
 * @author Kais Dukes
 */
public class VerseCountExample {

	public static void main() {

		// Create a new analysis table.
		AnalysisTable table = new AnalysisTable("ChapterNumber", "VerseCount");

		// Tabulate the number of verses in each chapter.
		for (Sura sura : Quran.getSuras()) {
			table.add(sura.getSuraNumber(), sura.getAyaCount());
		}

		// Display the results.
		System.out.println(table);
	}
}
