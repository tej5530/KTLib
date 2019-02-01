package com.ktlibrary.paymentIntegration;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;


public class StripeIntegration {

    /* Demo Keys and id */
//    public static final String PUBLISHABLE_KEY = "pk_test_1IqGGvSacpQ8lpIBxuVvI5rP";
//    public static final String APPLICATION_ID = "RKNck9SdN6sqcznBvy5lqnN2ln1FrrSabNcq8YEK";
//    public static final String CLIENT_KEY = "zWtkaYFS0Ia91jKkgmIHJql30cARcrDmKUGAXLTY";
//    public static final String BACK4PAPP_API = "https://parseapi.back4app.com/";
    private static Card card;
    private static ProgressDialog progress;
    private Context context;

    public interface StripeKeys {
        String PUBLISHABLE_KEY = "";
        String APPLICATION_ID = "";
        String CLIENT_KEY = "";
        String BACK4PAPP_API = "";
    }

    public StripeIntegration(Context context) {
        this.context = context;
        progress = new ProgressDialog(context);
    }

    /* Add card detail method */
    public void addCardDetail(String cardNumber, int expMonth, int expYear, String cvc) {
        card = new Card(cardNumber, expMonth, expYear, cvc);
        card.validateNumber();
        card.validateCVC();
    }

    /* Stripe payment integration service */
    public void payNow() {
        boolean validation = card.validateCard();
        if (validation) {
            startProgress();
            new Stripe(context).createToken(
                    card,
                    StripeKeys.PUBLISHABLE_KEY,
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            /* Handle your error callback */
                            Log.d("Stripe", error.toString());
                        }

                        @Override
                        public void onSuccess(Token token) {
                            /* Handle your success callback here */
                            finishProgress();
                            Log.d("Stripe", "onSuccess: token : " + token.getId());
                        }
                    });
        } else if (!card.validateNumber()) {
            Log.d("Stripe", "The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            Log.d("Stripe", "The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            Log.d("Stripe", "The CVC code that you entered is invalid");
        } else {
            Log.d("Stripe", "The card details that you entered are invalid");
        }
    }

    private void startProgress() {
        progress.setTitle("Validating Credit Card");
        progress.setMessage("Please Wait");
        progress.show();
    }

    private void finishProgress() {
        progress.dismiss();
    }
}
