package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Residence;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PropertyReader {

    public List<Residence> read(String fileName) throws IOException {

        List<Residence> residenceList = new ArrayList<>();//TODO
        File newFile = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(newFile));
        String firstLine = null;
        firstLine = br.readLine();

        //[0] total_livable_area [1] market_value [2]zip_code
        int[] informationColumn = findColumn(firstLine);
        String line = null;
        while((line = br.readLine()) != null) {
            String[] lineElements = line.split(",");
            String total_livable_areaStr = lineElements[informationColumn[0]];
            String market_valueStr = lineElements[informationColumn[1]];
            String zip_codeStr = lineElements[informationColumn[2]];

            if (isValidResidence(total_livable_areaStr, market_valueStr, zip_codeStr)) {
                double total_livable_area = Double.parseDouble(total_livable_areaStr);
                double market_value = Double.parseDouble(market_valueStr);
                String zip_code = zip_codeStr.substring(0,5);

                Residence current = new Residence(total_livable_area, market_value, zip_code);
                residenceList.add(current);
            }
        }

        br.close();
        return residenceList;
    }

    private boolean isValidResidence(String livableArea, String marketValue, String zipCode) {

        boolean validLivableArea = Pattern.matches("^\\d+\\.?\\d?$", livableArea);
        boolean validMarketValue = Pattern.matches("^\\d+\\.?\\d?$", marketValue);
        boolean validZipCode = Pattern.matches("^\\d{9}$", zipCode);
        return validLivableArea && validMarketValue && validZipCode;
    }



    private int[] findColumn(String firstLine) {
        int[] columns = new int[3];
        String[] headers = firstLine.split(",");

        for (int i = 0; i< headers.length;i++) {
            if (headers[i].equals("total_livable_area")) {
                columns[0] = i;
            } else if (headers[i].equals("market_value")) {
                columns[1] = i;
            } else  if (headers[i].equals("zip_code")) {
                columns[2] = i;
            }
        }
        return columns;

    }

    public static void main (String[] args) {
        PropertyReader pr = new PropertyReader();
        try {
            List<Residence> lr = pr.read("sample.csv");
        } catch (IOException e) {
            System.out.println("Property file not found");
        }
        PopulationReader poR = new PopulationReader();
        try {
            poR.read("population.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Population file not found");
        }

    }
}
