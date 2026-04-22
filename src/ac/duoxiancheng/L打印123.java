package ac.duoxiancheng;

public class L打印123 {

    private static final Object lock = new Object();
    private static int flag = 1;

    public static void main(String[] args) {
        new Thread(L打印123::print1, "T1").start();
        new Thread(L打印123::print2, "T2").start();

    }

    private static void print1() {
        while (true) {
            synchronized (lock) {
                while (flag % 2 == 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                flag++;
                System.out.println(Thread.currentThread().getName() + ": " + flag);
                lock.notifyAll();
            }
        }
    }

    private static void print2() {
        while (true) {
            synchronized (lock) {
                while (flag % 2 == 0) {

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                flag++;
                System.out.println(Thread.currentThread().getName() + ": " + flag);
                lock.notifyAll();
            }
        }
    }
}
