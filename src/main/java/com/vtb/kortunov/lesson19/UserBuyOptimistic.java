package com.vtb.kortunov.lesson19;

import javax.persistence.OptimisticLockException;
import java.util.concurrent.CountDownLatch;

public class UserBuyOptimistic extends Thread {

    private final User user;
    private final CountDownLatch downLatch;
    private final Factory factory;

    public UserBuyOptimistic(User user, CountDownLatch downLatch, Factory factory) {
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
        dbManagement.beginTransaction();
        try {
            for (int i = 0; i < 1000; i++) {
                Long lotId = (long) (Math.random() * 4 + 1);
                Long userId = user.getId();
                dbManagement.raise(userId, lotId, 100);
                sleep(1);
            }
            dbManagement.commit();
        } catch (OptimisticLockException e) {
            dbManagement.rollBack();
            usersBuying();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dbManagement.close();
        }
    }
}


