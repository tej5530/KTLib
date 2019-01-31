package com.ktlibrary.socialLogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ktlibrary.utils.AppConfig;

import java.util.Objects;


@SuppressLint("Registered")
public class GoogleLogin implements GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private SocialInterface socialInterface;

    public GoogleLogin(Context context, SocialInterface socialInterface) {
        this.context = context;
        this.socialInterface = socialInterface;
    }

    public void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener(this)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity) context).startActivityForResult(signInIntent, AppConfig.SocialLoginCode.GOOGLE_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConfig.SocialLoginCode.GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            SocialModel socialModel = new SocialModel();
            assert acct != null;
            socialModel.setId(acct.getId());
            socialModel.setFirstName(acct.getDisplayName());
            socialModel.setEmail(acct.getEmail());
            socialModel.setPhotoUrl(Objects.requireNonNull(acct.getPhotoUrl()).toString());
            socialInterface.onSuccess(socialModel);
        } else {
            socialInterface.onError("Login Failed");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        socialInterface.onError(connectionResult.getErrorMessage());
    }
}
