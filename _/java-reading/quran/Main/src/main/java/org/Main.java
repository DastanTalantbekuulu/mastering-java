package org;

import org.examples.analysis.*;
import org.examples.orthography.BismillahExample;
import org.examples.orthography.BuckwalterExample;
import org.examples.orthography.LocationExample;
import org.examples.orthography.SimpleEncodingExample;
import org.examples.search.LetterSearchExample;
import org.examples.search.TokenSearchExample;
import org.jqurantree.analysis.AnalysisTable;
import org.jqurantree.arabic.ArabicText;
import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.orthography.Quran;
import org.jqurantree.orthography.Aya;
import org.jqurantree.search.SearchOptions;
import org.jqurantree.search.TokenSearch;

public class Main {
    public static void main(String args[]) {
//        AnalysisTableExample.main();
//        ChapterInitialsExample.main();
//        CharacterFrequencyExample.main();
//        CsvExportExample.main();
//        LongestTokenExample.main();
//        TokenCountExample.main();
//        TokenFrequencyExample.main();
//        VerseCountExample.main();
//        BuckwalterExample.main();

//        BismillahExample.main();
//        BuckwalterExample.main();
//        LocationExample.main();
//        SimpleEncodingExample.main();

//        LetterSearchExample.main();
//        TokenSearchExample.main();
        run();
    }

    private static void run() {
//        xml  107-4 "فَوَيْلٌ لِّلْمُصَلِّينَ"
        ArabicText arabicText = ArabicText.fromUnicode("فَوَيْلٌ");

//        sql
//        ArabicText arabicText2 = ArabicText.fromUnicode("فَوَيْلٌ");

        System.out.println(arabicText);
        System.out.println(arabicText.toBuckwalter());

//        System.out.println(arabicText2);
//        System.out.println(arabicText2.toBuckwalter());

        TokenSearch search = new TokenSearch(EncodingType.Buckwalter);

        search.findSubstring(arabicText.toBuckwalter());
        search.findToken(arabicText.toBuckwalter());

        AnalysisTable table = search.getResults();

        System.out.println(table);
        System.out.println("Matches: " + table.getRowCount() + "\r\n");


//=========================================================================================================


//        TokenSearch search1 = new TokenSearch(EncodingType.Buckwalter, SearchOptions.RemoveDiacritics);
//
//        search.findToken("fawayolN]");
//
//        AnalysisTable table1 = search1.getResults();
//        System.out.println(table1);
//        System.out.println("Matches: " + table1.getRowCount());
    }
}
