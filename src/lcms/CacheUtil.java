package lcms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CacheUtil {

    // 私有化构造方法，禁止外部创建
    private CacheUtil() {
    }

    // 缓存, 高效且线程安全, 避免多线程写出现问题
    private final static Map<String, Entity> map = new ConcurrentHashMap<>();

    // 定时器线程池，用于清除过期缓存
    private final static ScheduledExecutorService executor 
    = Executors.newSingleThreadScheduledExecutor();

    // 是否存在该key
    public static boolean exists(String key) {
        return map.containsKey(key);
    }
    
    // 缓存大小
    public int size() {
        return map.size();
    }
    
    // set 
    public static void set(String key, Object data) {
        CacheUtil.set(key, data, 0);
    }

    // 插入(带过期时间)
    public static void set(String key, Object data, long expire) {
        // 清除原键值对
        CacheUtil.delete(key);
        // 设置过期时间
        if (expire > 0) {
            Future future = executor.schedule(new Runnable() {
                @Override
                public void run() {
                    // 过期后清除该键值对
                    map.remove(key);
                }
            }, expire, TimeUnit.MILLISECONDS);
            map.put(key, new Entity(data, future));
        } else {
            // 不设置过期时间
            map.put(key, new Entity(data, null));
        }
    }

    // get
    public static <T> T get(String key) {
        Entity entity = map.get(key);
        return entity == null ? null : (T) entity.value;
    }
    

    // del
    public static <T> T delete(String key) {
        Entity entity = map.remove(key);
        if (entity == null) {
            return null;
        }
        // 清除原键值对定时器
        if (entity.future != null) {
            entity.future.cancel(true);
        }
        return (T) entity.value;
    }

    // 缓存对象
    private static class Entity {
        // 缓存数据
        public Object value;

        // 过期清理
        public Future future;

        public Entity(Object value, Future future) {
            this.value = value;
            this.future = future;
        }
    }
}
