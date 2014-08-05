package me.sbozhko.test;

public class SynchronizedMethodPlayer implements Player {
    private long points;
    private long kudos;

    @Override
    public synchronized void update(long points, long kudos) {
        this.points = points;
        this.kudos = kudos;
    }

    @Override
    public synchronized long getPoints() {
        return points;
    }

    @Override
    public synchronized long getKudos() {
        return kudos;
    }
}
