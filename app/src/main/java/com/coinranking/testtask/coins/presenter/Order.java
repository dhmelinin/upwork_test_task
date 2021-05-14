package com.coinranking.testtask.coins.presenter;

public enum Order {
    price("price"),
    marketCap("marketCap"),
    hVolume("24hVolume"),
    change("change"),
    listedAt("listedAt");

    private final String order;

    Order(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
