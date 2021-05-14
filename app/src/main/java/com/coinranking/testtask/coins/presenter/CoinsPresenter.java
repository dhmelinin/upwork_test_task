package com.coinranking.testtask.coins.presenter;

import com.coinranking.testtask.app.CoinsApplication;
import com.coinranking.testtask.coins.view.CoinsView;
import com.coinranking.testtask.common.Utils;
import com.coinranking.testtask.data.models.Coin;
import com.coinranking.testtask.data.models.CoinsList;

import com.coinranking.testtask.data.repositories.CoinsService;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.ListCompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import org.reactivestreams.Subscription;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class CoinsPresenter extends MvpPresenter<CoinsView> {

    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    private boolean loadData = false;

    protected void unsubscribeOnDestroy(@NonNull Disposable subscription) {
        compositeSubscription.add(subscription);
    }

    @Inject
    CoinsService coinsService;

    @Inject
    String token;

    Order order = Order.marketCap;
    OrderDirection orderDirection = OrderDirection.desc;

    int currentOffset = 0;

    public CoinsPresenter() {
        CoinsApplication.getAppComponent().inject(this);
    }
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    private void loadData() {
        loadData = true;
        Observable<List<Coin>> observable = coinsService.getCoins(token, currentOffset, order.getOrder(), orderDirection.getOrderDirection());
        Disposable disposable = observable.compose(Utils.applySchedulers())
                .subscribe(coinsList -> {
                    if(currentOffset == 0) {
                        getViewState().setData(coinsList);
                    } else {
                        getViewState().addData(coinsList);
                    }
                    getViewState().hideSkeleton();
                    loadData = false;
                }, error -> {
                    getViewState().showError(error.getLocalizedMessage());
                    loadData = false;
                });

        unsubscribeOnDestroy(disposable);
    }

    private void reloadData() {
        currentOffset = 0;
        getViewState().setData(new ArrayList<>());
        getViewState().showSkeleton();
        loadData();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }

    public void setSort(Order order) {
        if(order == this.order) {
            orderDirection = switchDirection();
        } else {
            this.order = order;
            this.orderDirection = OrderDirection.desc;
        }
        reloadData();
        getViewState().setSortMode(this.order, this.orderDirection);
    }

    private OrderDirection switchDirection() {
        switch (orderDirection) {
            case desc:
                return OrderDirection.asc;
            case asc:
                return OrderDirection.desc;
        }

        return OrderDirection.desc;
    }

    public boolean isLoadData() {
        return loadData;
    }

    public void loadMoreData(int itemCount) {
        currentOffset += 50;
        if(currentOffset > itemCount) {
            currentOffset = itemCount;
        }
        loadData();
    }

    public int getOffset() {
        return currentOffset;
    }
}
