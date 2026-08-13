
package org.jqurantree.search;

import java.util.ArrayList;
import java.util.List;

import org.jqurantree.analysis.AnalysisTable;
import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.orthography.Quran;
import org.jqurantree.orthography.Token;

public class TokenSearch {

    private final List<SearchItem> items = new ArrayList<SearchItem>();
    private final EncodingType encodingType;
    private final SearchOptions options;

    public TokenSearch(EncodingType encodingType) {
        this(encodingType, null);
    }

    public TokenSearch(EncodingType encodingType, SearchOptions options) {
        this.encodingType = encodingType;
        this.options = options;
    }

    public void findToken(String text) {
        findToken(text, options);
    }

    public void findToken(String text, SearchOptions options) {
        items.add(new SearchItem(SearchType.Token, text, options));
    }

    public void findSubstring(String text) {
        findSubstring(text, options);
    }

    public void findSubstring(String text, SearchOptions options) {
        items.add(new SearchItem(SearchType.Substring, text, options));
    }

    public AnalysisTable getResults() {

        AnalysisTable table = new AnalysisTable("ChapterNumber", "VerseNumber",
                "TokenNumber", "Token");

        boolean isRemoveDiacritics = false;
        for (SearchItem item : items) {
            if (item.getOptions() == SearchOptions.RemoveDiacritics) {
                isRemoveDiacritics = true;
                break;
            }
        }

        for (Token token : Quran.getTokens()) {

            String diacriticText = token.toString(encodingType);
            String cleanText = isRemoveDiacritics ? token.removeDiacritics()
                    .toString(encodingType) : null;

            if (isMatch(diacriticText, cleanText)) {
                table.add(token.getSuraNumber(), token.getAyaNumber(),
                        token.getTokenNumber(), diacriticText);
            }
        }

        return table;
    }

    private boolean isMatch(String diacriticText, String cleanText) {

        boolean isValid = false;

        int size = items.size();
        for (int i = 0; i < size; i++) {
            if (isValid = isMatch(items.get(i), diacriticText, cleanText)) {
                break;
            }
        }

        return isValid;
    }

    private boolean isMatch(SearchItem item, String diacriticText, String cleanText) {

        boolean isValid;
        String text = item.getOptions() == SearchOptions.RemoveDiacritics ? cleanText : diacriticText;

        if (item.getType() == SearchType.Token) {
            isValid = text.equals(item.getText());
        } else {
            isValid = text.contains(item.getText());
        }

        return isValid;
    }
}
