package threaddemo;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程锁
 * synchronized
 */
public class ThreadDemoLock {

    // 队列长度
    public static int MAX_LENGTH = 10;
    // 共享资源
    public static Queue<Integer> queue = new ArrayDeque<>();

    public static void main(String[] args){

        // 创建固定线程池 -- 包含两个线程对象
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //创建callable对象  --生产者
        Callable<String> producer = new Callable<String>() {

            //具备返回值
            @Override
            public String call() throws Exception {
                //使用synchronized 获取queue的监视器，并获取queue的锁
                synchronized (queue){
                    while (queue.size() == MAX_LENGTH){
                        System.out.println("消费队列中资源已满，当前阻塞！");
                        //消费队列内数据满的时候，挂起当前线程，并释放通过同步代码（synchronized）获取的queue的锁，让消费者可以获取该锁
                        queue.wait();
                    }

                    queue.add(0);
                    System.out.println("生产资源：" + queue.peek());
                    //通知所有等待该锁资源的线程
                    queue.notifyAll();
                    //仅通知一个等待该锁资源的线程，且不能知名通知哪一个具体的线程
                    //queue.notify();
                }
                return "线程执行Callabel的call方法。。。";
            }
        };

        //消费者
        Callable<String> consumer = new Callable<String>() {
            @Override
            public String call() throws Exception {
                synchronized (queue){
                    //消费队列中没有资源时，阻塞
                    while (queue.size() == 0){
                        System.out.println("消费队列中没有资源，当前阻塞！");
                        queue.wait(); //如生产者
                    }
                    System.out.println("消费资源：" + queue.poll());
                    queue.notifyAll();
                }
                return null;
            }
        };

        while (true){
            executorService.submit(producer);
            executorService.submit(consumer);
        }
    }

}
