public class MyStack {
    private String[] array;
    private int top;

    public MyStack() {
        array = new String[10];
        top = -1;
    }

    public void push(String item) {
        if (top == array.length - 1) {
            array = expandArray(array.length * 2);
        }
        array[++top] = item;
    }

    public String pop() {
        if (empty()) {
            throw new IllegalStateException("Stack underflow");
        }
        String item = array[top];
        array[top--] = null;
        return item;
    }

    public String peek() {
        if (empty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return array[top];
    }

    public String get(int index) {
        if (index < 0 || index > top) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return array[index];
    }

    public boolean empty() {
        return top == -1;
    }

    public void clear() {
        for (int i = 0; i <= top; i++) {
            array[i] = null;
        }
        top = -1;
    }

    public int size() {
        return top + 1;
    }

    private String[] expandArray(int newCapacity) {
        String[] newArray = new String[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            if (array.length == i) {
                break;
            }
            newArray[i] = array[i];
        }
        return newArray;
    }

    @Override
    public String toString() {
        if (empty()) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <= top; i++) {
            str.append(array[i]);
            if (i < top) {
                str.append(" ");
            }
        }
        return str.toString();
    }
}


