package grammar;


import java.util.concurrent.atomic.AtomicLong;

public class ComputeIfAbsent {

    public static void main(String[] args) throws InterruptedException {
        AtomicLong time1 = new AtomicLong();
        new Thread(() -> {
            long now = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) {
                Object o = get0();
            }
            long then = System.currentTimeMillis();
            time1.getAndAdd(then - now);
            System.out.println(time1);
        }).start();

        new Thread(() -> {
            long now = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) {
                Object o = get0();
            }
            long then = System.currentTimeMillis();
            time1.getAndAdd(then - now);
            System.out.println(time1);
        }).start();
    }

    private synchronized static Object get() {
        return new Object();
    }

    private static Object get0() {
        return "0";
    }
}
