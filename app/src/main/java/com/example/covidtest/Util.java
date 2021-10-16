package com.example.covidtest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public int stringToInt(String string){
        int num = 0;
        try {
            num = Integer.parseInt(string);
            return num;
        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        return num;
    }
    public Double stringToDouble(String string){
        double num = 0;
        try {
            num = Double.parseDouble(string);
            return num;
        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        return num;
    }
    public Date stringToDate(String string)  {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date = format.parse(string);
            return date;
        }catch (ParseException E){
            E.printStackTrace();
        }
        return date;
    }

    public String dateToString (Date date){
        String stringDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        stringDate  = format.format(date);
        return stringDate;
    }
}
