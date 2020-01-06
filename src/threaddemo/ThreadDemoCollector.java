package threaddemo;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * ××× 强一致性（原子一致性、顺序一致性） ×××
 * 在任意时刻，所有节点中的数据是一样的。
 * ××× 弱一致性（最终一致性） ×××
 * 数据更新后，如果能容忍后续的访问只能访问到部分或者全部访问不到，则是弱一致性
 *
 * 线程安全的集合
 * 1.Vector  添加、获取、删除 内部使用synchronized(对象锁)实现 效率较低
 * 2.CopyOnWriteArrayList  CopyOnWriteArraySet  添加、删除 内部使用独占锁实现ReentrantLock  读取操作并不能保证读写的一致性（弱一致）
 * 3.HashTable 添加、获取、删除 内部使用synchronized（对象锁）实现 效率较低
 * 4.ConcurrentHashMap 写操作 内部实现使用synchronized（资源锁）实现 一把锁只锁住一个链表或者一棵树，并发效率更加提升  读操作遵循弱一致性
 * 5.concurrentLinkedDeque 写操作 内部使用UNSAFE的CAS操作保证线程的一致性， 读操作遵循弱一致性
 * 6.StringBuffer 线程安全 使用synchronized(对象锁)  StringBuilder 线程不安全
 *      均继承了AbstractStringBuilder
 *      StringBuffer和StringBuilder均实现了CharSequence
 * */
public class ThreadDemoCollector {
    public static Vector<Integer> vector = new Vector<>();
    public static CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArraySet<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    public static Hashtable<Integer, Object> hashtable = new Hashtable<>();
    public static ConcurrentHashMap<Integer, Object> concurrentHashMap = new ConcurrentHashMap<>();
    //deque 双向队列
    public static ConcurrentLinkedDeque<Integer> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    //queue 单项队列
    public static ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static StringBuffer stringBuffer = new StringBuffer();
    public static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] agrs){
        //vector 相关操作
        vector.add(1);
        vector.get(0);
        vector.remove(0);

        //CopyOnWriteArrayList 相关操作 有序可重复
        copyOnWriteArrayList.add(1);
        copyOnWriteArrayList.get(0);
        copyOnWriteArrayList.remove(0);

        //copyOnWriteArraySet 无序 不可重复
        copyOnWriteArraySet.add(1);
        copyOnWriteArraySet.remove(1);

        //hashtable
        hashtable.put(1,"first");
        hashtable.get(1);
        hashtable.remove(1);

        // concurrentHashMap
        concurrentHashMap.put(1, "first");
        concurrentHashMap.get(1);
        concurrentHashMap.remove(1);

        // concurrentLinkedDeque
        concurrentLinkedDeque.add(1);
        concurrentLinkedDeque.pop();

        //concurrentLinkedQueue
        concurrentLinkedQueue.add(1);
        concurrentLinkedQueue.poll();

        //stringBuilder
        stringBuilder.append("11111");
        stringBuilder.toString();

        //stringBuffer
        stringBuffer.append("000000");
        stringBuffer.toString();
    }
}
