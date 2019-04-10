package com.ktlibrary.apiCall;


public interface ApiCallback {
      void success(String responseData);
      void failure(String responseData);
}
