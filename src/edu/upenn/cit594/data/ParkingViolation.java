package edu.upenn.cit594.data;

/*
 * This class represents a single class offering.
 * It is not part of any tier but is rather used across tiers.
 */

public class ParkingViolation {
    String ticket_number;
    String plate_id;
    String date;
    String zip_code;
    String violation;
    int fine;
    String state;

    public ParkingViolation(String ticket_number, String plate_id, String date, String zip_code,
                            String violation, int fine, String state) {
        this.ticket_number = ticket_number;
        this.plate_id = plate_id;
        this.date = date;
        this.zip_code = zip_code;
        this.violation = violation;
        this.fine = fine;
        this.state = state;
    }
    
    public int getFine() {
    	return this.fine;
    }
    
    public String getZipCode() {
    	return this.zip_code;
    }
    
    public String getReason() {
    	return this.violation;
    }
    
    public String getState() {
    	return this.state;
    }

}
