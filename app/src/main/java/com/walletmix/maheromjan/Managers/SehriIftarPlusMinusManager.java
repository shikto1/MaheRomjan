package com.walletmix.maheromjan.Managers;

import android.content.Context;

import com.walletmix.maheromjan.AllDistrictGroupWise.IftarMinus;
import com.walletmix.maheromjan.AllDistrictGroupWise.IftarPlus;
import com.walletmix.maheromjan.AllDistrictGroupWise.SehriMinus;
import com.walletmix.maheromjan.AllDistrictGroupWise.SehriPlus;
import com.walletmix.maheromjan.Database.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Siddhartha on 15/05/2018.
 */

public class SehriIftarPlusMinusManager {

    private static SehriIftarPlusMinusManager sehriIftarPlusMinusManager;
    private SessionManager sessionManager;

    private String DHAKA = "ঢাকা";
    private String MANIKGANJ = "মানিকগঞ্জ";
    private String SYLHET = "সিলেট";
    private String PONCHOGOR = "পঞ্চগড়";
    private String SEHRI_HOUR = "03";
    private String IFTAR_HOUR = "06";
    private String SEHRI_SAME_TO_DHAKA[] = {"নারায়ণগঞ্জ", " চাঁদপুর", "নোয়াখালি", "মুন্সিগঞ্জ", "পঞ্চগড় ", "নীলফামারী"};
    private String IFTAR_SAME_TO_DHAKA[] = {"নারায়ণগঞ্জ", "গাজীপুর", "পিরোজপুর", "মাদারীপুর", "কিশোরগঞ্জ"};
    private String selected_district = "";

    private ArrayList<String> SEHRI_PLUS_ONE_M;
    private ArrayList<String> SEHRI_PLUS_TWO_M;
    private ArrayList<String> SEHRI_PLUS_THREE_M;
    private ArrayList<String> SEHRI_PLUS_FOUR_M;
    private ArrayList<String> SEHRI_PLUS_FIVE_M;
    private ArrayList<String> SEHRI_PLUS_SIX_M;
    private ArrayList<String> SEHRI_PLUS_SEVEN_M;
    private ArrayList<String> SEHRI_PLUS_EIGHT_M;


    private ArrayList<String> SEHRI_MINUS_ONE_M;
    private ArrayList<String> SEHRI_MINUS_TWO_M;
    private ArrayList<String> SEHRI_MINUS_THREE_M;
    private ArrayList<String> SEHRI_MINUS_FOUR_M;
    private ArrayList<String> SEHRI_MINUS_FIVE_M;
    private ArrayList<String> SEHRI_MINUS_SEVEN_M;
    private ArrayList<String> SEHRI_MINUS_NINE_M;


    private ArrayList<String> IFTAR_MINUS_ONE_M;
    private ArrayList<String> IFTAR_MINUS_TWO_M;
    private ArrayList<String> IFTAR_MINUS_THREE_M;
    private ArrayList<String> IFTAR_MINUS_FOUR_M;
    private ArrayList<String> IFTAR_MINUS_FIVE_M;
    private ArrayList<String> IFTAR_MINUS_EIGHT_M;
    private ArrayList<String> IFTAR_MINUS_NINE_M;
    private ArrayList<String> IFTAR_MINUS_TEN_M;


    private ArrayList<String> IFTAR_PLUS_ONE_M;
    private ArrayList<String> IFTAR_PLUS_TWO_M;
    private ArrayList<String> IFTAR_PLUS_THREE_M;
    private ArrayList<String> IFTAR_PLUS_FOUR_M;
    private ArrayList<String> IFTAR_PLUS_FIVE_M;
    private ArrayList<String> IFTAR_PLUS_SIX_M;
    private ArrayList<String> IFTAR_PLUS_SEVEN_M;
    private ArrayList<String> IFTAR_PLUS_EIGHT_M;
    private ArrayList<String> IFTAR_PLUS_TEN_M;
    private ArrayList<String> IFTAR_PLUS_TWELVE_M;


    public SehriIftarPlusMinusManager(Context context){
        setupList();
        sessionManager = SessionManager.getInstance(context);
    }

