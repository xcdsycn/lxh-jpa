package com.lxh.jpa.utils.norepeatlru;


import org.apache.commons.collections4.map.LRUMap;

/**
 * 幂等性判断，利用LRUMap
 */
public class IdempotentUtils {
    /**   根据 LRU(Least Recently Used，最近最少使用)算法淘汰数据的 Map 集合，最大容量 100 个 **/
    private static LRUMap<String, Integer> reqCache = new LRUMap<>(100);


    public static  boolean judge(String id, Object lockClass) {
        synchronized (lockClass) {
            if(reqCache.containsKey(id)) {
                System.out.println("==> 请勿重复提交！！！" + id);
                return false;
            }
            reqCache.put(id,1);
        }
        return true;
    }
}
