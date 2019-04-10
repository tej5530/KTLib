package com.ktlibrary.snackbar;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ktlibrary.R;
import com.ktlibrary.utils.CommonConfig;

import static com.ktlibrary.snackbar.TSnackbar.LENGTH_LONG;


public class MySanckbar {


    public static void showSnackBarWithButton(Context context, String msg, int snackBarPosition, int snackBarType, String btnLabel, int bgColor, int textColor, int btnColor, View.OnClickListener onClickListener) {
        View myview = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);


        if (CommonConfig.SnackBarPosition.TOP == snackBarPosition) {

            TSnackbar tsnackBar = TSnackbar
                    .make(myview, msg, snackBarType);


            View snackBarView = tsnackBar.getView();
            if (onClickListener != null) {
                tsnackBar.setAction(btnLabel, onClickListener);


                if (CommonConfig.SnackBarBtnColor.WHITE == btnColor)
                    tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
                else
                    tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            }

            if (CommonConfig.SnackBarBgColor.GREEN == bgColor)
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBGreenSnackbarBg));
            else
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBRedSnackbarBg));

            TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
            if (CommonConfig.SnackBarTxtColor.WHITE == textColor)
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            else
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarText));


            tsnackBar.show();


        } else {
            Snackbar tsnackBar = Snackbar
                    .make(myview, msg, snackBarType);


            View snackBarView = tsnackBar.getView();
            if (onClickListener != null) {
                tsnackBar.setAction(btnLabel, onClickListener);
            }


            if (CommonConfig.SnackBarBtnColor.WHITE == btnColor)
                tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            else
                tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));

            if (CommonConfig.SnackBarBgColor.GREEN == bgColor)
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBGreenSnackbarBg));
            else
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBRedSnackbarBg));

            TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
            if (CommonConfig.SnackBarTxtColor.WHITE == textColor)
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            else
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarText));


            tsnackBar.show();
        }
    }


    public static void showSnackBarWithButton(Context context, String msg, String snackBarType, String btnLabel, View.OnClickListener onClickListener) {
        View myview = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.SUCCESS_TOP)) {
            TSnackbar tsnackBar = TSnackbar.make(myview, msg, LENGTH_LONG);
            if (onClickListener != null) {
                tsnackBar.setAction(btnLabel, onClickListener);
                tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            }
            setGreenSnackBar(context, tsnackBar);

        } else if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.SUCCESS_BOTTOM)) {
            {
                Snackbar tsnackBar = Snackbar.make(myview, msg, LENGTH_LONG);
                if (onClickListener != null) {
                    tsnackBar.setAction(btnLabel, onClickListener);
                    tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
                }
                setGreenSnackBar(context, tsnackBar);
            }
        } else if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.FAILURE_TOP)) {
            TSnackbar tsnackBar = TSnackbar.make(myview, msg, LENGTH_LONG);

            if (onClickListener != null) {
                tsnackBar.setAction(btnLabel, onClickListener);
                tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            }
            setRedSnackBar(context, tsnackBar);
        } else if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.FAILURE_BOTTOM)) {
            {
                Snackbar tsnackBar = Snackbar.make(myview, msg, LENGTH_LONG);
                if (onClickListener != null) {
                    tsnackBar.setAction(btnLabel, onClickListener);
                    tsnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
                }
                setRedSnackBar(context, tsnackBar);
            }
        }
    }

    public static void setGreenSnackBar(Context context, Snackbar tsnackBar) {
        View snackBarView = tsnackBar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBGreenSnackbarBg));
        TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
        tsnackBar.show();
    }

    public static void setGreenSnackBar(Context context, TSnackbar tsnackBar) {
        View snackBarView = tsnackBar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBGreenSnackbarBg));
        TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarText));
        tsnackBar.show();
    }

    public static void setRedSnackBar(Context context, Snackbar tsnackBar) {
        View snackBarView = tsnackBar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBRedSnackbarBg));
        TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
        tsnackBar.show();
    }

    public static void setRedSnackBar(Context context, TSnackbar tsnackBar) {
        View snackBarView = tsnackBar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBRedSnackbarBg));
        TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarText));
        tsnackBar.show();
    }


    public static void showSnackBar(Context context, String msg, String snackBarType) {
        View myview = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.SUCCESS_TOP)) {
            TSnackbar tsnackBar = TSnackbar.make(myview, msg, LENGTH_LONG);
            setGreenSnackBar(context, tsnackBar);
        } else if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.SUCCESS_BOTTOM)) {
            Snackbar tsnackBar = Snackbar
                    .make(myview, msg, LENGTH_LONG);
            setGreenSnackBar(context, tsnackBar);
        } else if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.FAILURE_TOP)) {
            TSnackbar tsnackBar = TSnackbar
                    .make(myview, msg, LENGTH_LONG);
            setRedSnackBar(context, tsnackBar);
        } else if (snackBarType.equalsIgnoreCase(CommonConfig.snackBarType.FAILURE_BOTTOM)) {
            Snackbar tsnackBar = Snackbar
                    .make(myview, msg, LENGTH_LONG);
            setRedSnackBar(context, tsnackBar);
        }
    }


    public static void showSnackBars(Context context, String msg, int snackBarPosition, int snackBarType, int bgColor, int textColor) {
        View myview = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        if (CommonConfig.SnackBarPosition.TOP == snackBarPosition) {

            TSnackbar tsnackBar = TSnackbar
                    .make(myview, msg, snackBarType);

            View snackBarView = tsnackBar.getView();

            if (CommonConfig.SnackBarBgColor.GREEN == bgColor)
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBGreenSnackbarBg));
            else
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBRedSnackbarBg));

            TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
            if (CommonConfig.SnackBarTxtColor.WHITE == textColor)
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            else
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarText));


            tsnackBar.show();


        } else {
            Snackbar tsnackBar = Snackbar
                    .make(myview, msg, snackBarType);


            View snackBarView = tsnackBar.getView();

            if (CommonConfig.SnackBarBgColor.GREEN == bgColor)
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBGreenSnackbarBg));
            else
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.SBRedSnackbarBg));

            TextView snackbartextcolor = (TextView) snackBarView.findViewById(R.id.snackbar_text);
            if (CommonConfig.SnackBarTxtColor.WHITE == textColor)
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarBtn));
            else
                snackbartextcolor.setTextColor(ContextCompat.getColor(context, R.color.SBColorSnackBarText));


            tsnackBar.show();
        }
    }


}
