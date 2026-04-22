## 由单例模式深入 Java 锁

### 单例模式

下面的代码是一段经典的饿汉式的单例模式写法

```java
package javal;
/**
 * @author lunfee
 * @create 2022/3/6-14:53
 */
//懒汉式单例模式
public class Singleton {
    private static volatile Singleton instance = null;//B. 可见性
    public static Singleton getInstance(){
        if(instance == null) {//p.

            synchronized (Singleton.class) {//1.
                if(instance == null) {// A. DCL （Double Check Lock双端检锁机制）
                    instance = new Singleton();//2.
                }
            }
        }
        return instance;
    }
}
```

这是在同一 JVM 下线程同步的写法，保证多线程的实例唯一性，这里由两个需要注意的点（A. B.），我们从这两个点深入理解一下Java种的锁机制。

- A. Double Check，synchronized 关键字是一种对象锁的实现方式，由它修饰的代码块或者方法，能保证在临界区内的代码原子性，p 处对 instance 的判断不在同步块内，无法保证其读写原子性。在没有Double Check时，可能发生下面这种情况：Thread1 和 Thread2 同时执行到 p. 处， 都判断 instance == null 为 True，然后一次获取锁，完成对象的实例化，导致实例化多次。

- B. volatile，仅仅做Double Check的话，任然会有问题，原因时有指令重排的存在，加入volatile可以禁止指令重排

  原因是在某一个线程执行到第一次检测，读取到instance不为null时，instance的引用对象可能没有完成初始化。instance=new SingleDemo();可以被分为一下三步（[伪代码](https://www.zhihu.com/search?q=伪代码&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A2140946313})）：

  ```text
  memory = allocate();//1.分配对象内存空间
  instance(memory); //2.初始化对象
  instance = memory; //3.设置instance执行刚分配的内存地址，此时instance!=null
  ```

  步骤2和步骤3不存在数据依赖关系，而且无论重排前还是重排后程序的执行结果在单线程中并没有改变，因此这种重排优化时允许的

  所以如果3步骤提前于步骤2，但是instance还没有初始化完成指令重排只会保证串行语义的执行的一致性（单线程），但并不关心多线程间的语义一致性。

  所以当一条线程访问instance不为null时，由于instance示例未必已初始化完成，也就造成了线程安全问题。

  此时加上volatile后就不会出现线程安全问题

  ```java
  private static volatile Singleton instance = null;//B. 可见性
  ```

  因为volatile禁止了指令重排序的问题

### 原子性

- volatile 关键字保证了修饰变量的可见性

> 读：忽略本地内存的变量  >  直接读取主内存种的变量
>
> 赋值：直接读取主内存种的变量  > 存入本地内存  >  赋值  >  刷入主内存

在这种情况下，volatile 与 synchronized 一样保证了操作的原子性，但是这种保证，仅限于写操作与当前当前值无关的情况下。

对于运算等操作，无法使用 volatile 保证原子性。

> 运算：直接读取主内存种的变量  > 存入本地内存 > 运算操作  >  赋值  >  刷入主内存

- synchronized 时一种互斥锁，通过保证唯一的线程执行临界区代码，保证操作的原子性。

### 可见性

- volatile 的可见性不在赘述。
- synchronized 也能保证可见性，只不过不是实时的，



## 单一变量的高效锁 CAS (synchronized：大材小用)

我们先来看几行代码：

```java
public class CASTest {
    static int i = 0;
    public static void increment() {
        i++;
    }
}
```

假如有100个线程同时调用 increment() 方法对 i 进行自增操作，i 的结果会是 100 吗？

这个方法是线程不安全的，由于 i++ 不是一个**原子操作**，所以是很难得到 100 的。

那该怎么办呢？解决的策略一般都是给这个方法加个锁，如下（**这里不能用volatile，因为无法保证原子性**）

```java
public class CASTest {
    static int i = 0;
    public synchronized static void increment() {
        i++;
    }
}
```

然而，一个简简单单的自增操作，就加了 synchronized 进行同步，好像有点大材小用的感觉，加了 synchronized 关键词之后，当有很多线程去竞争 increment 这个方法的时候，拿不到锁的方法是会被**阻塞**在方法外面的，最后再来唤醒他们，而阻塞/唤醒这些操作，是非常消耗资源的。

虽然synchronized 到了JDK1.6之后不是做了很多优化，增加了偏向锁、轻量级锁等， 但是，就算增加了这些，当很多线程来竞争的时候，开销依然很多（会升级）

### CAS ：

如果我采用下面这种方式，能否保证 increment 是线程安全的，步骤如下：

1、线程从内存中读取 i 的值，假如此时 i 的值为 0，我们把这个值称为 k 吧，即此时 k = 0。

2、令 j = k + 1。

3、用 k 的值与内存中i的值相比，如果相等，这意味着没有其他线程修改过 i 的值，我们就把 j（此时为1） 的值写入内存；如果不相等（意味着i的值被其他线程修改过），我们就不把j的值写入内存，而是重新跳回步骤 1，继续这三个操作。

翻译成代码的话就是这样：

```java
public static void increment() {
    do{
        int k = i;
        int j = k + 1;
    }while (compareAndSet(i, k, j))
}
```

这样写是线程安全的。

这个 compareAndSet 操作，他其实只对应操作系统的**一条硬件操作指令**，尽管看似有很多操作在里面，但操作系统能够保证他是原子执行的。

对于一条英文单词很长的指令，我们都喜欢用它的简称来称呼他，所以，我们就把 compareAndSwap 称为 **CAS** 吧。

所以，采用 CAS 这种机制的写法也是线程安全的，通过这种方式，可以说是不存在锁的竞争，也不存在阻塞等事情的发生，可以让程序执行的更好。

在 Java 中，也是提供了这种 CAS 的原子类，例如：

1. AtomicBoolean
2. AtomicInteger
3. AtomicLong
4. AtomicReference

具体如何使用呢？我就以上面那个例子进行改版吧，代码如下：

```java
public class CASTest {
    static AtomicInteger i = new AtomicInteger(0);
    public static void increment() {
        // 自增 1并返回之后的结果
        i.incrementAndGet();
    }
}
```

### CAS源码中为什么有循环操作

```java
public static void main(String[] args) {
    AtomicInteger num = new AtomicInteger(0);
    num.getAndIncrement();
    System.out.println(num);
}
```

```java
public final int getAndAddInt(Object var1, long var2, int var4) {
    //Object var1 一般为this, long var2 为偏移量， int var4 为目标值
    int var5;
    do {
        var5 = this.getIntVolatile(var1, var2);//内存中获取值
        //执行CAS操作
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
    return var5;
}
```



## ThreadLocal

多个线程对同一个共享变量进行操作时，非常用以出现并发的问题，所以为了保证线程安全，都要对共享变量进行同步。

#### 合理的理解

ThreadLoal 变量，它的基本原理是，同一个 ThreadLocal 所包含的对象（对ThreadLocal< String >而言即为 String 类型变量），在不同的 Thread 中有不同的副本（实际是不同的实例，后文会详细阐述）。这里有几点需要注意

- 因为每个 Thread 内有自己的实例副本，且该副本只能由当前 Thread 使用。这是也是 ThreadLocal 命名的由来
- 既然每个 Thread 有自己的实例副本，且其它 Thread 不可访问，那就不存在多线程间共享的问题
- 既无共享，何来同步问题，又何来解决同步问题一说？

那 ThreadLocal 到底解决了什么问题，又适用于什么样的场景？

> This class provides thread-local variables. These variables differ from their normal counterparts in that each thread that accesses one (via its get or set method) has its own, independently initialized copy of the variable. ThreadLocal instances are typically private static fields in classes that wish to associate state with a thread (e.g., a user ID or Transaction ID).
> Each thread holds an implicit reference to its copy of a thread-local variable as long as the thread is alive and the ThreadLocal instance is accessible; after a thread goes away, all of its copies of thread-local instances are subject to garbage collection (unless other references to these copies exist).

核心意思是

> ThreadLocal 提供了线程本地的实例。它与普通变量的区别在于，每个使用该变量的线程都会初始化一个完全独立的实例副本。ThreadLocal 变量通常被`private static`修饰。当一个线程结束时，它所使用的所有 ThreadLocal 相对的实例副本都可被回收。

总的来说，**ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景。**后文会通过实例详细阐述该观点。另外，该场景下，并非必须使用 ThreadLocal ，其它方式完全可以实现同样的效果，只是 ThreadLocal 使得实现更简洁。



#### **ThreadLocal怎么用**

既然ThreadLocal的作用是每一个线程创建一个副本，我们使用一个例子来验证一下：

```java
public class ThreadLocalTest01 {
    public static void main(String[] args) {
        //新建一个ThreadLocal
        ThreadLocal<String> local = new ThreadLocal<>();
        //新建一个随机数类
        Random random = new Random();
        //使用java8的Stream新建5个线程
        IntStream.range(0, 5).forEach(a-> new Thread(()-> {
            //为每一个线程设置相应的local值
            local.set(a+"  "+random.nextInt(10));   
            System.out.println("线程和local值分别是  "+ local.get());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }
}
/*线程和local值分别是  0  6
  线程和local值分别是  1  4
  线程和local值分别是  2  3
  线程和local值分别是  4  9
  线程和local值分别是  3  5 */
```

从结果我们可以看到，每一个线程都有各自的local值，我们设置了一个休眠时间，就是为了另外一个线程也能够及时的读取当前的local值。

这就是TheadLocal的基本使用，是不是非常的简单。那么为什么会在数据库连接的时候使用的比较多呢？

```java
class ConnectionManager {
    private static Connection connect = null;
    public static Connection openConnection() {
        if(connect == null){
            connect = DriverManager.getConnection();
        }
        return connect;
    }
    public static void closeConnection() {
        if(connect!=null)
            connect.close();
    }
}
```

上面是一个数据库连接的管理类，我们使用数据库的时候首先就是建立数据库连接，然后用完了之后关闭就好了，这样做有一个很严重的问题，如果有1个客户端频繁的使用数据库，那么就需要建立多次链接和关闭，我们的服务器可能会吃不消，怎么办呢？如果有一万个客户端，那么服务器压力更大。

这时候最好ThreadLocal，因为ThreadLocal在每个线程中对连接会创建一个副本，且在线程内部任何地方都可以使用，线程之间互不影响，这样一来就不存在线程安全问题，也不会严重影响程序执行性能。是不是很好用。

#### ThreadLocal 原理

```java
public class ThreadLocal<T> {
        /**
     * Sets the current thread's copy of this thread-local variable
     * to the specified value.  Most subclasses will have no need to
     * override this method, relying solely on the {@link #initialValue}
     * method to set the values of thread-locals.
     *
     * @param value the value to be stored in the current thread's copy of
     *        this thread-local.
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
    
        /**
     * Create the map associated with a ThreadLocal. Overridden in
     * InheritableThreadLocal.
     *
     * @param t the current thread
     * @param firstValue value for the initial entry of the map
     */
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }
}
```

自我理解： ThreadLocal 其实就是一个空壳，线程的局部变量不是存放在 ThreadLocal 中的，而是使用 ThreadLocal 维护的方法对当前线程的 ThreadLocalMap threadLocals 进行操作，之所以使用 Map 的形式存储，是因为同一个线程可能维护多个ThreadLocal 变量。

内存溢出问题： 如果一个线程不消亡，其threadLocals中的变量就一直会存在，可能会造成内存溢出，所以使用完毕要进行remove操作。

#### 案例

对于 Java Web 应用而言，Session 保存了很多信息。很多时候需要通过 Session 获取信息，有些时候又需要修改 Session 的信息。一方面，需要保证每个线程有自己单独的 Session 实例。另一方面，由于很多地方都需要操作 Session，存在多方法共享 Session 的需求。如果不使用 ThreadLocal，可以在每个线程内构建一个 Session实例，并将该实例在多个方法间传递，如下所示。

```
public class SessionHandler {

  @Data
  public static class Session {
    private String id;
    private String user;
    private String status;
  }

  public Session createSession() {
    return new Session();
  }

  public String getUser(Session session) {
    return session.getUser();
  }

  public String getStatus(Session session) {
    return session.getStatus();
  }

  public void setStatus(Session session, String status) {
    session.setStatus(status);
  }

  public static void main(String[] args) {
    new Thread(() -> {
      SessionHandler handler = new SessionHandler();
      Session session = handler.createSession();
      handler.getStatus(session);
      handler.getUser(session);
      handler.setStatus(session, "close");
      handler.getStatus(session);
    }).start();
  }
}
```



该方法是可以实现需求的。但是每个需要使用 Session 的地方，都需要显式传递 Session 对象，方法间耦合度较高。

这里使用 ThreadLocal 重新实现该功能如下所示。

```
public class SessionHandler {

  public static ThreadLocal<Session> session = ThreadLocal.<Session>withInitial(() -> new Session());

  @Data
  public static class Session {
    private String id;
    private String user;
    private String status;
  }

  public String getUser() {
    return session.get().getUser();
  }

  public String getStatus() {
    return session.get().getStatus();
  }

  public void setStatus(String status) {
    session.get().setStatus(status);
  }

  public static void main(String[] args) {
    new Thread(() -> {
      SessionHandler handler = new SessionHandler();
      handler.getStatus();
      handler.getUser();
      handler.setStatus("close");
      handler.getStatus();
    }).start();
  }
}
```



使用 ThreadLocal 改造后的代码，不再需要在各个方法间传递 Session 对象，并且也非常轻松的保证了每个线程拥有自己独立的实例。

如果单看其中某一点，替代方法很多。比如可通过在线程内创建局部变量可实现每个线程有自己的实例，使用静态变量可实现变量在方法间的共享。但如果要同时满足变量在线程间的隔离与方法间的共享，ThreadLocal再合适不过。

#### 弱引用问题

https://zhuanlan.zhihu.com/p/304240519
