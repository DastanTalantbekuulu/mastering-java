import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CharacterSolution {

    public static void main(String[] args) {
        // ==============================================================================================================
        // Set<String> languages = Arrays.stream(Locale.getISOLanguages())
        // .map(Locale::new)
        // .map(Locale::getDisplayLanguage)
        // .collect(Collectors.toCollection(TreeSet::new));
        // languages.forEach(System.out::println);
        // ==============================================================================================================

        // ==============================================================================================================
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        charsets.forEach((key, charset) -> {
            System.out.println(key + " : " + charset.toString());
        });
        // ==============================================================================================================

        // ==============================================================================================================
        // Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.ARABIC;
        // System.out.println(unicodeBlock);
        // ==============================================================================================================

        // ==============================================================================================================
        // for (Character.UnicodeScript unicodeScript :
        // Character.UnicodeScript.values()) {
        // System.out.println(unicodeScript);
        // }
        // ==============================================================================================================
    }
}