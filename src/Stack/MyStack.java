package Stack;

import java.util.*;


public class MyStack<T> implements Iterable<T> {
    private final List<T> stackList = new LinkedList<>();

    public int size() {
        return stackList.size();
    }

    public boolean isEmpty() {
        return stackList.isEmpty();
    }

    public T push(T element){
        stackList.add(element);
        return element;
    }

    public T pop(){
        if (isEmpty())
            throw new RuntimeException("Stack is empty");

        T element = stackList.get(stackList.size() - 1);
        stackList.remove(stackList.size() - 1);
        return element;
    }

    public T peak(){
        if (isEmpty())
            throw new RuntimeException("Stack is empty");

        return stackList.get(stackList.size() - 1);
    }

    public void print(){
        for (int i = stackList.size() - 1; i >= 0; i--) {
            System.out.println(stackList.get(i));
        }
    }


    @Override
    public Iterator<T> iterator() {
        return stackList.iterator();
    }

    static boolean match(String word) {
        char[] chars = word.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : chars) {

            if (c == ']' && !stack.empty() && stack.peek() == '[')
                stack.pop();

            else if (c == '}' && !stack.empty() && stack.peek() == '{')
                stack.pop();

            else if (c == ')' && !stack.empty() && stack.peek() == '(')
                stack.pop();

            else
                stack.push(c);
        }

        return stack.empty();
    }

}
