import org.jqurantree.analysis.AnalysisTable;
import org.jqurantree.analysis.SortOrder;
import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.orthography.Document;
import org.jqurantree.orthography.Location;
import org.jqurantree.orthography.Token;
import org.jqurantree.orthography.Verse;
import org.jqurantree.search.TokenSearch;

public class Main
{
    public static void main( String[] args )
    {
        Location location = new Location(1,7,1);
        System.out.println(Document.getVerse(location));
        System.out.println(Document.getToken(1,7,1));
//        getLocation(1,7);
//        getBuckwalter(2,282,1);
//        // Search #1: The Sun.
//        searchSun();
//
//        // Search #2: The Moon.
//        searchMoon();
//        get();
    }
    private static void getLocation(int s, int v){
        // Get verse (27:30) by location.
        Location location = new Location(s, v);
        Verse verse = Document.getVerse(location);

        // Display the verse.
        System.out.println(verse);
    }
    private static void getBuckwalter(int s, int v,int t){
        // Get token #6 of verse (19:7).
        Token token = Document.getToken(s, v, t);

        // Display the token using Buckwalter transliteration.
        System.out.println(token);
    }

    private static void searchSun() {

        // Search for substring "$~amos" or exact token "$amosFA"
        TokenSearch search = new TokenSearch(EncodingType.Buckwalter);
        search.findSubstring("$~amos");
        search.findToken("$amosFA");

        // Display the results.
        AnalysisTable table = search.getResults();
        System.out.println(table);
        System.out.println("Matches: " + table.getRowCount() + "\r\n");
    }

    private static void searchMoon() {

        // Search for substring "qamar" or exact token "lo>ahil~api"
        TokenSearch search = new TokenSearch(EncodingType.Buckwalter);
        search.findSubstring("qamar");
        search.findToken("{lo>ahil~api");

        // Display the results.
        AnalysisTable table = search.getResults();
        System.out.println(table);
        System.out.println("Matches: " + table.getRowCount() + "\r\n");
    }

    private static void get(){

        // -----------------
        // Tabulate and sort
        // -----------------

        // Step 1. Create a new analysis table.
        AnalysisTable table = new AnalysisTable(
                "ChapterNumber", "VerseNumber", "TokenCount");

        // Step 2. Tabulate the number of tokens in each verse.
        for (Verse verse : Document.getVerses()) {
            table.add(
                    verse.getChapterNumber(),
                    verse.getVerseNumber(),
                    verse.getTokenCount());
        }

        // Step 3. Sort the table, then display the first 5 rows.
        table.sort("TokenCount", SortOrder.Descending);
        System.out.println(table.toString(5));

        // -------------
        // Group results
        // -------------

        // Group the token count table by number of tokens.
        AnalysisTable groupTable = table.group("TokenCount");

        // Sort the group table by the Count column, in descending order.
        groupTable.sort("Count", SortOrder.Descending);

        // Display the first 5 rows of the group table.
        System.out.println(groupTable.toString(5));
    }
}
