package com.ktlibrary.paymentIntegration;

import android.content.Context;

public class PaypalInstruction {
    private Context context;

    public PaypalInstruction(Context context) {
        this.context = context;
    }
    // Create project
    // Create Paypal CLIENT_ID
    // for client id - goto developer.paypal console and create your project
    // then put paypal integration code - PaypalIntegration class
    // call getPayment method and handle onActivityResult callback
    // then stop Paypal service on onDestroy method
}
