package diffarray;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiffArray2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] arr1 = new int[n];
        while (--n >= 0) {
            arr1[n] = scanner.nextInt();
        }

        n = scanner.nextInt();
        int[] arr2 = new int[n];
        while (--n >= 0) {
            arr2[n] = scanner.nextInt();
        }

        // set(arr1);
        // set(arr2);

        if (compare(arr1, arr2)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static boolean compare(int[] array1, int[] array2) {
        // for (int i = 0; i < array1.length; i++) {
        //     for (int j = 0; j < array2.length; j++) {
        //         if (array1[i] == array2[j]) {
        //             // array1[i] = 0;
        //             // array2[j] = 0;
        //             continue;
        //         }
        //         if(j == array2.length - 1) {
        //             return false;
        //         }
        //     }
        // }
        // for (int i = 0; i < array1.length; i++) {
        //     if (array1[i] != 0) {
        //         return false;
        //     }
        // }
        // for (int i = 0; i < array2.length; i++) {
        //     if (array1[i] != 0) {
        //         return false;
        //     }
        // }
        return true;
    }

    private static void set(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    array[j] = 0;
                }
            }
        }
    }
}
