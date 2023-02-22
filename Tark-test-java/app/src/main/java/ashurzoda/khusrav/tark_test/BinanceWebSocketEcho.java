package ashurzoda.khusrav.tark_test;

import java.util.concurrent.TimeUnit;

import ashurzoda.khusrav.tark_test.models.MiniTickerModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import com.google.gson.Gson;

public final class BinanceWebSocketEcho extends WebSocketListener {
    void run() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url("wss://stream.binance.com:9443/ws/btcusdt@miniTicker")
                .build();

        Request request2 = new Request.Builder()
                .url("wss://stream.binance.com:9443/ws/ethusdt@miniTicker")
                .build();
        client.newWebSocket(request, this);
        client.newWebSocket(request2, this);
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
        MiniTickerModel miniTickerModel = gson.fromJson(text, MiniTickerModel.class);
        System.out.println(miniTickerModel);


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