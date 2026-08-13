
public class MyStack {
    private Token[] array;
    private int top;

    public MyStack() {
        array = getArray(10);
        top = -1;
    }

    public void push(Token item) {
        if (top == array.length - 1) {
            array = getArray(array, array.length / 2);
        }
        array[++top] = item;
    }

    public Token pop() {
        if (top == -1) {
            throw new IllegalStateException("Stack underflow");
        }
        Token item = array[top];
        array[top--] = null;
        return item;
    }

    public Token peek() {
        if (top == -1) {
            throw new IllegalStateException("Stack is empty");
        }
        return array[top];
    }

    public Token get(int index) {
        if (top == -1) {
            throw new IllegalStateException("Stack is empty");
        }
        return array[index];
    }

    public boolean empty() {
        return (top == -1);
    }

    public void clear() {
        while(top > -1) {
            array[top--] = null;
        }
    }

    public int size() {
        return (top + 1);
    }


    private Token[] getArray(int capacity, Token[] array) {
        Token[] newArray = getArray(capacity);
        for (int i = 0; i < capacity; i++) {
            if (array.length == i) {
                break;
            }
            newArray[i] = array[i];
        }
        return newArray;
    }

    private Token[] getArray(int capacity) {
        return new Token[capacity];
    }

    private Token[] getArray(Token[] array, int add) {
        return getArray(array.length + add, array);
    }

    @Override
    public String toString() {
        if (top != -1) {
            String str = "";
            for (int i = 0; i < top; i++) {
                str = str + array[i] + " ";
            }
            str = str + array[top];
            return str;
        }
        return "";
    }
}