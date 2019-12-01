package edu.upenn.cit594.ui;

import java.util.Scanner;

public class userInput {
    private static userInput obj;

    private userInput() {
        start();

    }
    public static userInput getInstance(){
        if(obj == null) obj = new userInput();

        return obj;
    }
    private void start(){
        Scanner in  = new Scanner(System.in);
        System.out.println("Enter a number between 0-6, to conduct the actions below\n0: exit\n1: show total " +
                "population for all ZIP codes\n2: show total parking fines per capita for each ZIP code\n" +
                "3: show average market value for residences in a specified ZIP code\n" +
                "4: show the average total livable area for residences in a specified ZIP code\n" +
                "5: show the total residential market value per capita for a specified ZIP code\n" +
                "6: show the results of our custom feature");

        String userInput = in.nextLine();
        if (isCorrect(userInput)) {

        }
    }

    private boolean isCorrect (String input) {
        

    }
}
