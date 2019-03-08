package com.ktlibrary.snackbarHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ktlibrary.R;



public class SnackBarHandler {

    private static SnackBarHandler snackBarHandler;
    private static Context context;

    public static SnackBarHandler ShowSnackbar(Context context) {
        SnackBarHandler.context = context;
        if (snackBarHandler == null) {
            snackBarHandler = new SnackBarHandler();
        }
        return snackBarHandler;
    }

    public void show(String text) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
        tSnackbar.show();
    }

    public void show(String text, int snackbarPosition) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (snackbarPosition == SnackbarPosition.snackbarTop) params.gravity = Gravity.TOP;
        else if (snackbarPosition == SnackbarPosition.snackbarBottom)
            params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        view.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
        tSnackbar.show();
    }

    public void show(String text, int snackbarPosition, String type) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (snackbarPosition == SnackbarPosition.snackbarTop) params.gravity = Gravity.TOP;
        else if (snackbarPosition == SnackbarPosition.snackbarBottom)
            params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        if (type.equals(SnackbarType.typeSuccess)) {
            view.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        }
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
        tSnackbar.show();
    }

    public void show(String text, int bgColor, int textColor) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(context.getResources().getColor(bgColor));
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(textColor));
        tSnackbar.show();
    }

    public void show(String text, int bgColor, int textColor, int snackbarPosition) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (snackbarPosition == SnackbarPosition.snackbarTop) params.gravity = Gravity.TOP;
        else if (snackbarPosition == SnackbarPosition.snackbarBottom)
            params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        view.setBackgroundColor(context.getResources().getColor(bgColor));
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(textColor));
        tSnackbar.show();
    }

    public void show(String text, int bgColor, int textColor, int snackbarPosition, int icon) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (snackbarPosition == SnackbarPosition.snackbarTop) params.gravity = Gravity.TOP;
        else if (snackbarPosition == SnackbarPosition.snackbarBottom)
            params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        view.setBackgroundColor(context.getResources().getColor(bgColor));
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(textColor));
        tSnackbar.show();
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void show(String text, int icon, int iconSizeDp, int iconRightLeft) {
//        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
//        View view = tSnackbar.getView();
//        TextView textView = view.findViewById(R.id.snackbar_text);
//        textView.setTextColor(context.getResources().getColor(R.color.colorBlack));
//        if (iconRightLeft == SnackBarInterface.iconRight) tSnackbar.setIconRight(icon, iconSizeDp);
//        else if (iconRightLeft == SnackBarInterface.iconLeft)
//            tSnackbar.setIconLeft(icon, iconSizeDp);
//        tSnackbar.show();
//    }

    public void show(String text, String actionText, final SnackBarInterface snackBarInterface) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorBlack));
        tSnackbar.setAction(actionText, v -> snackBarInterface.setOnActionClicked());
        tSnackbar.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void show(String text, String actionText, int icon, int iconSizeDp, int iconRightLeft, final SnackBarInterface snackBarInterface) {
        TSnackbar tSnackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), text, TSnackbar.LENGTH_LONG);
        View view = tSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorBlack));
        if (iconRightLeft == SnackbarIconPosition.iconRight) tSnackbar.setIconRight(icon, iconSizeDp);
        else if (iconRightLeft == SnackbarIconPosition.iconLeft)
            tSnackbar.setIconLeft(icon, iconSizeDp);
        tSnackbar.setAction(actionText, v -> snackBarInterface.setOnActionClicked());
        tSnackbar.show();
    }

    public interface SnackBarInterface {
        void setOnActionClicked();
    }

    public interface SnackbarPosition {
        int snackbarTop = 0;
        int snackbarBottom = 1;
    }

    public interface SnackbarIconPosition {
        int iconRight = 0, iconLeft = 1;
    }

    public  interface SnackbarType {
        String typeError = "Error";
        String typeSuccess = "Success";
    }
}
