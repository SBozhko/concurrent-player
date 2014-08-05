package me.sbozhko.test;

public class SynchronizedBlockPlayer implements Player {
    private long points;
    private long kudos;
    private final Object lock = new Object();

    @Override
    public void update(long points, long kudos) {
        synchronized (lock) {
            this.points = points;
            this.kudos = kudos;
        }
    }

    @Override
    public long getPoints() {
        synchronized (lock) {
            return points;
        }
    }

    @Override
    public long getKudos() {
        synchronized (lock) {
            return kudos;
        }
    }
}
