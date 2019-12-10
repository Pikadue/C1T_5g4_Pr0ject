package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Residence;

import java.util.List;

public class ResidentialMarketValueCollector implements ResidentialInformationCollector {
    @Override
    public double[] getInformation(List<Residence> listOfResidence){
        double[] marketValuePerZIP = new double[listOfResidence.size()];
        int i = 0;
        for (Residence r: listOfResidence) {
            marketValuePerZIP[i] = r.getMarketValue();
//            System.out.println(i+" market value is: "+marketValuePerZIP[i]);
            i++;
        }
    return marketValuePerZIP;
    }

}
