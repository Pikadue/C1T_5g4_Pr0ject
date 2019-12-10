package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.processor​.ParkingViolationsProcessor;
import edu.upenn.cit594.processor​.ResidentialProcessor;
import edu.upenn.cit594.ui.UserInput;


public class Main {
    public static void main(String[] args) {
        // Check number of args
        if (args.length != 5) {
            System.out.println("Wrong number of arguments. Program exits!");
            System.exit(0);
        }

        // Check if the extension meets txt/json
        if (!checkExtension(args[0])) {
            System.out.println("Wrong input file format. Should be '.json or .csv'. Program exits!");
            System.exit(0);

        }
        // Get file names to process
        String ticketFormat = args[0];
        String ticketFileName = args[1];
        String propertyFileName = args[2];
        String populationFileName = args[3];
        String logFileName = args[4];

        Logging.getInstance(logFileName).log(String.join(" ",args));

        PropertyReader propertyReader = new PropertyReader(propertyFileName);
        ResidentialProcessor residentialProcessor = new ResidentialProcessor(propertyReader);
        PopulationReader populationReader = new PopulationReader(populationFileName);
        ParkingViolationsProcessor parkingViolationProcessor = new ParkingViolationsProcessor(ticketFormat,ticketFileName);
        UserInput user1 = UserInput.getInstance();



    }

    private static boolean checkExtension(String ext) {
        return ext.equals("json") || ext.equals("csv");
    }

}

