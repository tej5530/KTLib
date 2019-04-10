package com.ktlibrary.apiCall;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import com.google.gson.Gson;
import com.ktlibrary.R;
import com.ktlibrary.dialogs.CustomDialog;
import com.ktlibrary.localDb.DatabaseHelper;
import com.ktlibrary.localDb.MySQLiteHelper;
import com.ktlibrary.log.PrintLog;
import com.ktlibrary.snackbar.MySanckbar;
import com.ktlibrary.utils.CommonConfig;
import com.ktlibrary.utils.Utility;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

import static com.ktlibrary.utils.Utility.isConnectingToInternet;


public class CustomApiCall {


    private Call<ResponseBody> apicall;
    private String TAG = "ApiCall", url, methodType, dbTblName;
    private Retrofit retrofit;
    private Context context;
    private boolean isLoadingNeeded, isLocalDBNeeded, isMessageNeeded, isNODataFoundNeeded;
    private ApiCallback apiCallback;
    private Object requestBody;
    private Map<Uri, String> lstFileUri;
    private Map<String, String> getAllHeader;
    private List<MultipartBody.Part> files;
    private RequestBody fileWithJsonBody;
    private WeakReference<Activity> weakActivity;
    private Dialog dialog;
    private CustomDialog customDialog;

    //constructor for GET / POST / PUT / DELETE METHOD
    public CustomApiCall(Context context, Object requestBody, String[] getReqParams, Map<String, String> allHeader, String serviceCallFrom, ApiCallback apiCallback) {
        this.context = context;
        this.requestBody = requestBody;
        this.url = getReqParams[0];
        this.dbTblName = getReqParams[1];
        this.methodType = getReqParams[2];
        this.getAllHeader = allHeader;
        manageBoolFlags(serviceCallFrom);
        this.apiCallback = apiCallback;
        makeServiceCall();
    }

    //constructor for FILE METHOD
    public CustomApiCall(Context context, Object requestBody, String[] getReqParams, Map<String, String> allHeader, Map<Uri, String> FileUris, String serviceCallFrom, ApiCallback apiCallback) {
        this.context = context;
        this.requestBody = requestBody;
        this.url = getReqParams[0];
        this.dbTblName = getReqParams[1];
        this.methodType = getReqParams[2];
        this.getAllHeader = allHeader;
        manageBoolFlags(serviceCallFrom);
        this.apiCallback = apiCallback;
        this.lstFileUri = FileUris;

        makeWsCallWithFileJson();

    }


