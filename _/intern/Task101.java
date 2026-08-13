import java.util.Scanner;

public class Task101 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        int stringLength = inputString.length();

        int[] prefixFunction = new int[stringLength];

        for (int currentIndex = 1; currentIndex < stringLength; currentIndex++) {
            int previousPrefixValue = prefixFunction[currentIndex - 1];
            while (previousPrefixValue > 0 && inputString.charAt(currentIndex) != inputString.charAt(previousPrefixValue)) {
                previousPrefixValue = prefixFunction[previousPrefixValue - 1];
            }
            if (inputString.charAt(currentIndex) == inputString.charAt(previousPrefixValue)) {
                previousPrefixValue++;
            }
            prefixFunction[currentIndex] = previousPrefixValue;
        }

        int minimalOriginalLength = stringLength - prefixFunction[stringLength - 1];

        System.out.println(minimalOriginalLength);
    }
}
