package common;

import java.util.concurrent.locks.ReentrantLock;

public class ID implements Lockable {
    private int value;

    private final ReentrantLock lock = new ReentrantLock(true);

    private static final int DEFAULT_INITIAL_VALUE = 0;


    public ID() {
        this.value = DEFAULT_INITIAL_VALUE;
    }

    public ID(int initialValue) {
        this.value = initialValue;
    }

    public ID(ID id) {
        this.value = id.value;
    }

    public int get() {
        return value;
    }

    public int getAndIncrement() {
        return value++;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        ID id = (ID) o;
        return this.value == id.value;
    }

    public ID clone() {return new ID(this);}

    public String toString() {
        return "ID {value=" + value + "}";
    }

    public int hashCode() {
        return value;
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
