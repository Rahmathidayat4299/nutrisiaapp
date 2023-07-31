package com.example.kalori;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String theDate = dateFormat.format(date);
        return theDate;
    }

    public static String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String theTime = dateFormat.format(date);
        return theTime;
    }
}
