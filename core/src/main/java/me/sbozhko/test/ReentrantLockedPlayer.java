package me.sbozhko.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockedPlayer implements Player {
    private long points;
    private long kudos;
    private final Lock lock = new ReentrantLock();

    @Override
    public void update(long points, long kudos) {
        lock.lock();
        try {
            this.points = points;
            this.kudos = kudos;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getPoints() {
        lock.lock();
        try {
            return points;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getKudos() {
        lock.lock();
        try {
            return kudos;
        } finally {
            lock.unlock();
        }
    }
}
