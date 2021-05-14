package com.coinranking.testtask.di.modules;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class RetrofitModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl("https://api.coinranking.com/v2/").build();
    }

    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(converterFactory);
    }

    @Provides
    @Singleton
    public Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
}
