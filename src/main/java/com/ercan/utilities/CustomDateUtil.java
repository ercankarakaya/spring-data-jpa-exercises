package com.ercan.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomDateUtil {

    public static String calendarToString(Calendar calendar){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(calendar.getTime());
    }

}
