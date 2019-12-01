package edu.upenn.cit594.processor​;

import java.util.List;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.ParkingViolationsCSVReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsJSONReader;
import edu.upenn.cit594.datamanagement.ParkingViolationsReader;

public class ParkingViolationsProcessor {
	protected ParkingViolationsReader parkingViolationsReader;
	protected List<ParkingViolation> parkingViolationList;
	
	public ParkingViolationsProcessor(String format, String input) {
		if(format.equals("json")) {
			parkingViolationsReader = new ParkingViolationsJSONReader(input);
		}
		if(format.equals("csv")) {
			parkingViolationsReader = new ParkingViolationsCSVReader(input);
		}
		
		parkingViolationList = parkingViolationsReader.getAllParkingViolation();
	}

}
