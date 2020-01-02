package threaddemo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * CAS 操作
 * Compare And Swarp  非阻塞原子性操作
 * Unsafe 提供硬件级别的原子性操作
 * Unsafe 类中的方法都是native方法
 */
public class ThreadDemoUnsafe {
    //获取unsafe实例 CAS操作
    static Unsafe unsafe;

    //记录变量state在类ThreadDemoUnsafe中的偏移量
    static long stateOffset = 0;
    static long stateOffset1 = 0;
    static long stateOffset2 = 0;

    //变量
    private volatile long state = 0;
    private volatile long state1 = 1;
    private volatile long state2 = 2;

    static {
        try {
            //使用反射获取Unsafe的成员变量
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可存取
            field.setAccessible(true);

            //获取该变量的值
            unsafe = (Unsafe) field.get(null);

            //获取state变量在类ThreadDemoUnsafe中的偏移量
            stateOffset = unsafe.objectFieldOffset(ThreadDemoUnsafe.class.getDeclaredField("state"));
            stateOffset1 = unsafe.objectFieldOffset(ThreadDemoUnsafe.class.getDeclaredField("state1"));
            stateOffset2 = unsafe.objectFieldOffset(ThreadDemoUnsafe.class.getDeclaredField("state2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] agrs){
        // 创建ThreadDemoUnsafe实例
        ThreadDemoUnsafe demoUnsafe = new ThreadDemoUnsafe();

        System.out.println(stateOffset);
        System.out.println(stateOffset1);
        System.out.println(stateOffset2);

        //比较ThreadDemoUnsafe对象中偏移量为stateOffset的变量（state）的值是否为0，是则修改为1并return true，否则返回false；
        Boolean success = unsafe.compareAndSwapInt(demoUnsafe, stateOffset, 0, 1);
        // Boolean success = unsafe.compareAndSwapInt(demoUnsafe, stateOffset, 1, 1);  //return false;
        System.out.println(success);
    }
}
