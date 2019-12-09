package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Residence;
import edu.upenn.cit594.logging.Logging;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class PropertyReader {
    private String fileName;
    public PropertyReader(String fileName){
        this.fileName = fileName;
    };

    public List<Residence> read() throws IOException {

        List<Residence> residenceList = new LinkedList<>();
        File newFile = new File(fileName);
        Logging.getInstance().log(fileName);

        BufferedReader br = new BufferedReader(new FileReader(newFile));
        String firstLine;
        firstLine = br.readLine();

        //[0] total_livable_area [1] market_value [2]zip_code
        int[] informationColumn = findColumn(firstLine);
        String line;
        while((line = br.readLine()) != null) {
//        	String[] lineElements = line.split(","); // TODO: this line is for fast processing ONLY
        	String[] lineElements = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            String total_livable_areaStr = lineElements[informationColumn[0]].strip();
            String market_valueStr = lineElements[informationColumn[1]].strip();
            String zip_codeStr = lineElements[informationColumn[2]].strip();

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

    /*Check if the information in current line is valid */
    private boolean isValidResidence(String livableArea, String marketValue, String zipCode) {

        boolean validLivableArea = Pattern.matches("^\\d+\\.?\\d*$", livableArea);
        boolean validMarketValue = Pattern.matches("^\\d+\\.?\\d*$", marketValue);
//        boolean validZipCode = Pattern.matches("^[0-9]{5}-?\\d*$", zipCode);
        boolean validZipCode = Pattern.matches("^[0-9]{5}.*$", zipCode);
        return validLivableArea && validMarketValue && validZipCode;
    }


    /* Find corresponding columns for 3 key fields*/
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
}
