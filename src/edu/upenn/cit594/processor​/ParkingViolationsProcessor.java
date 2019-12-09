package edu.upenn.cit594.processor​;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingViolationsCSVReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsJSONReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsReader;
import edu.upenn.cit594.datamanagement.PopulationReader;

public class ParkingViolationsProcessor {
	private ParkingViolationsReader parkingViolationsReader;
	private static List<ParkingViolation> parkingViolationList;
	private static Map<String, Integer> populationMap;
	private static Map<String, Integer> parkingViolationFineMap; //zipcode, total fine
	private static Map<String, BigDecimal> avgFineMap; //TODO
	private static Map<String, String> violationReasonMap;
	private static String avgFineResult = "";


	public ParkingViolationsProcessor(String format, String input) {
		if(format.equals("json")) {
			parkingViolationsReader = new ParkingViolationsJSONReader(input);
		}
		if(format.equals("csv")) {
			parkingViolationsReader = new ParkingViolationsCSVReader(input);
		}

		ParkingViolationsProcessor.parkingViolationList = parkingViolationsReader.getAllParkingViolation();
		ParkingViolationsProcessor.parkingViolationFineMap = new HashMap<>();
		ParkingViolationsProcessor.avgFineMap = new TreeMap<>();
		ParkingViolationsProcessor.violationReasonMap = new HashMap<>();

		populationMap = PopulationReader.getPopulationMap(); 
		// run the calculation when the program starts
//		avgFineMap = getAvgFineMap();
//		violationReasonMap = getViolationReasonMap();
	}

	// implementation of problem #2 step 1: total parking fines for each ZIP Code
	private static Map<String, Integer>  generateParkingFineMap() {
		for(ParkingViolation item: parkingViolationList) {
			String zipCode = item.getZipCode();
			int fine = item.getFine();
			String state = item.getState();
			if(state.equals("PA") && !zipCode.isBlank() && !zipCode.isEmpty()) {
				if(parkingViolationFineMap.containsKey(zipCode)) {
					int fineUpdate = parkingViolationFineMap.get(zipCode) + fine;
					parkingViolationFineMap.put(zipCode, fineUpdate);
				}
				else {
					parkingViolationFineMap.put(zipCode, fine);
				}
			}	
		}

		return parkingViolationFineMap;
	}


	// implementation of problem #2 step 2: total parking fines per capita for each ZIP Code

	private static Map<String, BigDecimal>  getAvgFineMap() {
		if(parkingViolationFineMap.isEmpty()) {
			parkingViolationFineMap = generateParkingFineMap();
			for(Entry<String, Integer> item: populationMap.entrySet()) {
				String zipCode = item.getKey();
				int population = item.getValue();
				double avgFine = 0;
				if(parkingViolationFineMap.containsKey(zipCode)) {
					avgFine = (double) parkingViolationFineMap.get(zipCode)/ (double) population;
					BigDecimal avgFineUpdate = BigDecimal.valueOf(avgFine);
					avgFineUpdate = avgFineUpdate.setScale(4, RoundingMode.DOWN);
					avgFineMap.put(zipCode, avgFineUpdate);
				} 
			}
		}
		return avgFineMap;
	}


	public static String getAvgFine() {

		if(avgFineMap.isEmpty()) {
			avgFineMap = getAvgFineMap();
			for(Entry<String, BigDecimal> item: avgFineMap.entrySet()) {
				String zipCode = item.getKey();
				BigDecimal avgFine = item.getValue(); 
				avgFineResult = avgFineResult + zipCode + " " + avgFine + '\n';

			}

		} 
		

		return avgFineResult;
	}

	// implementation of problem #6: top #1 reason for each ZIP Code in PA

	public static Map<String, String>  getViolationReasonMap() {
		Map<String, Map<String, Integer>> violationCountMap = new HashMap<>();


		for (ParkingViolation item: parkingViolationList) {
			String zipCode = item.getZipCode();
			String reason = item.getReason();
			String state = item.getState();
			int count =0;
			if(!state.equals("PA")) {
				continue;
			}
			if(zipCode.isBlank() || zipCode.isEmpty()) {
				continue;
			}
			if(violationCountMap.containsKey(zipCode)) {
				Map<String, Integer> tempMap = violationCountMap.get(zipCode);
				if(tempMap.containsKey(reason)) {
					count = tempMap.get(reason) + 1;
				}
				tempMap.put(reason, count);
				violationCountMap.put(zipCode, tempMap);
			} else {
				Map<String, Integer> tempMap = new HashMap<>();
				tempMap.put(reason, 0);
				violationCountMap.put(zipCode, tempMap);
			}
		}

		for(String zipCodeKey: violationCountMap.keySet()) {
			Map<String, Integer> tempMap = violationCountMap.get(zipCodeKey);
			int max = -1;
			String topReason = null;
			for(String reasonKey: tempMap.keySet()) {
				int count = tempMap.get(reasonKey);
				if(count > max) {
					max = count;
					topReason = reasonKey;
				}
			}
			violationReasonMap.put(zipCodeKey, topReason);
		}


		return violationReasonMap;
	}

}
