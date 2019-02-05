package com.ktlib;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ktlibrary.paymentIntegration.StripeInstruction;
import com.ktlibrary.snackbarHelper.SnackBarHandler;
import com.ktlibrary.utils.AppConfig;
import com.ktlibrary.utils.RuntimePermissionsActivity;
import com.ktlibrary.validation.Validation;
import com.ktlibrary.webApi.ApiManager;
import com.ktlibrary.webApi.RetrofitClient;

public class MainActivity extends RuntimePermissionsActivity {

    private static final int REQUEST_PERMISSIONS = 0;
    private EditText etTesting;
    private Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity .super.requestAppPermissions(new
                        String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA,}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);
        etTesting = findViewById(R.id.etTesting);
        btnClick = findViewById(R.id.btnClick);
        btnClick.setOnClickListener(view -> Validation.isValid().validate(MainActivity.this,etTesting,"Error"));
        SnackBarHandler.ShowSnackbar(this).show("Done", SnackBarHandler.SnackbarPosition.snackbarBottom);
        RetrofitClient.baseURL = "https://api.androidhive.info/";
        new ApiManager().ApiCalling(this, "contacts/", AppConfig.requestType.GET, null, null, new ApiManager.ApiSuccessInterface() {
            @Override
            public void onSuccess(String response) {
                Log.e("response", response );
                Log.d("jsonRes", "onSuccess: " +new Gson().toJson(response));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}
