package edu.upenn.cit594;

import edu.upenn.cit594.data.Residence;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.processor​.ResidentialProcessor;
import edu.upenn.cit594.ui.UserInput;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Check number of args
        if (args.length != 5) {
            System.out.println("Wrong number of arguments. Program exit!");
            System.exit(0);
        }

        // Check if the extension meets txt/json
        if (!checkExtension(args[0])) {
            System.out.println("Wrong tweet file format. Should be '.json or .text'. Program exit!");
            System.exit(0);

        }
        // Get file names to process
        String ticketFormat = args[0];
        String ticketFileName = args[1];
        String propertyFileName = args[2];
        String populationFileName = args[3];
        String logFileName = args[4];

        PropertyReader pr = new PropertyReader("sample.csv");
        ResidentialProcessor residentialProcessor = new ResidentialProcessor(pr);
        PopulationReader populationReader = new PopulationReader("population.txt");


        UserInput user1 = UserInput.getInstance();




    }

    private static boolean checkExtension(String ext) {
        return ext.equals("json") || ext.equals("csv");
    }

}

