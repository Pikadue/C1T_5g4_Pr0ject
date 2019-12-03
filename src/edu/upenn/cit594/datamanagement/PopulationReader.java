package edu.upenn.cit594.datamanagement;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PopulationReader {
    private String fileName;
    private static Map<String, Integer> populationMap = new HashMap<>();
    private static int totalPopulation;
    public PopulationReader(){};
    public PopulationReader (String fileName) {
        this.fileName = fileName;
        try {
            populationMap = this.read();
        } catch (FileNotFoundException e) {
            System.out.println("Population file is not available. Program exits");
            System.exit(0);
        }

    }

    public Map<String, Integer> read() throws FileNotFoundException {
        File newFile = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(newFile));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] lineElements = line.split(" ");
                int population = Integer.parseInt(lineElements[1]);
                totalPopulation += population;
                populationMap.put(lineElements[0], population);


            }
        } catch (IOException e) {
            System.out.println("population file cannot be opened");
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();//todo
            }
        }


        return populationMap;
    }

    // implementation of problem #1: show the total population for all ZIP Codes
    public int getPopulationPerZIP(String zip) {
        int  populationPerZIP = 0;
        if (populationMap.keySet().contains(zip)) {
            populationPerZIP = populationMap.get(zip);

        }
        return populationPerZIP;
    }

    public int getTotalPopulation() {
        return totalPopulation;
    }
    
    public static Map<String, Integer> getPopulationMap(){
    	return populationMap;
    }
}
