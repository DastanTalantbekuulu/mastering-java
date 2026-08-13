public class MyStack {
    private int maxSize;
    private int top;
    private String[] stackArray;

    // Constructor
    public MyStack() {
        maxSize = 10;
        stackArray = new String[maxSize];
        top = -1;
    }

    // add element
    public void push(String j) {
        if (top >= maxSize - 1) {
            stackArray = copy(stackArray, maxSize * 2);
            maxSize = maxSize * 2;
        }

        top++;
        stackArray[top] = j;
    }

    // copy array
    public String[] copy(String[] stackArray, int newSize) {
        String[] newStackArray = new String[newSize];
        System.arraycopy(stackArray, 0, newStackArray, 0, stackArray.length);
        return newStackArray;
    }


    // delete last element
    public String pop() {
        if (top >= 0) {
            return stackArray[top--];
        }
        throw new IllegalStateException("Stack is empty");
    }

    // get last element
    public String peek() {
        if (top >= 0) {
            return stackArray[top];
        }
        throw new IllegalStateException("Stack is empty");
    }


    // check if stack is empty
    public boolean empty() {
        return (top == -1);
    }

    // get size of stack
    public int size() {
        return (top + 1);
    }

    // get string representation of stack
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= top; i++) {
            sb.append(stackArray[i]).append(" ");
        }
        return sb.toString();
    }

    // get element at index
    public String get(int index) {
        if (top < index) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return stackArray[index];
    }


}
