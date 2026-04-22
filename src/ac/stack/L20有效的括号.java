package ac.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class L20有效的括号 {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && isPair(stack.peek(), c)) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    private boolean isPair(Character left, char right) {
        if (left == '(') {
            return ')' == right;
        }
        if (left == '[') {
            return ']' == right;
        }
        if (left == '{') {
            return '}' == right;
        }
        return false;
    }
}
