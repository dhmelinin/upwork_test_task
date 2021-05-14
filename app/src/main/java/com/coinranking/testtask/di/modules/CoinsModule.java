package com.coinranking.testtask.di.modules;

import com.coinranking.testtask.app.CoinsApi;
import com.coinranking.testtask.data.repositories.CoinsService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(includes = {ApiModule.class})
public class CoinsModule {
    @Provides
    @Singleton
    public CoinsService provideGithubService(CoinsApi coinsApi) {
        return new CoinsService(coinsApi);
    }

    @Provides
    @Singleton
    public String provideToken() {
        return "coinranking3331f55d5b564252f64b34d14c60665a52c16c9acb71c324";
    }
}
