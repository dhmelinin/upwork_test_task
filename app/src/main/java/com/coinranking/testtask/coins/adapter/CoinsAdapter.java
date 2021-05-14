package com.coinranking.testtask.coins.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coinranking.testtask.R;
import com.coinranking.testtask.coins.adapter.models.BaseCellModel;
import com.coinranking.testtask.coins.adapter.models.CellModel;
import com.coinranking.testtask.coins.adapter.models.LoadingModel;
import com.coinranking.testtask.coins.adapter.models.ViewType;
import com.coinranking.testtask.data.models.Coin;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.ViewHolder> {

    private ArrayList<BaseCellModel> list = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ViewType.CELL.ordinal()) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.li_coins, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.li_loading, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.get(position).getViewType() == ViewType.CELL) {
            CellModel cell = (CellModel) list.get(position);
            holder.bindData(cell.getCoin());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Coin> data) {
        list = new ArrayList<>();
        for(Coin c : data) {
            CellModel m = new CellModel();
            m.setCoin(c);
            list.add(m);
        }
        notifyDataSetChanged();
    }

    public void addLoading() {
        list.add(new LoadingModel());
        notifyDataSetChanged();
    }

    public void addData(List<Coin> data) {
        for(int i = list.size() - 1; i >= 0; i--) {
            if(list.get(i).getViewType() == ViewType.LOADING) {
                list.remove(list.get(i));
                break;
            }
        }
       for(Coin c : data) {
            CellModel m = new CellModel();
            m.setCoin(c);
            list.add(m);
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView symbol;
        private final TextView price;
        private final TextView fullPrice;
        private final TextView change;
        private final ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.number_coins);
            name = itemView.findViewById(R.id.full_name_coin);
            symbol = itemView.findViewById(R.id.short_name_coin);
            price = itemView.findViewById(R.id.price);
            fullPrice = itemView.findViewById(R.id.full_price);
            change = itemView.findViewById(R.id.change);
            icon = itemView.findViewById(R.id.icon_coin);
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bindData(Coin coin) {
            id.setText(String.format("%d",getAdapterPosition()+1));
            name.setText(coin.getName());
            symbol.setText(coin.getSymbol());
            price.setText(coin.getPrice() != null ? getHumanReadablePriceFromNumber(Double.parseDouble(coin.getPrice())): "N/A");
            fullPrice.setText(coin.getMarketCap() != null ? getHumanReadablePriceFromNumber(Double.parseDouble(coin.getMarketCap())): "N/A");
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            change.setText(coin.getChange() != null ? df.format(Double.parseDouble(coin.getChange())) + "%" : "N/A");
            if(coin.getIconUrl() != null) {
                Glide.with(itemView).load(Uri.parse(coin.getIconUrl())).placeholder(R.drawable.placeholder_circle).into(icon);
            }
        }

        @SuppressLint("DefaultLocale")
        public String getHumanReadablePriceFromNumber(Double number){
            if(number >= 1000000000){
                return String.format("$ %.2fB", number/ 1000000000.0).replace("$", "$ ");
            }
            if(number >= 1000000){
                return String.format("$ %.2fM", number/ 1000000.0).replace("$", "$ ");
            }
            if(number >= 100000){
                return String.format("$ %.2fL", number/ 100000.0).replace("$", "$ ");
            }
            if(number >=1000){
                return String.format("$ %.2fK", number/ 1000.0).replace("$", "$ ");
            }
            return DecimalFormat.getCurrencyInstance(new Locale("en", "US")).format(number);

        }
    }
}
