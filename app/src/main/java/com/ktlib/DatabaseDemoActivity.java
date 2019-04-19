package com.ktlib;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ktlib.model.RandomUserResponse;
import com.ktlib.model.Req;
import com.ktlibrary.apiCall.ApiCallback;
import com.ktlibrary.apiCall.CustomApiCall;
import com.ktlibrary.localDb.DatabaseHelper;
import com.ktlibrary.localDb.MySQLiteHelper;
import com.ktlibrary.utils.CommonConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseDemoActivity extends AppCompatActivity {
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    ArrayList<String> colmName = new ArrayList<>();
    ArrayList<String> colmType = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_demo);
////        colmName.add("id");
//        colmName.add("FName");
//        colmName.add("LName");
//
////        colmType.add("INTEGER PRIMARY KEY");
//        colmType.add("TEXT");
//        colmType.add("TEXT");
//
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        databaseHelper.creatTbl("Test", colmName, colmType);
//        ArrayList<String> listData = new ArrayList<>();
//        listData.add("Demo");
//        listData.add("Demo1");
//        databaseHelper.insert("Test", colmName, listData);
//        Cursor cursor = databaseHelper.getWhere("Test", colmName, null, null,null );
//        if (cursor != null){
//            if (cursor.moveToFirst()){
//                do{
//                    Log.e( "print_data",cursor.getString(cursor.getColumnIndex("FName")) );
//                    Log.e( "print_data",cursor.getString(cursor.getColumnIndex("LName")) );
//                }while (cursor.moveToNext());
//            }
//        }

        /* Todo Custom api call usage */
        CommonConfig.WsPrefix ="https://api.randomuser.me";
        String urlArray [] = {"","",CommonConfig.WsMethodType.GET};
        Req req = new Req();
        new CustomApiCall(this, req, urlArray, getAllFileHeader(), CommonConfig.serviceCallFrom.GENERAL_WS_CALL, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                RandomUserResponse userResponse = gson.fromJson(responseData,RandomUserResponse.class );
                Toast.makeText(DatabaseDemoActivity.this, ""+userResponse.getResults().get(0).getEmail(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(String responseData) {

            }
        });

    }

    public static Map<String, String> getAllFileHeader() {
        //Header
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json");
//        mapHeader.put("Appsecret", "PYKuBOdOsne9oJyhKmLt6HDt8Mwt62I5CSS");
        return mapHeader;
    }
}
