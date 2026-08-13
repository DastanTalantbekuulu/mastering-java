package diffarray;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiffArray1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();
        List<Integer> arr1 = new ArrayList<>(n);
        while (--n >= 0) {
            arr1.add(scanner.nextInt());
        }
        
        n = scanner.nextInt();
        List<Integer> arr2 = new ArrayList<>(n);
        while (--n >= 0) {
            arr2.add(scanner.nextInt());
        }

        set(arr1);
        set(arr2);
        compare(arr1, arr2);

        if (arr1.size() > 0 || arr2.size() > 0) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
        }
    }

    private static void compare(List<Integer> array1, List<Integer> array2) {
        boolean flag = false;
        for (int i = 0; i < array1.size(); i++) {
            for (int j = 0; j < array2.size(); j++) {
                if (array1.get(i).equals(array2.get(j))) {
                    flag = true;
                    array2.remove(j);
                    j = j - 1;
                }
            }
            if (flag) {
                array1.remove(i);
                i = i - 1;
                flag = false;
            }
        }
    }

    private static void set(List<Integer> array) {
        for (int i = 0; i < array.size(); i++) {
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(i).equals(array.get(j))) {
                    array.remove(j);
                    j = j - 1;
                }
            }
        }
    }
}
