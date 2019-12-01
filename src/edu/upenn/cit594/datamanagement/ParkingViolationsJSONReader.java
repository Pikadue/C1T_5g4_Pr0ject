package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingViolation;
<<<<<<< HEAD
import edu.upenn.cit594.datamanagement​.ParkingViolationsReader;
=======
>>>>>>> origin/master

/*
 * This is one of the classes in the "Data Management" tier.
 * It handles the reading of the parking violation file and exposes a method to be
 * used by the ParkingViolationProcessor to get all the parking violation instances.
 */

public class ParkingViolationsJSONReader implements ParkingViolationsReader{
	String filename;
	List<ParkingViolation> parkingViolationsList;


	public ParkingViolationsJSONReader(String input){
		filename = input;
		parkingViolationsList = new ArrayList<>();
	}


	@Override
	public List<ParkingViolation> getAllParkingViolation() {
		// create a parser
		JSONParser parser = new JSONParser();
		// open the file and get the array of JSON objects
		JSONArray parkingViolationArray;
		
		try {
			FileReader fileReader = new FileReader(filename);
			Object obj = parser.parse(fileReader);
			parkingViolationArray = (JSONArray) obj;
			// use an iterator to iterate over each element of the array 
			Iterator<?> iter = parkingViolationArray.iterator();
			// iterate while there are more objects in array 
			while (iter.hasNext()) {
				// get the next JSON object
				JSONObject parkingViolation = (JSONObject) iter.next();
				// use the "get" method to print the value associated with that key
				
				/*
				 * [{"ticket_number":2905938,
				 * "plate_id":"1322731",
				 * "date":"2013-04-03T15:15:00Z",
				 * "zip_code":"19104",
				 * "violation":"METER EXPIRED CC",
				 * "fine":36,"state":"PA"}
				 */
				
				String ticket_number = (String) parkingViolation.get("ticket_number");
				String date = (String) parkingViolation.get("date");
				String plate_id = (String) parkingViolation.get("plate_id");
				String zip_code = (String) parkingViolation.get("zip_code");
				String violation = (String) parkingViolation.get("violation");
				int fine = (int) parkingViolation.get("fine");
				String state = (String) parkingViolation.get("state");
				
				parkingViolationsList.add(new ParkingViolation(ticket_number, plate_id, date, 
						zip_code, violation, fine, state));
			}
			
		} catch (IOException | ParseException e) {
			System.out.println("Invalid file!");
            System.exit(0);
		}
		

		return parkingViolationsList;
	}

}
