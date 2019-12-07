package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {

    private static Logging obj;
    private String fileName;
    private static File file;

    private Logging(String fileName) {

        this.fileName = fileName;
        file = new File(fileName);

    }

    public static Logging getInstance() {
        return obj;
    }

    public static Logging getInstance(String fileName) {
        if (obj == null) {
            obj = new Logging(fileName);

        }
        return obj;

    }

    public void log(String message) {
        long time = System.currentTimeMillis();
//        System.out.printf("Time: %d %s%n", time, message);
        try {
            FileWriter out = new FileWriter(Logging.file, true);
            out.write(time + " " + message+"\n");
            out.close();
        } catch (IOException e) {
            System.err.println("ERROR: Could not write to log file");
        }
    }

}
