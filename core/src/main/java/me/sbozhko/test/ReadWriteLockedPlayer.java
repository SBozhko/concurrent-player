package me.sbozhko.test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockedPlayer implements Player {
    private long points;
    private long kudos;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    @Override
    public void update(long points, long kudos) {
        rwLock.writeLock().lock();
        try {
            this.points = points;
            this.kudos = kudos;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public long getPoints() {
        rwLock.readLock().lock();
        try {
            return points;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public long getKudos() {
        rwLock.readLock().lock();
        try {
            return kudos;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
