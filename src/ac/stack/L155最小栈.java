package ac.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class L155最小栈 {
    private Deque<int[]> deque;

    public L155最小栈() {
        deque = new ArrayDeque<>();
    }
    
    public void push(int val) {
        if (deque.isEmpty()) {
            deque.push(new int[]{val, val});
        } else {
            deque.push(new int[]{val, Math.min(getMin(), val)});
        }
    }
    
    public void pop() {
        deque.pop();
    }
    
    public int top() {
        return deque.peek()[0];
    }
    
    public int getMin() {
        return deque.peek()[1];
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */