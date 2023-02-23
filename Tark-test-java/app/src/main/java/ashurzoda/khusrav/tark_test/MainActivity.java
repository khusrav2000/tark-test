package ashurzoda.khusrav.tark_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import ashurzoda.khusrav.tark_test.models.MiniTickerModel;
import ashurzoda.khusrav.tark_test.models.Ticker;

interface StreamCallbacks {
    void streamOnMessage(MiniTickerModel miniTickerModel);
}

public class MainActivity extends AppCompatActivity implements StreamCallbacks {

    private RecyclerView tickersRV;

    private TickerAdapter tickerAdapter;

    private ArrayList<Ticker> tickerArrayList;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tickersRV = findViewById(R.id.ticker_list);
        searchView = findViewById(R.id.search_tickers);
        searchView.clearFocus();

        createSearchListeners();

        buildRecyclerView();

        BinanceWebSocketEcho binanceWebSocketEcho = new BinanceWebSocketEcho(this);
        binanceWebSocketEcho.run(tickerArrayList);
    }

    private void createSearchListeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        ArrayList<Ticker> filteredlist = new ArrayList<>();

        for (Ticker item : tickerArrayList) {
            if (item.getSymbolOne().toLowerCase().contains(text.toLowerCase())
                    || item.getSymbolTwo().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            tickerAdapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

        tickerArrayList = new ArrayList<>();

        tickerArrayList.add(new Ticker("BTC", "USDT", "-","-",0.0));
        tickerArrayList.add(new Ticker("ETH", "USDT", "-","-",0.0));
        tickerArrayList.add(new Ticker("XRP", "USDT", "-","-",0.0));
        tickerArrayList.add(new Ticker("BNB", "USDT", "-","-",0.0));

        tickerAdapter = new TickerAdapter(tickerArrayList, MainActivity.this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        tickersRV.setHasFixedSize(true);

        tickersRV.setLayoutManager(manager);
        tickersRV.setAdapter(tickerAdapter);
    }
    @Override
    public void streamOnMessage(MiniTickerModel miniTickerModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tickerAdapter.updateList(miniTickerModel);
            }
        });

    }
}