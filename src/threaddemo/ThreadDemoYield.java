package threaddemo;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 调用yield()方法的线程，会让出当前线程对cpu的占用，将cpu资源释放出来给后续优先级高的线程使用
 */
public class ThreadDemoYield {

    public static void main(String[] args){

        // 创建固定线程池 -- 包含两个线程对象
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<String> producer = new Callable<String>() {

            //具备返回值
            @Override
            public String call() throws Exception {
                int i= 0;
                while (i<10){
                    if(i%3==0){
                        System.out.println(Thread.currentThread()+"yield cpu...");
                        Thread.yield();
                    }
                }
                System.out.println(Thread.currentThread()+"yield over...");
                return "线程执行Callabel的call方法。。。";
            }
        };

        while (true){
            executorService.submit(producer);
        }

    }
}
