package com.ktlibrary.socialLogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class FacebookLogin {

    List<String> permissionNeeds = Arrays.asList("public_profile", "email", "user_photos", "user_birthday", "user_friends");
    private String TAG = "fbLogin";
    private Context context;
    private SocialInterface socialInterface;
    private CallbackManager callbackManager;
    private SocialModel socialItem;

    public FacebookLogin(Context context, SocialInterface socialInterface) {
        this.context = context;
        this.socialInterface = socialInterface;
    }

    public void signIn() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        LoginManager.getInstance().logOut();
                        graphRequest(loginResult);
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onCancel() {
                        socialInterface.onError("Cancel");
                        Log.d(TAG, "onCancel: ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        socialInterface.onError(exception.getMessage());
                        Log.d(TAG, "onError: ");
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, permissionNeeds);
    }

    private void graphRequest(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            socialItem = new SocialModel();
                            socialItem.setId(object.getString("id"));
                            socialItem.setEmail(object.getString("email"));
                            String[] splited = object.getString("name").split("\\s+");
                            socialItem.setFirstName(splited[0]);
                            socialItem.setLastName(splited[1]);
                            socialItem.setGender(object.getString("gender"));
                            socialInterface.onSuccess(socialItem);
                            Log.d(TAG, "onCompleted: id : " + object.getString("id"));
                            Log.d(TAG, "onCompleted: email : " + object.getString("email"));
                            Log.d(TAG, "onCompleted: name : " + object.getString("name"));
                            Log.d(TAG, "onCompleted: gender : " + object.getString("gender"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            socialInterface.onError("Login Failed");
                            Log.d(TAG, "onCompleted: catch");
                        }
                        LoginManager.getInstance().logOut();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            Log.d(TAG, "onActivityResult: ");
        }
    }

    public void logout() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
            Log.d(TAG, "logout: ");
        }
    }

    public void getHashKey() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d(TAG, "hash key : " + sign);
            }
        } catch (Exception e) {
            Log.d(TAG, "getHashKey: exception : " + e.getLocalizedMessage());
        }
    }
}
