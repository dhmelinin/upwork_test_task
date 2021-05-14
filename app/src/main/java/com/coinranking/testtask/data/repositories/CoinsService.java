package com.coinranking.testtask.data.repositories;

import com.coinranking.testtask.app.CoinsApi;
import com.coinranking.testtask.data.models.Coin;
import com.coinranking.testtask.data.models.CoinsList;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public class CoinsService {
    private CoinsApi mCoinsApi;

    public CoinsService(CoinsApi coinsApi) {
        mCoinsApi = coinsApi;
    }


    public Observable<List<Coin>> getCoins(String token, Integer offset, String orderBy, String orderDirection) {
        return mCoinsApi.getCoins(token, offset, orderBy, orderDirection)
                .flatMap( d -> Observable.just(d.getData()))
                .flatMap(data -> Observable.fromArray(data.getCoins()));
    }
}
