package com.ktlibrary.webApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitClient {

    public static String baseURL = "";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
