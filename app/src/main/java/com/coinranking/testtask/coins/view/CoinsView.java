package com.coinranking.testtask.coins.view;

import com.coinranking.testtask.coins.presenter.Order;
import com.coinranking.testtask.coins.presenter.OrderDirection;
import com.coinranking.testtask.data.models.Coin;
import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface CoinsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setData(List<Coin> data);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void addData(List<Coin> data);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setSortMode(Order order, OrderDirection orderDirection);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showSkeleton();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideSkeleton();


    @StateStrategyType(SkipStrategy.class)
    void showError(String error);
}
