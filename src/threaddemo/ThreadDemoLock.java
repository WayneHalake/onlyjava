package threaddemo;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 独占锁
 * 独占锁的模式类似于单线程的运行方式，
 */
public class ThreadDemoLock {

    // 队列长度
    public static int MAX_LENGTH = 10;
    // 共享资源
    public static Queue<Integer> queue = new ArrayDeque<>();

    //创建一个独占锁
    public static final Lock lock = new ReentrantLock();

    public static void main(String[] args){
        // 创建固定线程池 -- 包含两个线程对象
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //创建callable对象  --生产者
        Callable<String> producer = new Callable<String>() {

            //具备返回值
            @Override
            public String call() throws Exception {

                lock.lock(); //获取独占锁
                System.out.println("获取独占锁，往queue中添加数据！");
                while (queue.size() < MAX_LENGTH){
                    queue.add(0);
                    System.out.println("添加的数据："+queue.peek());
                }
                System.out.println("释放独占锁！");
                lock.unlock();
                return "线程执行Callabel的call方法。。。";
            }
        };

        //消费者
        Callable<String> consumer = new Callable<String>() {
            @Override
            public String call() throws Exception {
                lock.lock();
                System.out.println("获取独占锁，读取queue中的数据");
                while (queue.size() > 0){
                    System.out.println("获取的数据：" +queue.poll());
                }
                System.out.println("释放独占锁！");
                lock.unlock();
                return null;
            }
        };

            while (true){
            executorService.submit(producer);
            executorService.submit(consumer);
        }
    }
}
