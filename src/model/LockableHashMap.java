package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


public class LockableHashMap<K, V> extends HashMap<K, V> implements LockableMap<K, V> {
    private ReentrantLock lock = new ReentrantLock(true);


    public LockableHashMap() {
        super();
    }

    public LockableHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public LockableHashMap(Map<? extends K, ? extends V> map) {
        super(map);
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
