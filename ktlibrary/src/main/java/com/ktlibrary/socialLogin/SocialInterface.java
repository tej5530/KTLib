package com.ktlibrary.socialLogin;

/**
 * Created by KT on 29/01/19.
 */

public interface SocialInterface {

    void onSuccess(SocialModel socialModel);
    void onError(String errorMessage);
}
