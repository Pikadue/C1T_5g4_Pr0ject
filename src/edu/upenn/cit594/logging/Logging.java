package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {

    private static Logging obj;
    private String fileName;
    private FileWriter fw;

    private Logging(String fileName) {

        this.fileName = fileName;
        try {
            this.fw = new FileWriter(new File(fileName));
        } catch (IOException e) {
            System.out.println("Fail creating output file.");
        }

    }

    public static Logging getInstance(String fileName) {
        if (obj == null) {
            obj = new Logging(fileName);

        }
        return obj;

    }

    public void write(String output) {
        long time = System.currentTimeMillis();
        System.out.printf("Time: %d %s%n", time, output);

    }
}
