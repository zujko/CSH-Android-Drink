package edu.csh.cshdrink.network;

import com.squareup.okhttp.OkHttpClient;

import retrofit.Retrofit;


public class ServiceGenerator {

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DrinkService.BASE_URL)
                .client(new OkHttpClient())
                .build();

        return retrofit.create(serviceClass);
    }
}