    private void setupList() {

        //Creating SEHRI PLUS LIST....
        SEHRI_PLUS_ONE_M = new ArrayList<>();
        SEHRI_PLUS_TWO_M = new ArrayList<>();
        SEHRI_PLUS_THREE_M = new ArrayList<>();
        SEHRI_PLUS_FOUR_M = new ArrayList<>();
        SEHRI_PLUS_FIVE_M = new ArrayList<>();
        SEHRI_PLUS_SIX_M = new ArrayList<>();
        SEHRI_PLUS_SEVEN_M = new ArrayList<>();
        SEHRI_PLUS_EIGHT_M = new ArrayList<>();

        SEHRI_PLUS_ONE_M.addAll(Arrays.asList(SehriPlus.ONE_MIN));
        SEHRI_PLUS_TWO_M.addAll(Arrays.asList(SehriPlus.TWO_MIN));
        SEHRI_PLUS_THREE_M.addAll(Arrays.asList(SehriPlus.THREE_MIN));
        SEHRI_PLUS_FOUR_M.addAll(Arrays.asList(SehriPlus.FOUR_MIN));
        SEHRI_PLUS_FIVE_M.addAll(Arrays.asList(SehriPlus.FIVE_MIN));
        SEHRI_PLUS_SIX_M.addAll(Arrays.asList(SehriPlus.SIX_MIN));
        SEHRI_PLUS_SEVEN_M.addAll(Arrays.asList(SehriPlus.SEVEN_MIN));
        SEHRI_PLUS_EIGHT_M.addAll(Arrays.asList(SehriPlus.EIGHT_MIN));

        //Creating SEHRI MINUS.....
        SEHRI_MINUS_ONE_M = new ArrayList<>();
        SEHRI_MINUS_TWO_M = new ArrayList<>();
        SEHRI_MINUS_THREE_M = new ArrayList<>();
        SEHRI_MINUS_FOUR_M = new ArrayList<>();
        SEHRI_MINUS_FIVE_M = new ArrayList<>();
        SEHRI_MINUS_SEVEN_M = new ArrayList<>();
        SEHRI_MINUS_NINE_M = new ArrayList<>();

        SEHRI_MINUS_ONE_M.addAll(Arrays.asList(SehriMinus.ONE_MIN));
        SEHRI_MINUS_TWO_M.addAll(Arrays.asList(SehriMinus.TWO_MIN));
        SEHRI_MINUS_THREE_M.addAll(Arrays.asList(SehriMinus.THREE_MIN));
        SEHRI_MINUS_FOUR_M.addAll(Arrays.asList(SehriMinus.FOUR_MIN));
        SEHRI_MINUS_FIVE_M.addAll(Arrays.asList(SehriMinus.FIVE_MIN));
        SEHRI_MINUS_SEVEN_M.addAll(Arrays.asList(SehriMinus.SEVEN_MIN));
        SEHRI_MINUS_NINE_M.addAll(Arrays.asList(SehriMinus.NINE_MIN));

        //CREATING IFTAR PLUS LIST>>>
        IFTAR_PLUS_ONE_M = new ArrayList<>();
        IFTAR_PLUS_TWO_M = new ArrayList<>();
        IFTAR_PLUS_THREE_M = new ArrayList<>();
        IFTAR_PLUS_FOUR_M = new ArrayList<>();
        IFTAR_PLUS_FIVE_M = new ArrayList<>();
        IFTAR_PLUS_SIX_M = new ArrayList<>();
        IFTAR_PLUS_SEVEN_M = new ArrayList<>();
        IFTAR_PLUS_EIGHT_M = new ArrayList<>();
        IFTAR_PLUS_TEN_M = new ArrayList<>();
        IFTAR_PLUS_TWELVE_M = new ArrayList<>();

        IFTAR_PLUS_ONE_M.addAll(Arrays.asList(IftarPlus.ONE_MIN));
        IFTAR_PLUS_TWO_M.addAll(Arrays.asList(IftarPlus.TWO_MIN));
        IFTAR_PLUS_THREE_M.addAll(Arrays.asList(IftarPlus.THREE_MIN));
        IFTAR_PLUS_FOUR_M.addAll(Arrays.asList(IftarPlus.FOUR_MIN));
        IFTAR_PLUS_FIVE_M.addAll(Arrays.asList(IftarPlus.FIVE_MIN));
        IFTAR_PLUS_SIX_M.addAll(Arrays.asList(IftarPlus.SIX_MIN));
        IFTAR_PLUS_SEVEN_M.addAll(Arrays.asList(IftarPlus.SEVEN_MIN));
        IFTAR_PLUS_EIGHT_M.addAll(Arrays.asList(IftarPlus.EIGHT_MIN));
        IFTAR_PLUS_TEN_M.addAll(Arrays.asList(IftarPlus.TEN_MIN));
        IFTAR_PLUS_TWELVE_M.addAll(Arrays.asList(IftarPlus.TWELVE_MIN));


        //Iftar MInus list....
        IFTAR_MINUS_ONE_M = new ArrayList<>();
        IFTAR_MINUS_TWO_M = new ArrayList<>();
        IFTAR_MINUS_THREE_M = new ArrayList<>();
        IFTAR_MINUS_FOUR_M = new ArrayList<>();
        IFTAR_MINUS_FIVE_M = new ArrayList<>();
        IFTAR_MINUS_EIGHT_M = new ArrayList<>();
        IFTAR_MINUS_NINE_M = new ArrayList<>();
        IFTAR_MINUS_TEN_M = new ArrayList<>();

        IFTAR_MINUS_ONE_M.addAll(Arrays.asList(IftarMinus.ONE_MIN));
        IFTAR_MINUS_TWO_M.addAll(Arrays.asList(IftarMinus.TWO_MIN));
        IFTAR_MINUS_THREE_M.addAll(Arrays.asList(IftarMinus.THREE_MIN));
        IFTAR_MINUS_FOUR_M.addAll(Arrays.asList(IftarMinus.FOUR_MIN));
        IFTAR_MINUS_FIVE_M.addAll(Arrays.asList(IftarMinus.FIVE_MIN));
        IFTAR_MINUS_EIGHT_M.addAll(Arrays.asList(IftarMinus.EIGHT_MIN));
        IFTAR_MINUS_NINE_M.addAll(Arrays.asList(IftarMinus.NINE_MIN));
        IFTAR_MINUS_TEN_M.addAll(Arrays.asList(IftarMinus.TEN_MIN));




    }


