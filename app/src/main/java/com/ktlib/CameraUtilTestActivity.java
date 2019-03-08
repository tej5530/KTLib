package com.ktlib;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ktlibrary.cameraUtils.CameraGalleryUtilActivity;

public class CameraUtilTestActivity extends CameraGalleryUtilActivity {

    private ImageView ivImage;
    private Button btnPick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_util_test);
        findComponent();
        setListner();
    }

    private void setListner() {
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findComponent() {
        ivImage = findViewById(R.id.ivImage);
        btnPick = findViewById(R.id.btnPick);
//        bottomSheetContainer = findViewById(R.id.bottomSheetContainer);
    }
}
