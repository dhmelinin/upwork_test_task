package com.coinranking.testtask.coins.presenter;

public enum OrderDirection {
    desc("desc"),
    asc("asc");

    private final String direction;

    OrderDirection(String direction) {
        this.direction = direction;
    }

    public String getOrderDirection() {
        return direction;
    }
}