    public String getSehri(int sehriMinute) {
        selected_district = sessionManager.getString(SessionManager.Key.SELECTED_DISTRICT);
        String value = "";
        if (selected_district == DHAKA || Arrays.asList(SEHRI_SAME_TO_DHAKA).contains(selected_district)) {
            // Nothing to do for this
        }else  if (SEHRI_PLUS_ONE_M.contains(selected_district)){
            sehriMinute = sehriMinute + 1;
        }else if (SEHRI_PLUS_TWO_M.contains(selected_district)){
            sehriMinute = sehriMinute + 2;
        }else if (SEHRI_PLUS_THREE_M.contains(selected_district)){
            sehriMinute = sehriMinute + 3;
        }else if (SEHRI_PLUS_FOUR_M.contains(selected_district)){
            sehriMinute = sehriMinute + 4;
        }else if (SEHRI_PLUS_FIVE_M.contains(selected_district)){
            sehriMinute = sehriMinute + 5;
        }else if (SEHRI_PLUS_SIX_M.contains(selected_district)){
            sehriMinute = sehriMinute + 6;
        }else if (SEHRI_PLUS_SEVEN_M.contains(selected_district)){
            sehriMinute = sehriMinute + 7;
        }else if (SEHRI_PLUS_EIGHT_M.contains(selected_district)){
            sehriMinute = sehriMinute + 8;
        }else  if (SEHRI_MINUS_ONE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 1;
        }else if (SEHRI_MINUS_TWO_M.contains(selected_district)){
            sehriMinute = sehriMinute - 2;
        }else if (SEHRI_MINUS_THREE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 3;
        }else if (SEHRI_MINUS_FOUR_M.contains(selected_district)){
            sehriMinute = sehriMinute - 4;
        }else if (SEHRI_MINUS_FIVE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 5;
        }else if (SEHRI_MINUS_SEVEN_M.contains(selected_district)){
            sehriMinute = sehriMinute - 7;
        }else if (SEHRI_MINUS_NINE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 9;
        }
        value = SEHRI_HOUR + ":" + sehriMinute;
        return value;
    }

