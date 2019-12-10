package edu.upenn.cit594.processor​;

import edu.upenn.cit594.data.Residence;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.datamanagement.ResidentialInformationCollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResidentialProcessor {
    private ResidentialInformationCollector infoCollector;
    private static List<Residence> residenceList;
    private static HashMap<String, Double> residentialMap;
    private PropertyReader propertyReader;

    public ResidentialProcessor(ResidentialInformationCollector infoCollector) {
        this.infoCollector = infoCollector;
    }

    public ResidentialProcessor(PropertyReader pr) {
        this.propertyReader = pr;
        try {
            residenceList = propertyReader.read();
        } catch (IOException e) {
            System.out.println("The property file is unavailable. Program exits!");
            System.exit(0);
        }
    }

    public double[] process(String zip) {
        List<Residence> selectedByZip = selectByZip(zip);
        double[] information = infoCollector.getInformation(selectedByZip);
        int len = information.length;
        double sum = 0;
        for (int i = 0; i < len; i++) {
            sum += information[i];
        }
        //double average = sum / len;
        return new double[]{sum, len};
    }

    private List<Residence> selectByZip(String zip) {
        List<Residence> residenceListZIP = new ArrayList<>();
        for (Residence r : residenceList) {
            if (r.getZipCode().equals(zip)) {
                residenceListZIP.add(r);

            }
        }
        return residenceListZIP;
    }

    private static void generateResidentialMap() {
    	residentialMap = new HashMap<>();
    	double sumLivableArea = 0;
    	for (Residence x : residenceList) {
    		String zip = x.getZipCode();
    		Double livableArea = x.getTotalLivableArea();
    		if (residentialMap.keySet().contains(zip)) {
    			sumLivableArea = residentialMap.get(zip) + livableArea;
    			residentialMap.put(zip, sumLivableArea);
    		} else {
    			
    			residentialMap.put(zip, livableArea);
    		}

    	}
    }
    
    public static Map<String, Double> getResidenceMap(){
    	if(residentialMap == null) {
    		generateResidentialMap();
    	}
    	return residentialMap;
    }

    
    public List<Residence> getResidenceList() {
        return residenceList;
    }

}
