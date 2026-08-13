package com.programs.challenges.hackerrank.jumpingclouds;

/**
 * Emma is playing a new mobile game that starts with consecutively numbered clouds.
 * Some of the clouds are thunderheads and others are cumulus.
 * She can jump on any cumulus cloud having a number that is equal to the number of the current cloud plus 1 or 2.
 * She must avoid the thunderheads. Determine the minimum number of jumps it will take Emma to jump from her starting position to the last cloud.
 * It is always possible to win the game.
 * <p>
 * For each game, Emma will get an array of clouds numbered 0 if they are safe or 1 if they must be avoided.
 * For example, c=[0,1,0,0,0,1,0] indexed from 0...6. The number on each cloud is its index in the list so she must avoid the clouds at indexes 1 and 5.
 * She could follow the following two paths: 0->2->4->6 or 0->2->3->4->6.
 * The first path takes jumps 3 while the second takes 4.
 * <p>Complete the jumpingOnClouds function in the editor below.
 * It should return the minimum number of jumps required, as an integer</p>
 * <p>
 * <b>Sample Input</b>
 * <p>
 * 7
 * </p>
 * <p>0 0 1 0 0 1 0</p>
 * </p>
 * <b>Sample Output</b>
 * 4
 */
public class JumpingClouds {
    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {
        if (c.length < 1) {
            return 0;
        }
        int len = c.length - 1;

        int jump = 0;
        int current = 0;

        while (current < len) {
            if (current + 1 <= len && c[current + 1] == 0) {
                if (current + 2 <= len && c[current + 2] == 0) {
                    jump += 1;
                    current += 2;
                } else {
                    jump += 1;
                    current++;
                }
            } else if (current + 1 <= len && c[current + 1] == 1) {
                if (current + 2 <= len && c[current + 2] == 0) {
                    jump += 1;
                    current += 2;
                }
            }
        }

        return jump;

    }

    public static void main(String[] args) {
        int[] c = {0, 0, 1, 0, 0, 1, 0, 1, 0};
        int result = jumpingOnClouds(c);

        System.out.println(result);
    }
}
