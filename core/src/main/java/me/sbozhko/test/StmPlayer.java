package me.sbozhko.test;

import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

import java.util.concurrent.Callable;

public class StmPlayer implements Player {
    private final Ref.View<Long> points = STM.newRef(0l);
    private final Ref.View<Long> kudos = STM.newRef(0l);

    @Override
    public void update(final long p, final long k) {
        STM.atomic(new Runnable() {
            @Override
            public void run() {
                points.set(p);
                kudos.set(k);
            }
        });
    }

    @Override
    public long getPoints() {
        return STM.atomic(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return points.get();
            }
        });
    }

    @Override
    public long getKudos() {
        return STM.atomic(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return kudos.get();
            }
        });
    }
}
