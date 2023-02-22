package ashurzoda.khusrav.tark_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ashurzoda.khusrav.tark_test.models.Ticker;

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder> {

    private ArrayList<Ticker> tickers;

    public TickerAdapter(ArrayList<Ticker> tickers, Context context) {
        this.tickers = tickers;
    }

    public void filterList(ArrayList<Ticker> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        tickers = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticker_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TickerAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Ticker ticker = tickers.get(position);
        holder.tickerPairOne.setText(ticker.getSymbolOne());
        holder.tickerPairTwo.setText(String.format("/%s", ticker.getSymbolTwo()));
        holder.tickerVol.setText(ticker.getVolume());
        holder.currentPrice.setText(ticker.getPrice());
        holder.changePercent.setText(String.format("%s%s", ticker.getChangePercent(), "%"));
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return tickers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView tickerPairOne;
        private final TextView tickerPairTwo;
        private final TextView tickerVol;
        private final TextView currentPrice;
        private final TextView changePercent; // 24h change
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            tickerPairOne = itemView.findViewById(R.id.ticker_pair_one);
            tickerPairTwo = itemView.findViewById(R.id.ticker_pair_two);
            tickerVol = itemView.findViewById(R.id.ticker_vol);
            currentPrice = itemView.findViewById(R.id.ticker_price);
            changePercent = itemView.findViewById(R.id.change_percent);

        }
    }

}
