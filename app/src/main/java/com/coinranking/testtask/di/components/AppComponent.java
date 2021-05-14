package com.coinranking.testtask.di.components;

import android.content.Context;
import com.coinranking.testtask.coins.presenter.CoinsPresenter;
import com.coinranking.testtask.coins.ui.CoinsFragment;
import com.coinranking.testtask.data.repositories.CoinsService;
import com.coinranking.testtask.di.modules.CoinsModule;
import com.coinranking.testtask.di.modules.ContextModule;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ContextModule.class, CoinsModule.class})
public interface AppComponent {
    Context getContext();
    CoinsService getCoinsService();
    String getToken();
    void inject(CoinsPresenter presenter);
}