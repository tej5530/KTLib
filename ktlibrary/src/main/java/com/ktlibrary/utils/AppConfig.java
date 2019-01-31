package com.ktlibrary.utils;

import android.Manifest;

/**
 * Created by KT on 29/01/19.
 */

public class AppConfig {

    public interface requestType {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
    }

    public interface SocialLoginCode {
        int GOOGLE_SIGN_IN = 1;
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
}
