package server;

import static common.Noisecloud.MAXDOWN;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


// package-private
class DownloadQueue {
    private final ReentrantLock lock;
    private final Condition cond;
    private int usedTickets;
    private int nextTicket;


    public DownloadQueue() {
        lock = new ReentrantLock();
        cond = lock.newCondition();
        usedTickets = 0;
        nextTicket = 1;
    }

    public void enterQueue() {
        lock.lock();

        int myTicket = nextTicket++;

        while (myTicket > usedTickets + MAXDOWN) {
            try {cond.await();} catch (InterruptedException ignored) {}
        }

        ++usedTickets;
        cond.signalAll();

        lock.unlock();
    }
}
