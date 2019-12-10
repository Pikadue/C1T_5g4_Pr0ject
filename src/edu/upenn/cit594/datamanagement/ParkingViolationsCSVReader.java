package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logging;


/*
 * This is one of the classes in the "Data Management" tier.
 * It handles the reading of the parking violation file and exposes a method to be
 * used by the ParkingViolationProcessor to get all the parking violation instances.
 */

public class ParkingViolationsCSVReader implements ParkingViolationsReader{
	private String fileName;
	private List<ParkingViolation> parkingViolationsList;


	public ParkingViolationsCSVReader(String input){
		fileName = input;
		parkingViolationsList = new LinkedList<>();
	}

	@Override
	public List<ParkingViolation> getAllParkingViolation() {
		// TODO Auto-generated method stub
		String row = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			Logging.getInstance().log(fileName);
			BufferedReader csvReader = new BufferedReader(fileReader);
			try {
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(",");
					if(data.length != 7) {
						continue;
					}
					/*
					 * Data example : 2013-04-03T15:15:00Z,36,METER EXPIRED CC,1322731,PA,2905938,19104
					 */

					String date = data[0];
					int fine = Integer.parseInt(data[1]);
					String violation = data[2];
					String plate_id = data[3];
					String state = data[4];
					String ticket_number = data[5];
					String zip_code = data[6];
					parkingViolationsList.add(new ParkingViolation(ticket_number, plate_id, date, 
							zip_code, violation, fine, state));
				}
				csvReader.close();
			} catch (NumberFormatException | IOException e) {
				System.out.println("Invalid data!");
				System.exit(0);
			}

		} catch (FileNotFoundException e1) {
			System.out.println("Parking violation file does not exist. Program exit.");
			System.exit(0);
		}
		return parkingViolationsList;
	}

}
