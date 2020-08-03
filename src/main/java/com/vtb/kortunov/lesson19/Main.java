package com.vtb.kortunov.lesson19;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        Factory factory = new Factory();
        DbManagement dbManagement = new DbManagement(null, factory);
        Prepare.prepareData(dbManagement);
        CountDownLatch downLatch = new CountDownLatch(8);
        dbManagement.beginTransaction();
        List<User> users = dbManagement.getUsers();
//        long time = System.currentTimeMillis();
//        for (User user : users) {
//            new UserBuyOptimistic(user, downLatch, factory).start();
//        }
//        try {
//            downLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Time with Optimistic lock: " +  (System.currentTimeMillis() - time));

        long time = System.currentTimeMillis();
        for (User user : users) {
            new UserBuyPessimistic(user, downLatch, factory).start();
        }
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Time with Pessimistic lock: " + (System.currentTimeMillis() - time));

        //Time with Pessimistic lock: 4236
        //Time with Optimistic lock: 14274

        //Task 4
        List<Lot> lots = dbManagement.getLots();
        int resultSum = 0;
        for (Lot lot : lots) {
            resultSum += lot.getCurrentRate();
        }
        if (resultSum == 800000) {
            System.out.println("Threads are working correctly");
        } else {
            System.out.println("Result is incorrect");
        }

    }
}
