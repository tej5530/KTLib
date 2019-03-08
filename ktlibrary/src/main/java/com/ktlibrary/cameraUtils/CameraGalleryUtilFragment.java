package com.ktlibrary.cameraUtils;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.ktlibrary.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

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

public class CameraGalleryUtilFragment extends Fragment {
    private final int GALLERY_CODE = 22;
    private final int CAMERA_STORAGE_PERMISSION_CODE = 23;
    public int REQ_CAMERA_CODE = 100;
    public int REQ_GALLERY_CODE = 101;
    private Uri photoURI, uri;
    private ImageView ivImage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void openBottomSheet(int layout, BottomSheetLayout container, ImageView imageView) {
        ivImage = imageView;
        final View layoutView = LayoutInflater.from(getActivity()).inflate(layout, container, false);
        LinearLayout llGallery = layoutView.findViewById(R.id.llGallery);
        LinearLayout llCamera = layoutView.findViewById(R.id.llCamera);

        llCamera.setOnClickListener(v -> {
            if (container.isSheetShowing()) {
                container.dismissSheet();
                int cameraResult = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
                int storageResult = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (cameraResult == PackageManager.PERMISSION_GRANTED && storageResult == PackageManager.PERMISSION_GRANTED) {
                    openCamera(ivImage);
                } else {
                    requestPermission();
                }

            }
        });

        llGallery.setOnClickListener(v -> {
            if (container.isSheetShowing()) {
                container.dismissSheet();
                int galleryResult = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (galleryResult == PackageManager.PERMISSION_GRANTED){
                    openGallery(ivImage);
                }else {
                    requestGalleryPermission();
                }
            }
        });
        container.showWithSheetView(layoutView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestGalleryPermission() {
        if (getActivity().shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to context block
            //Here you can explain why you need context permission
            //Explain here why you need context permission
        }
        getActivity().requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        if (getActivity().shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to context block
            //Here you can explain why you need context permission
            //Explain here why you need context permission
        }
        if (getActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
        }

        //And finally ask for the permission
        getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CAMERA_STORAGE_PERMISSION_CODE);
    }

    private void openGallery(ImageView imageView) {
        ivImage = imageView;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQ_GALLERY_CODE);
    }

    private void openCamera(ImageView imageView) {
        ivImage = imageView;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.getLocalizedMessage();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        getActivity().getPackageName() + ".provider_paths",
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CAMERA_CODE && photoURI != null) {
                CropImage.activity(photoURI).start(getContext(), this);
            } else if (requestCode == REQ_GALLERY_CODE) {
                if (data != null) {
                    CropImage.activity(data.getData()).start(getContext(), this);
                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (data != null) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    uri = result.getUri();
                    setImage(uri);
                } else {
                    Toast.makeText(getContext(), "Data Null", Toast.LENGTH_SHORT).show();
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_STORAGE_PERMISSION_CODE:
                if ((grantResults.length > 0) &&
                        (grantResults[0] + grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    openCamera(ivImage);
                } else {
                    boolean showRationale = shouldShowRequestPermissionRationale(permissions[0] + permissions[1]);
                    if (showRationale) {
                        Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_SHORT).show();
                    } else {
                        openDialog();
//                            redirectToSetting();
                    }
                }
                break;
            case GALLERY_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(ivImage);
                } else {
                    boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                    if (showRationale) {
                        Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_SHORT).show();
                    } else {
                        openDialog();
//                        redirectToSetting();
                    }

                }
                break;
        }
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), REQ_GALLERY_CODE);
    }
}
