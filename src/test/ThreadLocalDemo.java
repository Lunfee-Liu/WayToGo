package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {

    // 1. 定义一个 ThreadLocal 变量，用来存储当前线程的用户信息
    private static final ThreadLocal<String> userContext = new ThreadLocal<>();

    public static void main(String[] args) {
        // 模拟一个固定大小为 2 的线程池（模拟线程复用场景）
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 3; i++) {
            final int requestId = i;
            threadPool.execute(() -> {
                try {
                    // 2. 模拟从请求头或 Session 中获取用户信息并设置到 ThreadLocal
                    String userInfo = "用户-" + requestId;
                    userContext.set(userInfo);
                    
                    System.out.println(Thread.currentThread().getName() + " [开始处理] 请求ID: " + requestId + ", 当前绑定用户: " + userContext.get());

                    // 3. 模拟业务逻辑调用（不需要传参，直接从 ThreadLocal 拿）
                    doBusinessLogics();

                } finally {
                    // 4. 重要！使用完后务必移除，防止内存泄漏和线程复用导致的数据污染
                    userContext.remove();
                    System.out.println(Thread.currentThread().getName() + " [已清理]");
                }
            });
        }
        threadPool.shutdown();
    }

    private static void doBusinessLogics() {
        // 直接 get() 即可获取当前线程绑定的数据
        String user = userContext.get();
        System.out.println(Thread.currentThread().getName() + " [Service层] 正在为 " + user + " 处理跨境物流订单...");
    }
}