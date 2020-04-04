package lcms;

import java.util.Stack;

public class MinStack {
    Stack<Integer> data = new Stack<Integer>();
    Stack<Integer> min = new Stack<Integer>();
    Integer temp = null;

    public void push(int node) {
        if (temp == null) {
            temp = node;
            data.push(node);
            min.push(node);
        } else {
            if (node <= temp) {
                temp = node;
                min.push(node);
            }
            data.push(node);
        }
    }

    public void pop() {
        int top = data.pop();
        int mintop = min.peek();
        if (top == mintop) {
            min.pop();
        }
    }

    public int top() {
        return data.peek();
    }

    public int min() {
        return min.peek();
    }
}
