package edu.upenn.cit594.data;

public class Residence {
    //Size of home in square feet
    private double totalLivableArea;
    //Dollar value of the home
    private double marketValue;
    private int zipCode;

    public Residence(double totalLivableArea, double marketValue, int zipCode){
        this.totalLivableArea = totalLivableArea;
        this.marketValue = marketValue;
        this.zipCode = zipCode;

    }
    public String toString(){
        return totalLivableArea + "-" + marketValue + "-" + zipCode;
    }

}
