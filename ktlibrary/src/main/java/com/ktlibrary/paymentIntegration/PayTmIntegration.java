package com.ktlibrary.paymentIntegration;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;

public class PayTmIntegration {

    private Context mContext;
    private String MID;
    private String ORDER_ID;
    private String CUST_ID;
    private String CHANNEL_ID;
    private String TXN_AMOUNT;
    private String CALLBACK_URL;
    private String CHECKSUMHASH;
    private String INDUSTRY_TYPE_ID;
    private String WEBSITE;

    public PayTmIntegration(Context mContext) {
        this.mContext = mContext;
    }

    public PayTmIntegration(Context mContext, String MID, String ORDER_ID, String CUST_ID, String CHANNEL_ID, String TXN_AMOUNT, String CALLBACK_URL, String CHECKSUMHASH, String INDUSTRY_TYPE_ID, String WEBSITE) {
        this.mContext = mContext;
        this.MID = MID;
        this.ORDER_ID = ORDER_ID;
        this.CUST_ID = CUST_ID;
        this.CHANNEL_ID = CHANNEL_ID;
        this.TXN_AMOUNT = TXN_AMOUNT;
        this.CALLBACK_URL = CALLBACK_URL;
        this.CHECKSUMHASH = CHECKSUMHASH;
        this.INDUSTRY_TYPE_ID = INDUSTRY_TYPE_ID;
        this.WEBSITE = WEBSITE;
    }

    public void onStartTransaction() {
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<>();

        /* these are mandatory parameters */
        paramMap.put("MID", MID);
        paramMap.put("ORDER_ID", ORDER_ID);
        paramMap.put("CUST_ID", CUST_ID);
        paramMap.put("CHANNEL_ID", CHANNEL_ID);
        paramMap.put("TXN_AMOUNT", TXN_AMOUNT);
        paramMap.put("CALLBACK_URL", CALLBACK_URL);
        paramMap.put("CHECKSUMHASH", CHECKSUMHASH);
        paramMap.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
        paramMap.put("WEBSITE", WEBSITE);


        PaytmOrder Order = new PaytmOrder(paramMap);


        /*
        Paytm Merchant Merchant = new PaytmMerchant(
        "https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
        "https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");
        */

        Service.initialize(Order, null);

        Service.startPaymentTransaction(mContext, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to
                        // initialization of webView.
                        // Error Message details the error occurred.
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of paytm_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(mContext, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(mContext, "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }
}
