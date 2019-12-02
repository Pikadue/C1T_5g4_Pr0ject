package edu.upenn.cit594.processor​;

import edu.upenn.cit594.data.Residence;

import java.util.List;

public class ResidentialMarketValueCollector implements ResidentialInformationCollector {
    @Override
    public double[] getInformation(List<Residence> listOfResidence){
        double[] marketValuePerZIP = new double[listOfResidence.size()];
        int i = 0;
        for (Residence r: listOfResidence) {
            marketValuePerZIP[i] = r.getMarketValue();
            i++;
        }
    return marketValuePerZIP;
    }

}
