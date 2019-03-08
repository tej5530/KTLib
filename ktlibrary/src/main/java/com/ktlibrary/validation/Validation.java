package com.ktlibrary.validation;

import android.content.Context;
import android.widget.EditText;

import com.ktlibrary.R;
import com.ktlibrary.snackbarHelper.SnackBarHandler;


public class Validation {


    private static Validation validation;

    public static Validation isValid(){
        if (validation == null){
            validation = new Validation();
        }
        return validation;
    }

    /* this is field validation function */
    public  boolean validate(EditText editText, String errorMsg) {
        if (!editText.getText().toString().trim().equalsIgnoreCase("")) {
            return true;
        } else {
            editText.requestFocus();
            editText.setError(errorMsg);
            return false;
        }
    }

    /* this is field validation function */
    public  boolean validate(EditText editText, String errorMsg, int min, int max) {
        String strVal = editText.getText().toString().trim();
        if (!strVal.equalsIgnoreCase("")) {
            if (strVal.length() < min) {
                editText.requestFocus();
                editText.setError("Minimum " + min + " character required.");
                return false;
            } else if (strVal.length() > max) {
                editText.requestFocus();
                editText.setError("Maximum " + max + " character allowed.");
                return false;
            } else {
                return true;
            }
        } else {
            editText.requestFocus();
            editText.setError(errorMsg);
            return false;
        }
    }

    /* this is field validation function */
    public  boolean validateEmail(EditText editText, String errorMsg) {
        String strVal = editText.getText().toString().trim().toLowerCase();
        String strMatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!strVal.equalsIgnoreCase("")) {
            if (!strVal.matches(strMatch)) {
                editText.setError("please enter valid email.");
                return false;
            } else {
                return true;
            }
        } else {
            editText.requestFocus();
            editText.setError(errorMsg);
            return false;
        }
    }

    /* this is field validation function */
    public  boolean validatePassword(EditText editText1, EditText editText2, String errorMsg) {
        if (editText1.getText().toString().trim().matches(editText2.getText().toString().trim())) {
            return true;
        } else {
            editText2.requestFocus();
            editText2.setError(errorMsg);
            return false;
        }
    }

    /* this is field validation function */
    public  boolean validate(Context context, EditText editText, String errorMsg) {
        if (!editText.getText().toString().trim().equalsIgnoreCase("")) {
            return true;
        } else {
            editText.requestFocus();
            SnackBarHandler.ShowSnackbar(context).show(errorMsg, R.color.colorRed,R.color.colorWhite);
            return false;
        }
    }

    /* this is field validation function */
    public  boolean validate(Context context, EditText editText, String errorMsg, int min, int max) {
        String strVal = editText.getText().toString().trim();
        if (!strVal.equalsIgnoreCase("")) {
            if (strVal.length() < min) {
                editText.requestFocus();
                SnackBarHandler.ShowSnackbar(context).show("Minimum " + min + " character required.", R.color.colorRed,R.color.colorWhite);
                return false;
            } else if (strVal.length() > max) {
                editText.requestFocus();
                SnackBarHandler.ShowSnackbar(context).show("Maximum " + max + " character allowed.", R.color.colorRed,R.color.colorWhite);
                return false;
            } else {
                return true;
            }
        } else {
            editText.requestFocus();
            SnackBarHandler.ShowSnackbar(context).show(errorMsg, R.color.colorRed,R.color.colorWhite);
            return false;
        }
    }

    /* this is field validation function */
    public  boolean validateEmail(Context context, EditText editText, String blankErrorMsg, String invalidEmailMsg) {
        String strVal = editText.getText().toString().trim().toLowerCase();
        String strMatch = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!strVal.equalsIgnoreCase("")) {
            if (!strVal.matches(strMatch)) {
                editText.requestFocus();
                SnackBarHandler.ShowSnackbar(context).show(invalidEmailMsg, R.color.colorRed,R.color.colorWhite);
                return false;
            } else {
                return true;
            }
        } else {
            editText.requestFocus();
            SnackBarHandler.ShowSnackbar(context).show(blankErrorMsg, R.color.colorRed,R.color.colorWhite);
            return false;
        }
    }

    /* this is field validation function */
    public  boolean validatePassword(Context context, EditText editText1, EditText editText2, String errorMsg) {
        if (editText1.getText().toString().trim().matches(editText2.getText().toString().trim())) {
            return true;
        } else {
            editText2.requestFocus();
            SnackBarHandler.ShowSnackbar(context).show(errorMsg, R.color.colorRed,R.color.colorWhite);
            return false;
        }
    }
}