    public int getSehriMinute(int sehriMinute) {
        selected_district = sessionManager.getString(SessionManager.Key.SELECTED_DISTRICT);
        if (selected_district == DHAKA || Arrays.asList(SEHRI_SAME_TO_DHAKA).contains(selected_district)) {
            // Nothing to do for this
        }else  if (SEHRI_PLUS_ONE_M.contains(selected_district)){
            sehriMinute = sehriMinute + 1;
        }else if (SEHRI_PLUS_TWO_M.contains(selected_district)){
            sehriMinute = sehriMinute + 2;
        }else if (SEHRI_PLUS_THREE_M.contains(selected_district)){
            sehriMinute = sehriMinute + 3;
        }else if (SEHRI_PLUS_FOUR_M.contains(selected_district)){
            sehriMinute = sehriMinute + 4;
        }else if (SEHRI_PLUS_FIVE_M.contains(selected_district)){
            sehriMinute = sehriMinute + 5;
        }else if (SEHRI_PLUS_SIX_M.contains(selected_district)){
            sehriMinute = sehriMinute + 6;
        }else if (SEHRI_PLUS_SEVEN_M.contains(selected_district)){
            sehriMinute = sehriMinute + 7;
        }else if (SEHRI_PLUS_EIGHT_M.contains(selected_district)){
            sehriMinute = sehriMinute + 8;
        }else  if (SEHRI_MINUS_ONE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 1;
        }else if (SEHRI_MINUS_TWO_M.contains(selected_district)){
            sehriMinute = sehriMinute - 2;
        }else if (SEHRI_MINUS_THREE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 3;
        }else if (SEHRI_MINUS_FOUR_M.contains(selected_district)){
            sehriMinute = sehriMinute - 4;
        }else if (SEHRI_MINUS_FIVE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 5;
        }else if (SEHRI_MINUS_SEVEN_M.contains(selected_district)){
            sehriMinute = sehriMinute - 7;
        }else if (SEHRI_MINUS_NINE_M.contains(selected_district)){
            sehriMinute = sehriMinute - 9;
        }
        return sehriMinute;
    }

    public String getIftar(int IFTARMinute) {
        selected_district = sessionManager.getString(SessionManager.Key.SELECTED_DISTRICT);
        String value = "";
        if (selected_district == DHAKA || Arrays.asList(IFTAR_SAME_TO_DHAKA).contains(selected_district)) {
            // Nothing to do for this .....
        }else  if (IFTAR_PLUS_ONE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 1;
        }else if (IFTAR_PLUS_TWO_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 2;
        }else if (IFTAR_PLUS_THREE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 3;
        }else if (IFTAR_PLUS_FOUR_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 4;
        }else if (IFTAR_PLUS_FIVE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 5;
        }else if (IFTAR_PLUS_SIX_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 6;
        }else if (IFTAR_PLUS_SEVEN_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 7;
        }else if (IFTAR_PLUS_EIGHT_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 8;
        }else if (IFTAR_PLUS_TEN_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 10;
        }else if (IFTAR_PLUS_TWELVE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 12;
        }else  if (IFTAR_MINUS_ONE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 1;
        }else if (IFTAR_MINUS_TWO_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 2;
        }else if (IFTAR_MINUS_THREE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 3;
        }else if (IFTAR_MINUS_FOUR_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 4;
        }else if (IFTAR_MINUS_FIVE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 5;
        }else if (IFTAR_MINUS_EIGHT_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 8;
        }else if (IFTAR_MINUS_NINE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 9;
        }else if (IFTAR_MINUS_TEN_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 10;
        }

        if (IFTARMinute >= 60){
            int remainder = IFTARMinute - 60;
            value = "07" + ":" + (remainder<10 ? "0"+remainder : remainder);
        }else{
            value = IFTAR_HOUR + ":" + IFTARMinute;
        }
        return value;
    }
    public int getIftarMinute(int IFTARMinute) {
        selected_district = sessionManager.getString(SessionManager.Key.SELECTED_DISTRICT);
        if (selected_district == DHAKA || Arrays.asList(IFTAR_SAME_TO_DHAKA).contains(selected_district)) {
            // Nothing to do for this .....
        }else  if (IFTAR_PLUS_ONE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 1;
        }else if (IFTAR_PLUS_TWO_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 2;
        }else if (IFTAR_PLUS_THREE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 3;
        }else if (IFTAR_PLUS_FOUR_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 4;
        }else if (IFTAR_PLUS_FIVE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 5;
        }else if (IFTAR_PLUS_SIX_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 6;
        }else if (IFTAR_PLUS_SEVEN_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 7;
        }else if (IFTAR_PLUS_EIGHT_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 8;
        }else if (IFTAR_PLUS_TEN_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 10;
        }else if (IFTAR_PLUS_TWELVE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute + 12;
        }else  if (IFTAR_MINUS_ONE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 1;
        }else if (IFTAR_MINUS_TWO_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 2;
        }else if (IFTAR_MINUS_THREE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 3;
        }else if (IFTAR_MINUS_FOUR_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 4;
        }else if (IFTAR_MINUS_FIVE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 5;
        }else if (IFTAR_MINUS_EIGHT_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 8;
        }else if (IFTAR_MINUS_NINE_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 9;
        }else if (IFTAR_MINUS_TEN_M.contains(selected_district)){
            IFTARMinute = IFTARMinute - 10;
        }

        return  IFTARMinute;
    }

}
