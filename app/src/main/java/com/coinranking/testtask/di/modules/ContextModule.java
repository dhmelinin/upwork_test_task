package com.coinranking.testtask.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ContextModule {
    private Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }
}
