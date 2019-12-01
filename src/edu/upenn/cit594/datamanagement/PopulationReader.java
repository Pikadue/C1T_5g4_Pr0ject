package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Residence;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulationReader {
    public HashMap<Integer, Integer> read(String fileName) throws FileNotFoundException {
        Map<Integer, Integer> populationMap = new HashMap<>();//TODO
        File newFile = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(newFile));
        String line;
        try{
            while ((line = br.readLine()) != null) {
                System.out.println(line);


            }
        } catch(IOException e){
            System.out.println("population file cannot be opened");


        }

        return null;

    }
}
