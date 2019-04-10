package com.ktlibrary.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.ktlibrary.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class CustomDialog {


    public  android.app.ProgressDialog progressDialog;
    public WeakReference<Activity> weakActivity;
    public android.support.v7.app.AlertDialog oneButtonDialog, twoButtonDialog;
    private android.support.v7.app.AlertDialog alertListDialog;
    private Dialog dialog;


    public void displayProgress(Context context) {

        weakActivity = new WeakReference<Activity>((Activity) context);

        if (weakActivity.get() != null && !weakActivity.get().isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new android.app.ProgressDialog(context);

            } else {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
            progressDialog.setMessage(context.getString(R.string.msg_progress));
            if(!((Activity) context).isFinishing()) {
                progressDialog.show();
            }
            progressDialog.setCancelable(false);
        }
    }


    public void dismissProgress(Context context) {
        weakActivity = new WeakReference<Activity>((Activity) context);

        if (weakActivity.get() != null && !weakActivity.get().isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


    public  void showDialogOneButton(Context context, String title, String message,
                                     String btnLabel,
                                     DialogInterface.OnClickListener buttonClickListener) {

        weakActivity = new WeakReference<Activity>((Activity) context);

        if (weakActivity.get() != null && !weakActivity.get().isFinishing()) {
            if (oneButtonDialog != null)
                oneButtonDialog.dismiss();
            final android.support.v7.app.AlertDialog.Builder oneButtonDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
            if (!title.isEmpty())
                oneButtonDialogBuilder.setTitle(title);
            oneButtonDialogBuilder.setMessage(message);
            oneButtonDialogBuilder.setCancelable(false);


            if (buttonClickListener == null) {
                oneButtonDialogBuilder.setPositiveButton(btnLabel,
                        new DialogInterface.OnClickListener()

                        {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }

                );
            } else {
                oneButtonDialogBuilder.setPositiveButton(btnLabel, buttonClickListener);
            }

            oneButtonDialog = oneButtonDialogBuilder.create();

            oneButtonDialog.show();

        }
    }

    public  void showDialogTwoButton(Context context, String title, String message,
                                     String btnPositiveLabel, String btnNegativeLabel,
                                     DialogInterface.OnClickListener positiveButtonClickListener, DialogInterface.OnClickListener negativeButtonClickListener) {


        weakActivity = new WeakReference<Activity>((Activity) context);

        if (weakActivity.get() != null && !weakActivity.get().isFinishing()) {
            if (twoButtonDialog != null)
                twoButtonDialog.dismiss();

            android.support.v7.app.AlertDialog.Builder twoButtonDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
            twoButtonDialogBuilder.setTitle(title);

            twoButtonDialogBuilder.setMessage(message);
            twoButtonDialogBuilder.setCancelable(false);


            if (positiveButtonClickListener == null) {
                twoButtonDialogBuilder.setPositiveButton(btnPositiveLabel,
                        new DialogInterface.OnClickListener()

                        {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }

                );
            } else {
                twoButtonDialogBuilder.setPositiveButton(btnPositiveLabel, positiveButtonClickListener);
            }

            if (negativeButtonClickListener == null) {
                twoButtonDialogBuilder.setNegativeButton(btnNegativeLabel,
                        new DialogInterface.OnClickListener()

                        {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }

                );
            } else {
                twoButtonDialogBuilder.setNegativeButton(btnNegativeLabel, negativeButtonClickListener);
            }


            twoButtonDialog = twoButtonDialogBuilder.create();
            twoButtonDialog.show();
        }
    }


    public  void  showListDialog(Context context, final ArrayList<String> listItems, DialogInterface.OnClickListener _dialogClickListener) {

        weakActivity = new WeakReference<Activity>((Activity) context);

        if (weakActivity.get() != null && !weakActivity.get().isFinishing() && listItems.size() > 0) {

            final CharSequence[] tags = listItems.toArray(new String[listItems.size()]);
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);


            if (_dialogClickListener == null) {
                builder.setItems(tags, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
//                        _setItemView.setText(listItems.get(item));
                    }
                });


            } else {
                builder.setItems(tags, _dialogClickListener);
            }

            alertListDialog = builder.create();
            ListView listView = alertListDialog.getListView();
            if (listItems.size() > 1) {
                listView.setDivider(new ColorDrawable(ContextCompat.getColor(context, R.color.colorDivider))); // set color
                listView.setDividerHeight(1); // set height
            }
            alertListDialog.setCancelable(true);
            alertListDialog.show();
        }
    }


    public static void AlertDialogSingleClick(Context context, int icon, String title, String message, boolean cancelable, String btnText, int buttonColor, final SingleClickDialogInterface singleClickDialogInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(icon);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(btnText, (dialog, which) -> singleClickDialogInterface.onClicked());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(buttonColor));
    }

    public static void AlertDialogDoubleClick(Context context, int icon, String title, String message, boolean cancelable, String positiveText, String negativeText, int positiveTextColor, int negaticveTextColor, final DoubleClickDialogInterface doubleClickDialogInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(icon);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(positiveText, (dialog, which) -> doubleClickDialogInterface.setPositiveClicked());
        builder.setNegativeButton(negativeText, (dialog, which) -> doubleClickDialogInterface.setNegativeClicked());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(positiveTextColor));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(negaticveTextColor));
    }

    public static void customDialog(Context context, Dialog dialog, final int view,int positiveButtonId, int negativeButtonId, boolean cancelable, int height, final CustomDialogInterface customDialogInterface) {
        final View viewLayout = LayoutInflater.from(context).inflate(view, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(viewLayout);
        if (height == CustomDialogInterface.wrap_content) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (height == CustomDialogInterface.match_parent) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCancelable(cancelable);
        dialog.show();
        final Dialog finalDialog = dialog;
        dialog.findViewById(positiveButtonId).setOnClickListener(v -> customDialogInterface.onOkClicked(finalDialog, viewLayout));
        dialog.findViewById(negativeButtonId).setOnClickListener(v -> customDialogInterface.onCancelClicked(finalDialog));
    }

    public static void noInternetDialog(Context context, Dialog dialog, final int view,int positiveButtonId, boolean cancelable, int height, final NoInterNetDialogInterface noInterNetDialogInterface) {
        final View viewLayout = LayoutInflater.from(context).inflate(view, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(viewLayout);
        if (height == CustomDialogInterface.wrap_content) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (height == CustomDialogInterface.match_parent) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCancelable(cancelable);
        dialog.show();
        final Dialog finalDialog = dialog;
        dialog.findViewById(positiveButtonId).setOnClickListener(v -> noInterNetDialogInterface.onOkClicked(finalDialog, viewLayout));
//        dialog.findViewById(negativeButtonId).setOnClickListener(v -> customDialogInterface.onCancelClicked(finalDialog));
    }

    public static void customDialog(Context context, Dialog dialog, final int view, boolean cancelable, int height) {
        final View viewLayout = LayoutInflater.from(context).inflate(view, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(viewLayout);
        if (height == CustomDialogInterface.wrap_content) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (height == CustomDialogInterface.match_parent) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCancelable(cancelable);
        dialog.show();
    }

    public interface CustomDialogInterface {

        int wrap_content = 0;
        int match_parent = 1;

        void onOkClicked(Dialog dialog, View view);

        void onCancelClicked(Dialog dialog);
    }

    public interface NoInterNetDialogInterface {

        int wrap_content = 0;
        int match_parent = 1;

        void onOkClicked(Dialog dialog, View view);

    }

    public interface DoubleClickDialogInterface {

        void setPositiveClicked();
        void setNegativeClicked();
    }

    public interface SingleClickDialogInterface {

        void onClicked();
    }
}
