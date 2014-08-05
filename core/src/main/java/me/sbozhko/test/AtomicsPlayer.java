package me.sbozhko.test;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicsPlayer implements Player {
    private AtomicLong points = new AtomicLong(0);
    private AtomicLong kudos = new AtomicLong(0);

    @Override
    public synchronized void update(long points, long kudos) {
        this.points.getAndSet(points);
        this.kudos.getAndSet(kudos);
    }

    @Override
    public long getPoints() {
        return points.get();
    }

    @Override
    public long getKudos() {
        return kudos.get();
    }
}
