package com.ktlibrary.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


public class CustomDialog {

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
