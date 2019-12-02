package edu.upenn.cit594.processor​;

import edu.upenn.cit594.data.Residence;

import java.util.List;

public interface ResidentialInformationCollector {
    public double[] getInformation(List<Residence> listOfResidence);

}
