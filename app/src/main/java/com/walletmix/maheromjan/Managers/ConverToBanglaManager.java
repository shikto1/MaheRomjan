package com.walletmix.maheromjan.Managers;

/**
 * Created by Siddhartha on 16/05/2018.
 */

public class ConverToBanglaManager {

    public static String getInBangla(String string) {
        Character bangla_number[] = {'১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯', '০'};
        Character eng_number[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        String values = "";
        char[] character = string.toCharArray();
        for (int i = 0; i < character.length; i++) {
            Character c = ' ';
            for (int j = 0; j < eng_number.length; j++) {
                if (character[i] == eng_number[j]) {
                    c = bangla_number[j];
                    break;
                } else {
                    c = character[i];
                }
            }
            values = values + c;
        }
        return values;
    }
}
