package com.ktlibrary.log;

import android.content.Context;
import android.util.Log;

import com.ktlibrary.utils.Utility;


/*

PrintLog == denote logging
*/
public class PrintLog {

    public static void Log(Context context, String PageTag, String MethodName, String ExceptionMsg, int LogType) {

        if (Utility.getDebugFlag(context)) {

            if (LogType == Log.DEBUG) {
                Log.d(getApplicationName(context), "::::" + PageTag + "::::" + MethodName + ":::::" + ExceptionMsg);
            } else if (LogType == Log.ERROR) {
                Log.e(getApplicationName(context), "::::" + PageTag + "::::" + MethodName + ":::::" + ExceptionMsg);
            } else if (LogType == Log.VERBOSE) {
                Log.v(getApplicationName(context), "::::" + PageTag + "::::" + MethodName + ":::::" + ExceptionMsg);
            } else if (LogType == Log.INFO) {
                Log.i(getApplicationName(context), "::::" + PageTag + "::::" + MethodName + ":::::" + ExceptionMsg);
            } else if (LogType == Log.WARN) {
                Log.w(getApplicationName(context), "::::" + PageTag + "::::" + MethodName + ":::::" + ExceptionMsg);
            }
        }

    }



    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }

}
