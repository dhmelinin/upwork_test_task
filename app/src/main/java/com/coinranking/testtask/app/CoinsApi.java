package com.coinranking.testtask.app;

import androidx.annotation.Nullable;
import com.coinranking.testtask.data.models.CoinsList;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CoinsApi {

    @GET("coins")
    Observable<CoinsList> getCoins(@Header("x-access-token") String token, @Query("offset") @Nullable Integer offset,  @Query("orderBy") @Nullable String orderBy, @Query("orderDirection") @Nullable String orderDirection);
}
