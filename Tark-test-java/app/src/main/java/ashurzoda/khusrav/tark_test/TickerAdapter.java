package ashurzoda.khusrav.tark_test;

import android.content.Context;
import android.icu.number.Precision;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;

import ashurzoda.khusrav.tark_test.models.MiniTickerModel;
import ashurzoda.khusrav.tark_test.models.Ticker;

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder> {

    private ArrayList<Ticker> tickers;
    private Context context;

    public TickerAdapter(ArrayList<Ticker> tickers, Context context) {
        this.tickers = tickers;
        this.context = context;
    }

    public void filterList(ArrayList<Ticker> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        tickers = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();

    }

    public void updateList(MiniTickerModel miniTickerModel) {
        for (Ticker ticker : tickers) {
            if (String.format("%s%s",
                            ticker.getSymbolOne().toLowerCase(),
                            ticker.getSymbolTwo().toLowerCase())
                    .equals(miniTickerModel.getSymbol().toLowerCase())) {
                ticker.setPrice(miniTickerModel.getClosePrice());
                ticker.setVolume(miniTickerModel.getTotalQuoteVolume());
                double closePrice = Double.parseDouble(miniTickerModel.getClosePrice());
                double openPrice = Double.parseDouble(miniTickerModel.getOpenPrice());
                ticker.setChangePercent(100 - openPrice / closePrice * 100);
                System.out.println("Equal");
                // notifyItemChanged(i);
                notifyDataSetChanged();
                //tickerAdapter.updateList(tickerArrayList, i);
                break;
            }
        }

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
        Ticker ticker = tickers.get(position);
        holder.tickerPairOne.setText(ticker.getSymbolOne());
        holder.tickerPairTwo.setText(String.format("/%s", ticker.getSymbolTwo()));

        double vol = 0.0;
        double price = 0.0;
        try {
            vol = Double.parseDouble(ticker.getVolume());
            price = Double.parseDouble(ticker.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int count = Math.max(2, 5 - String.valueOf((int) price).length());
        holder.tickerVol.setText(String.format("Vol %.2f M", vol / 1000000.0));
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(count);
        nf.setMinimumFractionDigits(count);
        nf.isGroupingUsed();
        holder.currentPrice.setText(String.format("%s", nf.format(price)));
        holder.tickerDollar.setText(String.format("%s $", nf.format(price)));
        if (ticker.getChangePercent() >= 0) {
            holder.changePercent.setText(String.format("+%s%s", String.format("%.2f", Math.abs(ticker.getChangePercent())), "%"));
            holder.changePercent.setBackground(context.getDrawable(R.drawable.change_percent_rounded_green));
            holder.currentPrice.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.changePercent.setText(String.format("-%s%s", String.format("%.2f", Math.abs(ticker.getChangePercent())), "%"));
            holder.changePercent.setBackground(context.getDrawable(R.drawable.change_percent_rounded_red));
            holder.currentPrice.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return tickers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView tickerPairOne;
        private final TextView tickerPairTwo;
        private final TextView tickerVol;
        private final TextView currentPrice;
        private final TextView changePercent; // 24h change
        private final TextView tickerDollar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tickerPairOne = itemView.findViewById(R.id.ticker_pair_one);
            tickerPairTwo = itemView.findViewById(R.id.ticker_pair_two);
            tickerVol = itemView.findViewById(R.id.ticker_vol);
            currentPrice = itemView.findViewById(R.id.ticker_price);
            changePercent = itemView.findViewById(R.id.change_percent);
            tickerDollar = itemView.findViewById(R.id.ticker_price_dollar);

            tickerVol.setAlpha((float) 0.4);
            tickerPairTwo.setAlpha((float) 0.4);
            tickerDollar.setAlpha((float) 0.4);

        }
    }

}
