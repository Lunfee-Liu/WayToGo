package ac.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class L739每日温度 {
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> lessStack = new ArrayDeque<>();
        int[] res = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            int todayTemp = temperatures[i];
            while (!lessStack.isEmpty() && todayTemp > temperatures[lessStack.peek()]) {
                res[lessStack.peek()] = i - lessStack.pop();
            }
            lessStack.push(i);
        }
        return res;
    }
}
