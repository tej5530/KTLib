package com.ktlibrary.dateFormatter;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class DateFunction {

    private static String TAG = "dateFunction";

    public static String getFormattedDate(String inputDate, String inputPattern, String outputPattern) {
        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputPattern, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputPattern, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
//            if (IS_LOG) Log.d(TAG, "getFormattedDate: ");
        }

        return outputDate;
    }

    public static String getCurrentDate(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, java.util.Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

    public static String getDateFromMillis(String pattern, long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, java.util.Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
