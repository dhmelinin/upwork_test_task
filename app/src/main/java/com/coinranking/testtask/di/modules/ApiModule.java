package com.coinranking.testtask.di.modules;

import com.coinranking.testtask.app.CoinsApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module(includes = {RetrofitModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public CoinsApi provideCoinsApi(Retrofit retrofit) {
        return retrofit.create(CoinsApi.class);
    }
}
