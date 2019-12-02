package edu.upenn.cit594.processor​;

import edu.upenn.cit594.data.Residence;

import java.util.List;

public class ResidentialTLACollector implements ResidentialInformationCollector {
    @Override
    public double[] getInformation(List<Residence> listOfResidence) {
        double[] livableAreaPerZIP = new double[listOfResidence.size()];
        int i = 0;
        for (Residence r : listOfResidence) {
            livableAreaPerZIP[i] = r.getTotalLivableArea();
            i++;
        }
        return livableAreaPerZIP;

    }
}