    private void makeWsCallWithFileJson() {
        files = new ArrayList<>();


        int i = 0;
        for (Uri uri : lstFileUri.keySet()) {

            File file = new File(uri.getPath());
            RequestBody fileBody = RequestBody.create(MediaType.parse(lstFileUri.get(uri)), file);
            //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
            files.add(MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "photo%d", i), file.getName(), fileBody));
            i++;
        }

        fileWithJsonBody = RequestBody.create(
                MultipartBody.FORM, new Gson().toJson(requestBody));
        makeServiceCall();
    }

    private void manageBoolFlags(String serviceCallFrom) {

        if (serviceCallFrom.equalsIgnoreCase(CommonConfig.serviceCallFrom.LIST_NORMAL_RC_WS_CALL)) {
            assignBoolFlags(true, true, false, true);
        } else if (serviceCallFrom.equalsIgnoreCase(CommonConfig.serviceCallFrom.LIST_LOAD_MORE_RC_WS_CALL) ||
                serviceCallFrom.equalsIgnoreCase(CommonConfig.serviceCallFrom.PULL_TO_REFRESH_WS_CALL)) {
            assignBoolFlags(false, true, false, true);
        } else if (serviceCallFrom.equalsIgnoreCase(CommonConfig.serviceCallFrom.SEARCH_WS_CALL)) {
            assignBoolFlags(true, false, false, true);
        } else if (serviceCallFrom.equalsIgnoreCase(CommonConfig.serviceCallFrom.GENERAL_WS_CALL_WITH_MSG)) {
            assignBoolFlags(true, false, true, false);
        } else if (serviceCallFrom.equalsIgnoreCase(CommonConfig.serviceCallFrom.GENERAL_WS_CALL)) {
            assignBoolFlags(true, false, false, false);
        } else if (serviceCallFrom.equalsIgnoreCase(CommonConfig.serviceCallFrom.BACKGROUND_WS_CALL)) {
            assignBoolFlags(false, true, false, false);
        }

    }

    private void assignBoolFlags(boolean isLoading, boolean isLocalDBNeeded, boolean isMessageNeeded, boolean isNODataFoundNeeded) {
        this.isLoadingNeeded = isLoading;
        this.isLocalDBNeeded = isLocalDBNeeded;
        this.isMessageNeeded = isMessageNeeded;
        this.isNODataFoundNeeded = isNODataFoundNeeded;
    }

    private void makeServiceCall() {
        if (isLocalDBNeeded && !dbTblName.isEmpty()) {
            String whereCondition = MySQLiteHelper.COL_TABLE_NAME + " = ? ";
            ArrayList args = new ArrayList();
            args.add(dbTblName);

            DatabaseHelper dbHelper = new DatabaseHelper(context);
            if ((!dbTblName.isEmpty()) && dbHelper.rowExists(MySQLiteHelper.TBL_NAME, whereCondition, args)) {
                ArrayList column = new ArrayList();
                column.add(MySQLiteHelper.COL_DATA);

                Cursor cursorData = dbHelper.getWhere(MySQLiteHelper.TBL_NAME, column, whereCondition, args, null);
                apiCallback.success(cursorData.getString(cursorData.getColumnIndex(MySQLiteHelper.COL_DATA)));
                cursorData.close();

            } else {
                makeApiCall();
            }
        } else {
            makeApiCall();
        }

    }

    private void makeApiCall() {

        if (isConnectingToInternet(context)) {

            customDialog = new CustomDialog();
            if (isLoadingNeeded)
                customDialog.displayProgress(context);

            if (methodType.equalsIgnoreCase(CommonConfig.WsMethodType.GET)) {
                apicall = getClient(CommonConfig.WsPrefix).create(ApiInterface.class).makeGetRequest(url + CommonConfig.getQueryString(new Gson().toJson(requestBody)), getAllHeader);
            } else if (methodType.equalsIgnoreCase(CommonConfig.WsMethodType.POST)) {
                apicall = getClient(CommonConfig.WsPrefix).create(ApiInterface.class).makePostRequest(url, RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(requestBody)), getAllHeader);
            } else if (methodType.equalsIgnoreCase(CommonConfig.WsMethodType.PUT)) {
                apicall = getClient(CommonConfig.WsPrefix).create(ApiInterface.class).makePutRequest(url, RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(requestBody)), getAllHeader);
            } else if (methodType.equalsIgnoreCase(CommonConfig.WsMethodType.DELETE)) {
                apicall = getClient(CommonConfig.WsPrefix).create(ApiInterface.class).makeDeleteRequest(url, RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(requestBody)), getAllHeader);
            } else if (methodType.equalsIgnoreCase(CommonConfig.WsMethodType.POST_WITH_FILE_TYPE)) {
                apicall = getClient(CommonConfig.WsPrefix).create(ApiInterface.class).makePostRequestWithFile(url, fileWithJsonBody, files);
          }


            apicall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    if (isLoadingNeeded)
                                        customDialog.dismissProgress(context);

                                    String responseBody = null;

                                    try {
                                        if (response.code() == CommonConfig.StandardStatusCodes.SUCCESS) {
                                            responseBody = new String(response.body().bytes());
                                            handleSuccessBlock(responseBody, response.code());
                                        } else {
                                            responseBody = response.errorBody().string();
                                            handleSuccessBlock(responseBody, response.code());
                                        }

                                        printServiceData(responseBody,"");


                                    } catch (Exception e) {
                                        printServiceData(null,e.getMessage());
                                        MySanckbar.showSnackBar(context, context.getString(R.string.msg_unexpected_error), CommonConfig.snackBarType.FAILURE_TOP);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    if (isLoadingNeeded)
                                        customDialog.dismissProgress(context);

                                    if (t.getClass() == SocketTimeoutException.class) {
                                        showDialog(false);
                                    } else {
                                        if (Utility.getDebugFlag(context)) {
                                            MySanckbar.showSnackBar(context, t.getMessage().isEmpty() ? context.getString(R.string.msg_unexpected_error) : t.getMessage(), CommonConfig.snackBarType.FAILURE_TOP);
                                        } else {
                                            MySanckbar.showSnackBar(context, context.getString(R.string.msg_unexpected_error), CommonConfig.snackBarType.FAILURE_TOP);
                                        }
                                    }
                                    printServiceData(t.getMessage(),"");

                                }
                            }
            );
        } else {
            showDialog(true);
        }
    }


    private void handleSuccessBlock(String response, int code) {

        switch (code) {
            case CommonConfig.StandardStatusCodes.SUCCESS:
                if (isLocalDBNeeded && !(dbTblName.isEmpty())) {
                    DatabaseHelper dbHelper = new DatabaseHelper(context);

                    ArrayList<String> column = new ArrayList<>();
                    column.add(MySQLiteHelper.COL_TABLE_NAME);
                    column.add(MySQLiteHelper.COL_DATA);
                    column.add(MySQLiteHelper.COL_DATE);

                    ArrayList columnValue = new ArrayList();
                    columnValue.add(dbTblName);
                    columnValue.add(response);

                    String whereCondition = MySQLiteHelper.COL_TABLE_NAME + " = ? ";

                    ArrayList args = new ArrayList();
                    args.add(dbTblName);

                    dbHelper.delete(MySQLiteHelper.TBL_NAME, whereCondition, args);
                    dbHelper.insert(MySQLiteHelper.TBL_NAME, column, columnValue);
                }

                if (isMessageNeeded && !response.isEmpty()) {
                    MySanckbar.showSnackBar(context, response, CommonConfig.snackBarType.SUCCESS_TOP);
                }
                apiCallback.success(response);
                break;
            case CommonConfig.StandardStatusCodes.NO_DATA_FOUND:
                if (isNODataFoundNeeded) {
                    CommonConfig.setNoDataFound(context);
                }
                break;

            case CommonConfig.StandardStatusCodes.NOTACCEPTABLE:
                apiCallback.success(response);
            case CommonConfig.StandardStatusCodes.DUPLICATE_ERROR:
            case CommonConfig.StandardStatusCodes.CONFLICT:
                MySanckbar.showSnackBar(context, response.isEmpty() ? context.getString(R.string.msg_unexpected_error) : response, CommonConfig.snackBarType.FAILURE_TOP);
                break;
            case CommonConfig.StandardStatusCodes.UNAUTHORISE:
            case CommonConfig.StandardStatusCodes.TIMEOUT:
            case CommonConfig.StandardStatusCodes.BAD_REQUEST:
            case CommonConfig.StandardStatusCodes.METHODNOTFOUND:
                MySanckbar.showSnackBar(context, response.isEmpty() ? context.getString(R.string.msg_unexpected_error) : response, CommonConfig.snackBarType.FAILURE_TOP);
                break;
        }


    }


    private Retrofit getClient(String WsPrefix) {
        //TODO 60 to 30 second at everywhere
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(WsPrefix)
                .client(okHttpClient)
                .build();
        return retrofit;
    }


    interface ApiInterface {

        @GET
        Call<ResponseBody> makeGetRequest(@Url String url, @HeaderMap() Map<String, String> header);

        @POST
        Call<ResponseBody> makePostRequest(@Url String url, @Body RequestBody requestBody, @HeaderMap() Map<String, String> header);

        @PUT
        Call<ResponseBody> makePutRequest(@Url String url, @Body RequestBody requestBody, @HeaderMap() Map<String, String> header);

        @DELETE
        Call<ResponseBody> makeDeleteRequest(@Url String url, @Body RequestBody requestBody, @HeaderMap() Map<String, String> header);

        @Multipart
        @POST
        Call<ResponseBody> makePostRequestWithFile(@Url String url, @Part("fileParams") RequestBody requestBody, @Part List<MultipartBody.Part> files);

    }


    private void printServiceData(final Object responseBody,String error) {
        if (Utility.getDebugFlag(context)) {
            try {
                if (methodType.equalsIgnoreCase(CommonConfig.WsMethodType.GET)) {
                    PrintLog.Log(context, TAG, "makeApiCall", "WS call req Url :=> " + CommonConfig.WsPrefix + url + CommonConfig.getQueryString(new Gson().toJson(requestBody)), Log.ERROR);
                } else {
                    PrintLog.Log(context, TAG, "makeApiCall", "WS call req Url :=> " + CommonConfig.WsPrefix + url, Log.ERROR);
                }
                PrintLog.Log(context, TAG, "makeApiCall", "WS call req Method :=> " + methodType, Log.ERROR);
                String strHeaderKeyValue = "";
                for (Map.Entry<String, String> headers : getAllHeader.entrySet()) {
                    String key = headers.getKey();
                    String value = headers.getValue();
                    strHeaderKeyValue = strHeaderKeyValue + (key + ":" + value) + "|";
                }
                PrintLog.Log(context, TAG, "makeApiCall", "WS call Header:=> " + strHeaderKeyValue, Log.ERROR);
                PrintLog.Log(context, TAG, "makeApiCall", "WS call req Param :=> " + new Gson().toJson(requestBody), Log.ERROR);
                if (responseBody != null)
                    PrintLog.Log(context, TAG, "makeApiCall", "WS call res Param :=> " + new Gson().toJson(responseBody), Log.ERROR);
                else
                    PrintLog.Log(context, TAG, "makeApiCall", "WS call res Param :=> " + error, Log.ERROR);

                String debugUrl = "";
                if (methodType.equalsIgnoreCase(CommonConfig.WsMethodType.GET)) {
                    debugUrl = "http://192.168.11.38/servicemanagement/api_debug?URL=" + CommonConfig.WsPrefix + url + CommonConfig.getQueryString(new Gson().toJson(requestBody)) + "&METHOD=" + methodType + "&HEADER=" + strHeaderKeyValue + "&REQ=" + new Gson().toJson(requestBody);

                } else {
                    debugUrl = "http://192.168.11.38/servicemanagement/api_debug?URL=" + CommonConfig.WsPrefix + url + "&METHOD=" + methodType + "&HEADER=" + strHeaderKeyValue + "&REQ=" + new Gson().toJson(requestBody);

                }

//                makeTinyUrl(debugUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private void makeTinyUrl(String URL) {
//        if (isConnectingToInternet(context)) {
//
//            new HttpRequestTask(
//                    new HttpRequest(context.getString(R.string.tiny_URL) + URL),
//                    new HttpRequest.Handler() {
//                        @Override
//                        public void response(HttpResponse response) {
//                            if (response.code == 200) {
//
//                                PrintLog.Log(context, TAG, "makeApiCall", "WS call URL::::" + "http://192.168.11.38/servicemanagement/api_debug?URL="+response.body, Log.ERROR);
//
//                            } else {
//                                MySanckbar.showSnackBar(context, context.getString(R.string.msg_unexpected_error), CommonConfig.snackBarType.FAILURE_TOP);
//                            }
//                        }
//                    }).execute();
//        }
//    }

    public void showDialog(boolean isForNoInternet) {
        weakActivity = new WeakReference<Activity>((Activity) context);

        if (weakActivity.get() != null && !weakActivity.get().isFinishing()) {
            if (dialog == null) {
                dialog = new Dialog(context, R.style.AppThemeLib);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.internet_connection);
                RelativeLayout rlMain = (RelativeLayout) dialog.findViewById(R.id.rlMain);


                ImageView imgNoInternet = (ImageView) dialog.findViewById(R.id.imgNoInternet);
                TextView txtNoNetworkHeader = (TextView) dialog.findViewById(R.id.txtNoNetworkHeader);
                TextView txtNoNetworkMessage = (TextView) dialog.findViewById(R.id.txtNoNetworkMessage);

                if (isForNoInternet) {

                    if (CommonConfig.NoInternetImg != null)
                        imgNoInternet.setImageDrawable(CommonConfig.NoInternetImg);
                    else
//                        imgNoInternet.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_internet));
                        imgNoInternet.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
                    if (CommonConfig.NoInternetTitle != null && !CommonConfig.NoInternetTitle.isEmpty())
                        txtNoNetworkHeader.setText(CommonConfig.NoInternetTitle);
                    else
                        txtNoNetworkHeader.setText(context.getString(R.string.txt_no_network_header));
                    if (CommonConfig.NoInternetMessage != null && !CommonConfig.NoInternetMessage.isEmpty())
                        txtNoNetworkMessage.setText(CommonConfig.NoInternetMessage);
                    else
                        txtNoNetworkMessage.setText(context.getString(R.string.txt_no_network_message));
                } else {
                    if (CommonConfig.timeoutImg != null)
                        imgNoInternet.setImageDrawable(CommonConfig.timeoutImg);
                    else
//                        imgNoInternet.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_internet));
                        imgNoInternet.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
                    if (CommonConfig.timeoutTitle != null && !CommonConfig.timeoutTitle.isEmpty())
                        txtNoNetworkHeader.setText(CommonConfig.timeoutTitle);
                    else
                        txtNoNetworkHeader.setText(context.getString(R.string.txt_connection_time_out));
                    if (CommonConfig.timeoutMessage != null && !CommonConfig.timeoutMessage.isEmpty())
                        txtNoNetworkMessage.setText(CommonConfig.timeoutMessage);
                    else
                        txtNoNetworkMessage.setText(context.getString(R.string.txt_connection_time_out_message));
                }
                rlMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isConnectingToInternet(context)) {
                            dialog.dismiss();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    makeApiCall();
                                }
                            }, 1000);
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
            if (!((Activity) context).isFinishing()) {
                dialog.show();
            }
        }
    }


}
