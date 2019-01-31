package com.ktlibrary.paymentIntegration;

import android.content.Context;

public class StripeInstruction {

    private Context context;
    public StripeInstruction(Context context) {
        this.context = context;
    }

    // Reference Link - https://github.com/dominwong4/Back4app-Stripe-Android-Tutorial
    // Get Stripe API Key -Create an account and login
    // Go to this link: https://manage.stripe.com/account/apikeys
    // Go to Account Settings -> API Key
    // Create your Android Client Application
    // Add dependency
    // implementation 'com.stripe:stripe-android:6.1.2'
    // implementation "com.parse:parse-android:1.13.0"
    // Put Stripe Integration class
    // And then call both method onClicked of xyz
    // put below code for parse initialisation in onCreate - parse only init once in entire app
    //         Parse.initialize(new Parse.Configuration.Builder(this)
    //                .applicationId(StripeIntegration.APPLICATION_ID)
    //                .clientKey(StripeIntegration.CLIENT_KEY)
    //                .server(StripeIntegration.BACK4PAPP_API)
    //                .build());
    //        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
}
