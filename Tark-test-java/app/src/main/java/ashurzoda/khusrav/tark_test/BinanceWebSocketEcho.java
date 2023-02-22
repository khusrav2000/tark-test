package ashurzoda.khusrav.tark_test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ashurzoda.khusrav.tark_test.models.MiniTickerModel;
import ashurzoda.khusrav.tark_test.models.StreamTickers;
import ashurzoda.khusrav.tark_test.models.Ticker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import com.google.gson.Gson;

public final class BinanceWebSocketEcho extends WebSocketListener {
    void run(ArrayList<Ticker> tickers) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();

        StringBuilder streams = new StringBuilder();

        for (Ticker ticker: tickers) {
            streams.append(ticker.getSymbolOne().toLowerCase());
            streams.append(ticker.getSymbolTwo().toLowerCase());
            streams.append("@miniTicker");
            streams.append("/");
        }

        if (streams.length() > 0) {
            streams.deleteCharAt(streams.length() - 1);
        }

        Request request = new Request.Builder()
                .url(String.format("%s%s",
                        "wss://stream.binance.com:9443/stream?streams=",
                        streams.toString()))
                .build();
        System.out.println(request.url());
        client.newWebSocket(request, this);
        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    @Override public void onOpen(WebSocket webSocket, Response response) {
        // webSocket.send("Hello...");
        // webSocket.send("...World!");
        // webSocket.send(ByteString.decodeHex("deadbeef"));
        // webSocket.close(1000, "Goodbye, World!");
    }

    @Override public void onMessage(WebSocket webSocket, String text) {
        System.out.println("MESSAGE1: " + text);
        Gson gson = new Gson();
        try {
            StreamTickers streamTickers = gson.fromJson(text, StreamTickers.class);
            System.out.println(streamTickers.getData().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("MESSAGE2: " + bytes.hex());
    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

}