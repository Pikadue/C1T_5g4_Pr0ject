package edu.upenn.cit594.ui;

import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.processor​.ParkingViolationsProcessor;
import edu.upenn.cit594.datamanagement.ResidentialMarketValueCollector;
import edu.upenn.cit594.processor​.ResidentialProcessor;
import edu.upenn.cit594.datamanagement.ResidentialTLACollector;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInput {
	private static UserInput obj;
	private PopulationReader populationReader = new PopulationReader();


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
			Logging.getInstance().log(userInput);
			if (isCorrect(userInput)) {
				switch (userInput) {
				case "0":
					System.exit(0);
					break;
				case "1":
					System.out.println(populationReader.getTotalPopulation());
					break;
				case "2":
					System.out.println(ParkingViolationsProcessor.getAvgFine());
					break;
				case "3":
					System.out.println("Please enter a ZIP code:");
					if (in.hasNextLine()) {
						String zip = in.nextLine();
						Logging.getInstance().log(zip);
						double result = handleThree(zip);
						System.out.printf("The average residential market value for ZIP '%s' is %d\n", zip, (int)result);
					}
					break;
				case "4":
					System.out.println("Please enter a ZIP code:");
					if (in.hasNextLine()) {
						String zip = in.nextLine();
						Logging.getInstance().log(zip);
						double result = handleFour(zip);
						System.out.printf("The average residential total livable area for ZIP %s is %d\n", zip, (int)result);
					}
					break;
				case "5":
					System.out.println("Please enter a ZIP code:");
					if (in.hasNextLine()) {
						String zip = in.nextLine();
						Logging.getInstance().log(zip);
						double result = handleFive(zip);
						System.out.printf("The total residential market value per capita for ZIP %s is %d\n", zip, (int)result);
					}
					break;
				case "6":
					System.out.println("Please enter a ZIP code:");
					if (in.hasNextLine()) {
						String zip = in.nextLine();
						Logging.getInstance().log(zip);
						String result = handleSix(zip);
						System.out.println(result);
					}
					break;
				default:
					System.out.println("Invalid selection");
				}
				initialize();

			} else {
				System.out.println("Please select from 0-6.");
			}
		}


	}

	private void initialize() {
		System.out.println("Enter a number between 0-6, to conduct the actions below:\n0: exit\n1: show total " +
				"population for all ZIP codes\n2: show total parking fines per capita for each ZIP code\n" +
				"3: show average market value for residences in a specified ZIP code\n" +
				"4: show the average total livable area for residences in a specified ZIP code\n" +
				"5: show the total residential market value per capita for a specified ZIP code\n" +
				"6: show the livable area per capita and the top parking violation reason for a specified ZIP code");

	}

	private boolean isCorrect(String input) {
		return Pattern.matches("\\d", input);

	}

	Map<String, Double> requestedZIP_P3 = new HashMap<>();
	private double handleThree(String zip) {
		double result = 0;
		if (isValidZip(zip)) {
			if (requestedZIP_P3.keySet().contains(zip)) {
				result = requestedZIP_P3.get(zip);
			} else {
				ResidentialMarketValueCollector marketValueCollector = new ResidentialMarketValueCollector();
				ResidentialProcessor residentialProcessor = new ResidentialProcessor(marketValueCollector);
				double sumAndCount[] = residentialProcessor.process(zip);
				if (sumAndCount[1]!=0) {
					result = sumAndCount[0]/sumAndCount[1];
				}
				requestedZIP_P3.put(zip, result);
			}
		}
		return result;
	}

	Map<String, Double> requestedZIP_P4 = new HashMap<>();
	private double handleFour(String zip) {
		double result = 0;
		if (isValidZip(zip)) {
			if (requestedZIP_P4.keySet().contains(zip)) {
				result = requestedZIP_P3.get(zip);
			} else {
				ResidentialTLACollector TLACollector = new ResidentialTLACollector();
				ResidentialProcessor residentialProcessor = new ResidentialProcessor(TLACollector);
				double sumAndCount[] = residentialProcessor.process(zip);
				if (sumAndCount[1]!=0) {
					result = sumAndCount[0]/sumAndCount[1];
				}
				requestedZIP_P4.put(zip, result);
			}
		}
		return result;
	}
	Map<String, Double> requestedZIP_P5 = new HashMap<>();
	private double handleFive(String zip) {
		double result = 0;
		if (isValidZip(zip)) {
			if (requestedZIP_P5.keySet().contains(zip)) {
				result = requestedZIP_P5.get(zip);
			} else {
				PopulationReader populationReader = new PopulationReader();
				int populationPerZIP = populationReader.getPopulationPerZIP(zip);
				ResidentialMarketValueCollector marketValueCollector = new ResidentialMarketValueCollector();
				ResidentialProcessor residentialProcessor = new ResidentialProcessor(marketValueCollector);
				double sumAndCount[] = residentialProcessor.process(zip);
				if (populationPerZIP != 0 && sumAndCount[1]!=0) {

					result = sumAndCount[0]/populationPerZIP;
				}
				requestedZIP_P5.put(zip, result);
			}
		}
		return result;
	}

	Map<String, String> requestedZIP_P6 = new HashMap<>();
	private String handleSix(String zip) {
		int livableAreaPerCapita = 0;
		String violationReason = "no parking violation in this area";
		String result = "No information for this request";
		if (isValidZip(zip)) {
			if (requestedZIP_P6.keySet().contains(zip)) {
				result = requestedZIP_P6.get(zip);
			} else {
				ResidentialTLACollector TLACollector = new ResidentialTLACollector();
				ResidentialProcessor residentialProcessor = new ResidentialProcessor(TLACollector);
				PopulationReader populationReader = new PopulationReader();
				double sumLivableArea = residentialProcessor.process(zip)[0];
				double population = populationReader.getPopulationPerZIP(zip);
				if(population != 0) {
					livableAreaPerCapita = (int) (sumLivableArea / population);
				}
				Map<String, String> violationMap = ParkingViolationsProcessor.getViolationReasonMap();
				if(violationMap.containsKey(zip)) {
					violationReason = violationMap.get(zip);
				}
				result = "Livable area per capita is " + livableAreaPerCapita + 
						" and the top parking violation reason is " + violationReason + '\n';
				requestedZIP_P6.put(zip, result);
			}
		}
		return result;
	}

	private boolean isValidZip(String input) {
		return Pattern.matches("\\d{5}", input);

	}
}
