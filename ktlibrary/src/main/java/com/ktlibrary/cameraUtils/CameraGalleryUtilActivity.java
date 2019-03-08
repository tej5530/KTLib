package com.ktlibrary.cameraUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.ktlibrary.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
        put this lines in your manifest file for getting image data from storage
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.provider_paths"
            android:exported="false"
            android:grantUriPermissions="true">
                <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
        </provider>*/

public class CameraGalleryUtilActivity extends AppCompatActivity {
    final private int GALLERY_CODE =22 ;
    public int REQ_CAMERA_CODE = 100;
    public int REQ_GALLERY_CODE = 101;
    public Context context = this;
    private Uri photoURI,uri;
    private ImageView ivImage;
    final private int CAMERA_STORAGE_PERMISSION_CODE = 23;

    public void openBottomSheet(int layout, BottomSheetLayout container, ImageView imageView){
        this.ivImage = imageView;
        final View layoutView = LayoutInflater.from(context).inflate(layout, container, false);
        TextView txtCamera = layoutView.findViewById(R.id.txtCamera);
        TextView txtGallery = layoutView.findViewById(R.id.txtGallery);
        LinearLayout llGallery = layoutView.findViewById(R.id.llGallery);
        LinearLayout llCamera = layoutView.findViewById(R.id.llCamera);

        llCamera.setOnClickListener(v -> {
            if (container.isSheetShowing()){
                container.dismissSheet();
                int cameraResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                int storageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (cameraResult == PackageManager.PERMISSION_GRANTED && storageResult == PackageManager.PERMISSION_GRANTED){
                    openCamera(ivImage);
                }else {
                    requestPermission();
                }

            }
        });

        llGallery.setOnClickListener(v -> {
            if (container.isSheetShowing()){
                container.dismissSheet();
                int galleryResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (galleryResult == PackageManager.PERMISSION_GRANTED){
                    openGallery(ivImage);
                }else {
                    requestGalleryPermission();
                }
            }
        });
        container.showWithSheetView(layoutView);
    }

    private void requestGalleryPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_CODE);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){}

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, CAMERA_STORAGE_PERMISSION_CODE);
    }

    private void openGallery(ImageView imageView) {
        this.ivImage = imageView;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQ_GALLERY_CODE);
    }

    private void openCamera(ImageView imageView) {
        this.ivImage = imageView;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(context,
                        getPackageName()+".provider_paths",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQ_CAMERA_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ){
            if (requestCode == REQ_CAMERA_CODE && photoURI != null){
                CropImage.activity(photoURI).start(((Activity) context));
            }
            else if (requestCode == REQ_GALLERY_CODE){
                if (data != null){
                    CropImage.activity(data.getData()).start(((Activity) context));
                }
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                if (data != null){
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        uri = result.getUri();
                        setImage(uri);
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Log.e( "cropError", "onActivityResult Cropping error");
                    }
                }
            }
        }
    }

    private void setImage(Uri uri) {
        ivImage.setImageURI(uri);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_STORAGE_PERMISSION_CODE:
                    if ((grantResults.length>0) &&
                            (grantResults[0]+grantResults[1])== PackageManager.PERMISSION_GRANTED){
                        openCamera(ivImage);
                    }else {
                        boolean showRationale = shouldShowRequestPermissionRationale( permissions[0] + permissions[1] );
                        if (showRationale){
                            Toast.makeText(context, "Permission Required", Toast.LENGTH_SHORT).show();
                        }else {
                            openDialog();
//                            redirectToSetting();
                        }
                    }
                break;
            case GALLERY_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGallery(ivImage);
                }else {
                    boolean showRationale = shouldShowRequestPermissionRationale( permissions[0] );
                    if (showRationale){
                        Toast.makeText(context, "Permission Required", Toast.LENGTH_SHORT).show();
                    }else {
                        openDialog();
//                        redirectToSetting();
                    }

                }
                break;
        }
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Give permission from settings");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Goto Setting",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        redirectToSetting();
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void redirectToSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
