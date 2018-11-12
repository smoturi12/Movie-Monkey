package com.practice.sowmya.movie_monkey.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", "876db37c9eb2be92a162285d2d985373")
                .build();

        Request request = original.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
