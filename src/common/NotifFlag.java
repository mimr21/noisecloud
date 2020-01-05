package common;

import client.Notifications;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NotifFlag implements Lockable{
    private String name;
    private String artist;
    private boolean n;                  // true = update
    private final ReentrantLock lock;
    private final Condition cond;


    public NotifFlag(){
        name = "";
        artist = "";
        n = false;
        lock = new ReentrantLock();
        cond = lock.newCondition();
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public void await(){
        try {
            cond.await();
        } catch (InterruptedException e) {}
    }

    public void signalAll(){
        cond.signalAll();
    }

    public boolean test(){
        return n;
    }

    public void update(String n, String a){
        this.name = n;
        this.artist = a;
        this.n = true;
    }

    public void goBack(){
        this.name = "";
        this.artist = "";
        this.n = false;
    }

    public String getNotif() {
        return this.name + " " + this.artist;
    }
}
