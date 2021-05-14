package com.coinranking.testtask.coins.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.coinranking.testtask.R;
import com.coinranking.testtask.app.CoinsApplication;
import com.coinranking.testtask.coins.adapter.CoinsAdapter;
import com.coinranking.testtask.coins.presenter.CoinsPresenter;
import com.coinranking.testtask.coins.presenter.Order;
import com.coinranking.testtask.coins.presenter.OrderDirection;
import com.coinranking.testtask.coins.view.CoinsView;
import com.coinranking.testtask.data.models.Coin;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import javax.inject.Inject;
import java.util.List;

public class CoinsFragment extends MvpAppCompatFragment implements CoinsView {

    public CoinsFragment() {
        super(R.layout.fragment_coins);
    }

    RecyclerView recyclerView;

    CoinsAdapter adapter;

    ShimmerFrameLayout shimmerContainer;

    @InjectPresenter
    CoinsPresenter mCoinsPresenter;

    private ImageView sort_price_icon;
    private ImageView sort_mastercap_icon;
    private ImageView sort_24h_icon;

    private LinearLayout sort_price_view;
    private LinearLayout sort_mastercap_view;
    private LinearLayout sort_24h_view;

    private static final int PAGE_SIZE = 50;

    @ProvidePresenter
    CoinsPresenter provideCoinsPresenter() {
        return new CoinsPresenter();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CoinsAdapter();
        shimmerContainer = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.rv_coins);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        shimmerContainer.startShimmer();
        sort_price_icon = view.findViewById(R.id.sort_price_icon);
        sort_mastercap_icon = view.findViewById(R.id.sort_mastercap_icon);
        sort_24h_icon = view.findViewById(R.id.sort_24h_icon);
        sort_price_view = view.findViewById(R.id.sort_price_view);
        sort_mastercap_view = view.findViewById(R.id.sort_mastercap_view);
        sort_24h_view = view.findViewById(R.id.sort_24h_view);

        sort_price_view.setOnClickListener(v -> {
            mCoinsPresenter.setSort(Order.price);
        });
        sort_mastercap_view.setOnClickListener(v -> {
            mCoinsPresenter.setSort(Order.marketCap);
        });
        sort_24h_view.setOnClickListener(v -> {
            mCoinsPresenter.setSort(Order.hVolume);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE && !mCoinsPresenter.isLoadData()) {
                    mCoinsPresenter.loadMoreData(adapter.getItemCount());
                    adapter.addLoading();
                }
            }
        });
    }

    @Override
    public void setData(List<Coin> data) {
        adapter.setData(data);
    }

    @Override
    public void addData(List<Coin> data) {
        adapter.addData(data);
    }

    @Override
    public void showSkeleton() {
        shimmerContainer.startShimmer();
        shimmerContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSkeleton() {
        shimmerContainer.stopShimmer();
        shimmerContainer.setVisibility(View.GONE);
    }

    @Override
    public void setSortMode(Order order, OrderDirection orderDirection) {
        if (order == Order.marketCap) {
            sort_price_icon.setVisibility(View.GONE);
            sort_24h_icon.setVisibility(View.GONE);
            sort_mastercap_icon.setVisibility(View.VISIBLE);
            sort_mastercap_icon.setImageResource(orderDirection == OrderDirection.desc ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        } else if (order == Order.price) {
            sort_price_icon.setVisibility(View.VISIBLE);
            sort_24h_icon.setVisibility(View.GONE);
            sort_mastercap_icon.setVisibility(View.GONE);
            sort_price_icon.setImageResource(orderDirection == OrderDirection.desc ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        } else if (order == Order.hVolume) {
            sort_price_icon.setVisibility(View.GONE);
            sort_24h_icon.setVisibility(View.VISIBLE);
            sort_mastercap_icon.setVisibility(View.GONE);
            sort_24h_icon.setImageResource(orderDirection == OrderDirection.desc ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        }
    }

    @Override
    public void showError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(error);
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Retry",
                (dialog, id) -> {
                    mCoinsPresenter.loadMoreData(adapter.getItemCount());
                    dialog.cancel();
                });
        if(mCoinsPresenter.getOffset() != 0) {
            builder.setNegativeButton(
                    "Cancel",
                    (dialog, id) -> dialog.cancel());
        }
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}

