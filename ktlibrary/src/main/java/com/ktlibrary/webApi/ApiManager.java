package com.ktlibrary.webApi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.ktlibrary.R;
import com.ktlibrary.dialogs.CustomDialog;
import com.ktlibrary.utils.AppConfig;
import com.ktlibrary.utils.InternetChecking;

import java.io.IOException;
import java.util.HashMap;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by kd on 21/3/18.
 */

public class ApiManager {

    ApiSuccessInterface apiSuccessInterface = new ApiSuccessInterface() {
        @Override
        public void onSuccess(String response) {
            apiSuccessInterface.onSuccess(response);
        }

        @Override
        public void onFailure(Throwable t) {
            apiSuccessInterface.onFailure(t);
        }
    };

    public Context context;
    public Dialog dialog;
    public String url;
    public View view;
    public int method;

    public static Api getApiInstance() {
        return RetrofitClient.getClient().create(Api.class);
    }

    public void ApiCalling(final Context context, final String url, final int method, final View view, final HashMap map, final ApiSuccessInterface apiSuccessInterface) {
        init(context, url, method, view);
        if (InternetChecking.isNetworkAvailable(context)) {
            dialog = ProgressDialog.show(context, "", "Loading...");
            dialog.show();

            Call<Object> call = null;
            if (method == AppConfig.requestType.GET) {
                call = getApiInstance().getRequest();
            } else if (method == AppConfig.requestType.POST) {
//                call = getApiInstance().postRequest(url);
            }

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                    if (dialog.isShowing()) dialog.dismiss();
                    apiSuccessInterface.onSuccess(response.toString());
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    if (dialog.isShowing()) dialog.dismiss();
                    apiSuccessInterface.onFailure(t);
                    Log.d("response", "onFailure:~ failed :~ " + t.getLocalizedMessage());
                }
            });
        } else {
            if (dialog == null) {
                dialog = new Dialog(context, R.style.dialog_full_screen);
                CustomDialog.noInternetDialog(context, dialog, R.layout.dialog_no_internet, R.id.llNoInternet ,false, CustomDialog.NoInterNetDialogInterface.match_parent, new CustomDialog.NoInterNetDialogInterface() {
                    @Override
                    public void onOkClicked(Dialog dialog, View view) {
                        if (InternetChecking.isNetworkAvailable(context))
                            if (dialog.isShowing()) dialog.dismiss();
                        ApiCalling(context, url, method, view, map, apiSuccessInterface);
                    }

                });
            }
        }
    }

    public void ApiCalling1(final Context context, final String url, Object requessstBody, final View view, final HashMap map, final ApiSuccessInterface apiSuccessInterface) {
        init(context, url, method, view);
        if (InternetChecking.isNetworkAvailable(context)) {
            dialog = ProgressDialog.show(context, "", "Loading...");
            dialog.show();

            Call call = null;
            if (method == AppConfig.requestType.GET) {
                call = getApiInstance().getRequest(url);
            } else if (method == AppConfig.requestType.POST) {
//                call = getApiInstance().postRequest(url);
            }

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (dialog.isShowing()) dialog.dismiss();
                        String res = new String(response.body().bytes());
                        apiSuccessInterface.onSuccess(res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (dialog.isShowing()) dialog.dismiss();
                    apiSuccessInterface.onFailure(t);
                    Log.d("response", "onFailure:~ failed :~ " + t.getLocalizedMessage());
                }
            });
        } else {
            if (dialog == null) {
                dialog = new Dialog(context, R.style.dialog_full_screen);
                CustomDialog.noInternetDialog(context, dialog, R.layout.dialog_no_internet, R.id.llNoInternet ,false, CustomDialog.CustomDialogInterface.match_parent, new CustomDialog.NoInterNetDialogInterface() {
                    @Override
                    public void onOkClicked(Dialog dialog, View view) {
                        if (InternetChecking.isNetworkAvailable(context))
                            if (dialog.isShowing()) dialog.dismiss();
                        ApiCalling1(context, url, method, view, map, apiSuccessInterface);
                    }
                });
            }
        }
    }

    private void init(Context context, String url, int method, View view) {
        this.context = context;
        this.url = url;
        this.method = method;
        this.view = view;
    }

    public interface Api {

        @GET
        Call<ResponseBody> getRequest(@Url String url);

        @GET
        Call<ResponseBody> getRequest(@Url String url, HashMap<String, String> map);

        @GET("contacts")
        Call<Object> getRequest();

        @POST
        Call<ResponseBody> postRequest(@Url String url);
    }

    public interface ApiSuccessInterface {

        void onSuccess(String response);
        void onFailure(Throwable t);
    }
}
