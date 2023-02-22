package ashurzoda.khusrav.tark_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import ashurzoda.khusrav.tark_test.models.Ticker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tickersRV;

    private TickerAdapter tickerAdapter;

    private ArrayList<Ticker> tickerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tickersRV = findViewById(R.id.ticker_list);

        BinanceWebSocketEcho binanceWebSocketEcho = new BinanceWebSocketEcho();
        binanceWebSocketEcho.run();

        buildRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Ticker> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Ticker item : tickerArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            tickerAdapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

        // below line we are creating a new array list
        tickerArrayList = new ArrayList<>();

        // below line is to add data to our array list.
        tickerArrayList.add(new Ticker("DSA", "DSA Self Paced Course"));
        tickerArrayList.add(new Ticker("JAVA", "JAVA Self Paced Course"));
        tickerArrayList.add(new Ticker("C++", "C++ Self Paced Course"));
        tickerArrayList.add(new Ticker("Python", "Python Self Paced Course"));
        tickerArrayList.add(new Ticker("Fork CPP", "Fork CPP Self Paced Course"));

        // initializing our adapter class.
        tickerAdapter = new TickerAdapter(tickerArrayList, MainActivity.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        tickersRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        tickersRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        tickersRV.setAdapter(tickerAdapter);
    }
}