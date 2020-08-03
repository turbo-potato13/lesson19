package com.vtb.kortunov.lesson19;

import java.util.concurrent.CountDownLatch;

public class UserBuyPessimistic extends Thread {
    private final User user;
    private final CountDownLatch downLatch;
    private final Factory factory;

    public UserBuyPessimistic(User user, CountDownLatch downLatch, Factory factory) {
        this.user = user;
        this.downLatch = downLatch;
        this.factory = factory;
    }

    @Override
    public void run() {
        usersBuying();
        downLatch.countDown();
    }

    public void usersBuying() {
        DbManagement dbManagement = new DbManagement(null, factory);
        try {
            for (int i = 0; i < 1000; i++) {
                dbManagement.beginTransaction();
                Long lotId = (long) (Math.random() * 4 + 1);
                Long userId = user.getId();
                dbManagement.raisePessimistic(userId, lotId, 100);
                dbManagement.commit();
                sleep(1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dbManagement.close();
        }
    }
}
