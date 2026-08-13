import java.util.Scanner;

public class TaskNumber1791 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String lineOne = scanner.nextLine();
        String lineTwo = scanner.nextLine();

        int firstWord = lineOne.length();
        int secondWord = lineTwo.length();

        int[][] matrix = new int[firstWord + 1][secondWord + 1];

        for (int i = 0; i <= firstWord; i++ ) {
          for (int j = 0; j <= secondWord; j++ ) {
            if( i == 0) {
              matrix[i][j] = j;

            } else if (j == 0) {
              matrix[i][j] = i;

            } else {
              if (lineOne.charAt(i - 1) == lineTwo.charAt(j - 1)){
                matrix[i][j] = matrix[i - 1][j - 1];

              } else {
                int delete = matrix[i - 1][j] + 1;
                int paste = matrix[i][j - 1] + 1;
                int replace = matrix[i - 1][j - 1] + 1;
                matrix[i][j] = Math.min(delete, Math.min(paste, replace));
              }
            }

          }
        }
        System.out.println(matrix[firstWord][secondWord]);
  }
}
