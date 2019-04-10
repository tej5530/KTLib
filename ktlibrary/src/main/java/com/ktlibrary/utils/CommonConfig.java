package com.ktlibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ktlibrary.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class CommonConfig {


    public static final int GOOGLE_SIGN_IN = 200;
    public static int projectId;
    public static String WsPrefix, NoInternetTitle, NoInternetMessage, noDataTitle, noDataDesc, timeoutTitle, timeoutMessage;

    public static Drawable NoInternetImg, noDataFoundImg, timeoutImg;
    public static String tableName[] = {};

    public static String getQueryString(String unparsedString) {

        StringBuilder sb;
        sb = new StringBuilder();
        try {

            JSONObject json = null;

            json = new JSONObject(unparsedString);

            Iterator<String> keys = json.keys();
            sb.append("?"); //start of query args
            while (keys.hasNext()) {
                String key = keys.next();
                sb.append(key);
                sb.append("=");
                sb.append(json.get(key));
                sb.append("&"); //To allow for another argument.

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String newURL = sb.toString();
        return newURL.substring(0, newURL.length() - 1);
    }

    public static void setNoDataFound(String title, String desc, Drawable img) {
        noDataTitle = title;
        noDataDesc = desc;
        noDataFoundImg = img;
    }

    public static void setNoDataFound(Context context) {

//        if (context instanceof Activity) {

//            try {
//                View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
//                View view = LayoutInflater.from(context).inflate(R.layout.no_data_found, (ViewGroup) rootView.findViewWithTag("12345"));
//
//                ((TextView) view.findViewById(R.id.txt_no_data_title)).setText(CommonConfig.noDataTitle != null && !CommonConfig.noDataTitle.isEmpty() ? CommonConfig.noDataTitle : context.getString(R.string.msg_no_data_found));
//                ((TextView) view.findViewById(R.id.txt_no_data_desc)).setText(CommonConfig.noDataDesc != null && !CommonConfig.noDataDesc.isEmpty() ? CommonConfig.noDataDesc : context.getString(R.string.msg_no_data_description));
//                ((ImageView) view.findViewById(R.id.imgNoDataFound)).setImageDrawable(CommonConfig.noDataFoundImg != null ? CommonConfig.noDataFoundImg : ContextCompat.getDrawable(context, R.drawable.no_data_found));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
            try {
                View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
                View view = LayoutInflater.from(context).inflate(R.layout.no_data_found, (ViewGroup) rootView.findViewWithTag("12345"));
                ((TextView) view.findViewById(R.id.txt_no_data_title)).setText(CommonConfig.noDataTitle);
                ((TextView) view.findViewById(R.id.txt_no_data_desc)).setText(CommonConfig.noDataDesc);
                ((ImageView) view.findViewById(R.id.imgNoDataFound)).setImageDrawable(CommonConfig.noDataFoundImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }

    }

    public static void setNoInternet(String title, String msg, Drawable img) {
        NoInternetTitle = title;
        NoInternetMessage = msg;
        NoInternetImg = img;
    }

    public static void setTimeOut(String title, String msg, Drawable img) {
        NoInternetTitle = title;
        NoInternetMessage = msg;
        NoInternetImg = img;
    }

    public interface SnackBarDuration {
        int LENGTH_INDEFINITE = -2;
        int LENGTH_SHORT = -1;
        int LENGTH_LONG = 0;
    }

    public interface SnackBarBgColor {
        int GREEN = 0;
        int RED = 1;
    }

    public interface SnackBarTxtColor {
        int WHITE = 0;
        int BLACK = 1;

    }

    public interface SnackBarBtnColor {
        int WHITE = 0;
        int BLACK = 1;
        int NONE = 3;
    }

    public interface SnackBarPosition {
        int TOP = 0;
        int BOTTOM = 1;
    }

    public interface WsMethodType {
        String POST = "POST";
        String GET = "GET";
        String PUT = "PUT";
        String DELETE = "DELETE";
        String POST_WITH_FILE_TYPE = "POSTFileData";
        String POSTMapData = "POSTMapData";

    }

    public interface PermissionCode {
        int READ_EXTERNAL_STORAGE = 1;
        int WRITE_EXTERNAL_STORAGE = 2;
        int SEND_SMS = 3;
        int RECEIVE_SMS = 4;
        int READ_SMS = 5;
        int RECEIVE_WAP_PUSH = 6;
        int RECEIVE_MMS = 7;
        int BODY_SENSORS = 8;
        int READ_PHONE_STATE = 9;
        int CALL_PHONE = 10;
        int READ_CALL_LOG = 11;
        int WRITE_CALL_LOG = 12;
        int ADD_VOICEMAIL = 13;
        int USE_SIP = 14;
        int PROCESS_OUTGOING_CALLS = 15;
        int RECORD_AUDIO = 16;
        int ACCESS_FINE_LOCATION = 17;
        int ACCESS_COARSE_LOCATION = 18;
        int READ_CONTACTS = 19;
        int WRITE_CONTACTS = 20;
        int GET_ACCOUNTS = 21;
        int CAMERA = 22;
        int READ_CALENDAR = 23;
        int WRITE_CALENDAR = 24;
    }


    public interface PermissionType {
        String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
        String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String SEND_SMS = Manifest.permission.SEND_SMS;
        String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
        String READ_SMS = Manifest.permission.READ_SMS;
        String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
        String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
        String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
        String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
        String CALL_PHONE = Manifest.permission.CALL_PHONE;
        String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
        String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
        String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
        String USE_SIP = Manifest.permission.USE_SIP;
        String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
        String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
        String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
        String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
        String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
        String CAMERA = Manifest.permission.CAMERA;
        String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
        String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    }

    public interface ActionType {
        int ACTION_CALL = 1;
        int ACTION_EMAIL = 2;
        int ACTION_SMS = 3;
        int ACTION_WEB = 4;
    }


    public interface StandardStatusCodes {
        int SUCCESS = 200;
        int BAD_REQUEST = 400;
        int POLICY_NOT_FULL_FILLED = 420;
        int INTERNAL_SERVER_ERROR = 500;
        int NO_DATA_FOUND = 404;
        int CONFLICT = 409;
        int UNAUTHORISE = 401;
        int NOTACCEPTABLE = 406;
        int DUPLICATE_ERROR = 208;
        int METHODNOTFOUND = 405;
        int TIMEOUT = 408;
    }


    public interface serviceCallFrom {
        String LIST_NORMAL_RC_WS_CALL = "LIST_NORMAL_WS_CALL";
        String LIST_LOAD_MORE_RC_WS_CALL = "LIST_LOAD_MORE_WS_CALL";
        String SEARCH_WS_CALL = "SEARCH_NORMAL_WS_CALL";
        String BACKGROUND_WS_CALL = "BACKGROUND_WS_CALL";
        String PULL_TO_REFRESH_WS_CALL = "PULL_TO_REFRESH_WS_CALL";
        String GENERAL_WS_CALL = "GENERAL_WS_CALL";
        String GENERAL_WS_CALL_WITH_MSG = "GENERAL_WS_CALL_WITH_MSG";
    }

    public interface snackBarType {
        String SUCCESS_TOP = "SUCCESS_TOP";
        String SUCCESS_BOTTOM = "SUCCESS_BOTTOM";
        String FAILURE_TOP = "FAILURE_TOP";
        String FAILURE_BOTTOM = "FAILURE_BOTTOM";
    }

}
