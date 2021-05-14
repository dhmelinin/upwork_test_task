package com.coinranking.testtask.app;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.coinranking.testtask.di.components.AppComponent;
import com.coinranking.testtask.di.components.DaggerAppComponent;
import com.coinranking.testtask.di.modules.ContextModule;

public class CoinsApplication extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }


    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent) {
        sAppComponent = appComponent;
    }
}
