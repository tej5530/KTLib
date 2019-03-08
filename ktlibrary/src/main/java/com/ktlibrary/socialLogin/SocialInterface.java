package com.ktlibrary.socialLogin;


public interface SocialInterface {

    void onSuccess(SocialModel socialModel);
    void onError(String errorMessage);
}
