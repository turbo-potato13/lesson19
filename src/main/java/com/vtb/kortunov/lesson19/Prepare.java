package com.vtb.kortunov.lesson19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Prepare {

    public static void prepareData(DbManagement db) {
        try {
            String sql = Files.lines(Paths.get("full.sql")).collect(Collectors.joining(" "));
            db.beginTransaction();
            db.createNativeQuery(sql);
            db.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}
