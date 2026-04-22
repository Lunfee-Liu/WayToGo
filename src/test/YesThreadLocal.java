package test;

public class YesThreadLocal {
	
    private static final ThreadLocal<String> threadLocalName = ThreadLocal.withInitial(() -> Thread.currentThread().getName());

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> System.out.println("threadName: " + threadLocalName.get()), "yes-thread-" + i).start();
        }
    }
}