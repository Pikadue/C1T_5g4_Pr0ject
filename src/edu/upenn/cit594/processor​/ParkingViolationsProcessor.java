package edu.upenn.cit594.processor​;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingViolationsCSVReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsJSONReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsReader;
import edu.upenn.cit594.datamanagement.PopulationReader;

public class ParkingViolationsProcessor {
	protected ParkingViolationsReader parkingViolationsReader;
	protected List<ParkingViolation> parkingViolationList;
	protected Map<String, Integer> populationMap;
	protected Map<String, Integer> parkingViolationFineMap; //zipcode, total fine
	protected Map<String, String> violationReasonMap;

	public ParkingViolationsProcessor(String format, String input) {
		if(format.equals("json")) {
			parkingViolationsReader = new ParkingViolationsJSONReader(input);
		}
		if(format.equals("csv")) {
			parkingViolationsReader = new ParkingViolationsCSVReader(input);
		}

		parkingViolationList = parkingViolationsReader.getAllParkingViolation();
		parkingViolationFineMap = generateParkingFineMap();
		violationReasonMap = getViolationReasonMap();

		populationMap = PopulationReader.getPopulationMap(); 
	}

	// implementation of problem 2 step 1: total parking fines for each ZIP Code
	private Map<String, Integer>  generateParkingFineMap() {
		for(ParkingViolation item: parkingViolationList) {
			String zipCode = item.getZipCode();
			int fine = item.getFine();
			if(parkingViolationFineMap.containsKey(zipCode)) {
				int fineUpdate = parkingViolationFineMap.get(zipCode) + fine;
				parkingViolationFineMap.put(zipCode, fineUpdate);
			} else {
				parkingViolationFineMap.put(zipCode, fine);
			}
		}

		return parkingViolationFineMap;
	}


	// implementation of problem #2: total parking fines per capita for each ZIP Code

	public Map<String, Integer>  getAvgFineMap() {
		Map<String, Integer> avgFineMap = new HashMap<>();
		for(Entry<String, Integer> item: populationMap.entrySet()) {
			String zipCode = item.getKey();
			int population = item.getValue();
			int avgFine = 0;
			if(parkingViolationFineMap.containsKey(zipCode)) {
				avgFine = parkingViolationFineMap.get(zipCode) / population;
				avgFineMap.put(zipCode, avgFine);
			} 
		}

		return avgFineMap;
	}


	// implementation of problem #6: top #1 reason for each ZIP Code

	public Map<String, String>  getViolationReasonMap() {
		Map<String, Map<String, Integer>> violationCountMap = new HashMap<>();


		for (ParkingViolation item: parkingViolationList) {
			String zipCode = item.getZipCode();
			String reason = item.getReason();
			int count =0;
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
