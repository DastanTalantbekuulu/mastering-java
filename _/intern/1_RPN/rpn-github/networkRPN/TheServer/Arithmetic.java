
public class Arithmetic {
    public static String makeOperation(String a, String b, char operator) {
        double da = Double.parseDouble(a);
        double db = Double.parseDouble(b);
        double res = 0;
        switch (operator) {
          case '+':
              res = da + db;
              break;
          case '-':
              res = da - db;
              break;
          case '*':
              res = da * db;
              break;
          case '/':
              res = da / db;
              break;
          case '~':
              res = -Math.abs(da);
              break;
          default:
              res = 0;
              break;
        }

        return res + "";
    }

    public static int getPriority(char c) {
        int priority = 0;
        switch (c) {
          case '+':
          case '-':
              priority = 1;
              break;
          case '*':
          case '/':
              priority = 2;
              break;
          case '~':
              priority = 101;
              break;
          default:
              priority = 0;
              break;
          }

        return priority;
    }

    public static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public static boolean isPoint(char c) {
        return '.' == c || ',' == c;
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '~';
    }

    public static boolean isBracket(char c) {
        return c == '(' || c == ')';
    }

}
