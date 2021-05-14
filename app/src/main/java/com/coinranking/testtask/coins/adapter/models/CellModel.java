package com.coinranking.testtask.coins.adapter.models;

import com.coinranking.testtask.data.models.Coin;

public class CellModel extends BaseCellModel {

    Coin coin;

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.CELL;
    }
}
