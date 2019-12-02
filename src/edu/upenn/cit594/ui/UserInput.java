package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor​.ResidentialMarketValueCollector;
import edu.upenn.cit594.processor​.ResidentialProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInput {
    private static UserInput obj;

    private UserInput() {
        start();

    }

    public static UserInput getInstance() {
        if (obj == null) obj = new UserInput();

        return obj;
    }

    private void start() {

        Scanner in = new Scanner(System.in);
        initialize();
        while (in.hasNextLine()) {

            String userInput = in.nextLine();

            if (isCorrect(userInput)) {
                switch (userInput) {
                    case "0":
                        System.exit(0);
                        break;
                    case "1":
                        System.out.println("Select 1");
                        break;
                    case "2":
                        System.out.println("Select 2");
                        break;
                    case "3":
                        System.out.println("Please enter a ZIP code:");
                        if (in.hasNextLine()) {
                            String zip = in.nextLine();
                            handleThree(zip);
                        }
                        break;
                    case "4":
                        System.out.println("Select 4");
                        break;
                    case "5":
                        System.out.println("Select 5");
                        break;
                    case "6":
                        System.out.println("Select 6");
                        break;
                    default:
                        System.out.println("Invalid selection");
                        System.exit(0);
                }
                initialize();

            }
        }


    }

    private void initialize() {
        System.out.println("Enter a number between 0-6, to conduct the actions below:\n0: exit\n1: show total " +
                "population for all ZIP codes\n2: show total parking fines per capita for each ZIP code\n" +
                "3: show average market value for residences in a specified ZIP code\n" +
                "4: show the average total livable area for residences in a specified ZIP code\n" +
                "5: show the total residential market value per capita for a specified ZIP code\n" +
                "6: show the results of our custom feature");

    }

    private boolean isCorrect(String input) {
        return Pattern.matches("\\d", input);

    }
    Map<String, Double> requestedZIP_P3 = new HashMap<>();
    private void handleThree(String zip) {

        double result = 0;
        if (isValidZip(zip)) {
            if (requestedZIP_P3.keySet().contains(zip)) {
                result = requestedZIP_P3.get(zip);
            } else {
                ResidentialMarketValueCollector marketValueCollector = new ResidentialMarketValueCollector();
                ResidentialProcessor residentialProcessor = new ResidentialProcessor(marketValueCollector);
                result = residentialProcessor.process(zip);

                requestedZIP_P3.put(zip, result);
            }
        }
        System.out.printf("The average residential market value for ZIP %s is %d\n", zip, (int) 333.6);
    }

    private boolean isValidZip(String input) {
        return Pattern.matches("\\d{5}", input);

    }
}
