
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle {

    private final RPNModel model;

    Puzzle(String first, String second, String summ, boolean add) {

        model = new RPNModel(first, second, summ, add);

        solution(model.getDigits(), 0, model.getUsedDigits(), model.getLetterToDigit(), model);
    }

    private static void solution(
            int[] digits,
            int index,
            Set<Integer> usedDigits,
            Map<Character, Integer> letterToDigit,
            RPNModel model) {
        if (index == model.getLetters().length) {
            // Если все буквы сопоставлены с цифрами, проверяем правильность уравнения
            if (isValid(letterToDigit, model)) {
                System.out.println("Result: " + letterToDigit);
            }
            return;
        }

        char currentLetter = model.getLetters()[index];

        // Перебираем все возможные цифры для текущей буквы
        for (int digit : digits) {
            if (!usedDigits.contains(digit)) {
                // Пробуем сопоставить текущую букву с цифрой
                letterToDigit.put(currentLetter, digit);
                usedDigits.add(digit);

                // Переходим к следующей букве
                solution(digits, index + 1, usedDigits, letterToDigit, model);

                // Отменяем выбор (backtracking)
                usedDigits.remove(digit);
                letterToDigit.remove(currentLetter);
            }
        }
    }

    private static int wordToNumber(String word, Map<Character, Integer> letterToDigit) {
        int number = 0;
        for (char ch : word.toCharArray()) {
            number = number * 10 + letterToDigit.get(ch);
        }
        return number;
    }

    private static boolean isValid(Map<Character, Integer> letterToDigit, RPNModel model) {
        int first = wordToNumber(model.getFirst(), letterToDigit);
        int second = wordToNumber(model.getSecond(), letterToDigit);
        int summ = wordToNumber(model.getSumm(), letterToDigit);
        return first + second == summ;
    }

    public RPNModel getModel() {
        return model;
    }

}

class Model {

    private final String FIRST;
    private final String SECOND;
    private final String SUMM;
    private final boolean ADD;

    private final char[] LETTERS;

    private final int[] DIGITS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    private final Set<Integer> usedDigits = new HashSet<>(DIGITS.length);
    private final Map<Character, Integer> letterToDigit = new HashMap<>();

    Model(String first, String second, String summ, boolean add) {
        FIRST = first;
        SECOND = second;
        SUMM = summ;
        ADD = add;

        String letters = first + second + summ;

        for (int i = 0; i < letters.length(); i++) {
            letterToDigit.put(letters.charAt(i), null);
        }

        LETTERS = letterToDigit.keySet().stream()
                .map(String::valueOf)
                .collect(Collectors.joining()).toCharArray();
    }

    public String getFirst() {
        return FIRST;
    }

    public String getSecond() {
        return SECOND;
    }

    public String getSumm() {
        return SUMM;
    }

    public char[] getLetters() {
        return LETTERS;
    }

    public int[] getDigits() {
        return DIGITS;
    }

    public Set<Integer> getUsedDigits() {
        return usedDigits;
    }

    public Map<Character, Integer> getLetterToDigit() {
        return letterToDigit;
    }

    public boolean isAdd() {
        return ADD;
    }
}
