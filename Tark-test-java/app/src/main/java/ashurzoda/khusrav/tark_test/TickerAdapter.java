package ashurzoda.khusrav.tark_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.tickerName.setText(ticker.getName());
        holder.tickerDescription.setText(ticker.getDescription());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return tickers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView tickerName;
        private final TextView tickerDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            tickerName = itemView.findViewById(R.id.ticker_pair_one);
            tickerDescription = itemView.findViewById(R.id.ticker_vol);
        }
    }

}
