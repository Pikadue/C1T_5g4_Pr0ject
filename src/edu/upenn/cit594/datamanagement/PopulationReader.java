package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Residence;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulationReader {
    public Map<String, Integer> read(String fileName) throws FileNotFoundException {
        Map<String, Integer> populationMap = new HashMap<>();//TODO
        File newFile = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(newFile));
        String line;
        try{
            while ((line = br.readLine()) != null) {
                String[] lineElements = line.split(" ");
                int population = Integer.parseInt(lineElements[1]);
                populationMap.put(lineElements[0], population);

            }
        } catch(IOException e){
            System.out.println("population file cannot be opened");
        }
        return populationMap;

    }
}
