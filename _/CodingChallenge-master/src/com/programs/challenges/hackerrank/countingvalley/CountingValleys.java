package com.programs.challenges.hackerrank.countingvalley;

/**
 * Gary is an avid hiker. He tracks his hikes meticulously, paying close attention to small details like topography. During his last hike he took exactly  steps.
 * For every step he took, he noted if it was an uphill, , or a downhill,  step.
 * Gary's hikes start and end at sea level and each step up or down represents a  unit change in altitude.
 * We define the following terms:
 *
 * <li>A mountain is a sequence of consecutive steps above sea level, starting with a step up from sea level and ending with a step down to sea level.</li>
 * <li>A valley is a sequence of consecutive steps below sea level, starting with a step down from sea level and ending with a step up to sea level.</li>
 * Given Gary's sequence of up and down steps during his last hike, find and print the number of valleys he walked through.
 *
 * For example, if Gary's path is s= [D D U U U U D D] , he first enters a valley 2 units deep.
 * Then he climbs out an up onto a mountain 2 units high. Finally, he returns to sea level and ends his hike.
 * <p>
 * <b>Sample Input</b>
 * 8
 * UDDDUDUU
 * </p>
 * <b>Sample Output</b>
 * 1
 */
public class CountingValleys {

    private static final char UP = 'U';
    private static final char DOWN = 'D';

    // Complete the countingValleys function below.
    static int countingValleys(int n, String s) {
        char previousStep = 'N';
        int currentLevel = 0;
        int valleyEntered = 0;
        if(n == 0 || s == null){
            return 0;
        }

        for(char c : s.toCharArray()){
            if(UP == c){
                ++currentLevel;
                if ((DOWN == previousStep || UP == previousStep) && currentLevel == 0) {
                    ++valleyEntered;
                }
                previousStep = UP;
            }else if(DOWN == c){
                --currentLevel;
                previousStep = DOWN;
            }
        }

        return valleyEntered;

    }

    public static void main(String[] args) {
        String s = "DUDDDUUDUU";

        int result = countingValleys(s.length(), s);

        System.out.println(result);
    }
}
