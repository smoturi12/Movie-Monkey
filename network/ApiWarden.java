package com.practice.sowmya.movie_monkey.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.practice.sowmya.movie_monkey.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiWarden {

    private static final String TAG = ApiWarden.class.getSimpleName();

    protected Retrofit retrofit;
    protected MovieApi api;
    protected static ApiWarden instance;

    public static ApiWarden init() {
        instance = new ApiWarden();
        return instance;
    }

    public void setup() {
        retrofit = buildRetrofit(Constants.BASE_URL);
        api = retrofit.create(MovieApi.class);
    }

    @NonNull
    public MovieApi api() {
        return api;
    }

    @NonNull
    static Retrofit buildRetrofit(@NonNull String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new AuthRequestInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpBuilder.build())
                .build();
    }

    @NonNull
    public static ApiWarden getInstance() {
        if (instance == null) {
            Log.e(TAG,"ApiWarden not initialized!!!");
            throw new IllegalStateException("ApiWarden not initialized!!!");
        }
        return instance;
    }

    public static String getImagePath(String imagePath) {
        return Constants.IMAGE_BASE_URL + imagePath;
    }

}

