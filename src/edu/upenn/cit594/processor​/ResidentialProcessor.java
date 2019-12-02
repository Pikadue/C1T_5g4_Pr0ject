package edu.upenn.cit594.processor​;

import edu.upenn.cit594.data.Residence;
import edu.upenn.cit594.datamanagement.PropertyReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResidentialProcessor {
    ResidentialInformationCollector infoCollector;
    static List<Residence> residenceList;
    PropertyReader pr;

    public ResidentialProcessor(ResidentialInformationCollector infoCollector) {
        this.infoCollector = infoCollector;
    }

    public ResidentialProcessor(PropertyReader pr) {
        this.pr = pr;
        try {
            residenceList = pr.read();
        } catch (IOException e) {
            System.out.println("The property file is unavailable.Program exits!");
            System.exit(0);
        }
    }

    public double[] process(String zip) {
        List<Residence> selectedByZip = selectByZip(zip);
        double[]information = infoCollector.getInformation(selectedByZip);
        int len = information.length;
        double sum = 0;
        for (int i = 0; i < len; i++) {
            sum += information[i];
        }
        //double average = sum / len;
        return new double[] {sum, len};
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

    public List<Residence> getResidenceList() {
        return residenceList;
    }


}
