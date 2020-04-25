package game;

import java.util.HashMap;

/**
 * 在整数数组中，如果一个整数的出现频次和它的数值大小相等，我们就称这个整数为「幸运数」。
 *
 * 给你一个整数数组 arr，请你从中找出并返回一个幸运数。
 *
 * 如果数组中存在多个幸运数，只需返回 最大 的那个。
 * 如果数组中不含幸运数，则返回 -1 。
 *
 *
 * 示例 1：
 * 输入：arr = [2,2,3,4]
 * 输出：2
 * 解释：数组中唯一的幸运数是 2 ，因为数值 2 的出现频次也是 2 。
 *
 * 示例 2：
 * 输入：arr = [1,2,2,3,3,3]
 * 输出：3
 * 解释：1、2 以及 3 都是幸运数，只需要返回其中最大的 3 。
 *
 * 示例 3：
 * 输入：arr = [2,2,2,3,3]
 * 输出：-1
 * 解释：数组中不存在幸运数。
 *
 */
public class LuckyInteger {

    public int findLucky(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<arr.length; i++){
            if(map.containsKey(arr[i])){
                map.put(arr[i], map.get(arr[i]) + 1);
                continue;
            }
            map.put(arr[i], 1);
        }
        int result = -1;
        for(int key: map.keySet()){
            if(key == map.get(key)){
                if(key > result){
                    result = key;
                }
            }
        }
        return result;
    }

    public static void main(String[] args){
        LuckyInteger luckyInteger = new LuckyInteger();
        int[] arr = {2,2,3,4};
        int result = luckyInteger.findLucky(arr);
        System.out.println(result);
    }
}
