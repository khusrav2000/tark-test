package ashurzoda.khusrav.tark_test.models;

import com.google.gson.annotations.SerializedName;

public class MiniTickerModel {

    @SerializedName("e")
    private String e;

    @SerializedName("E")
    private long eventTime;

    @SerializedName("s")
    private String symbol;

    @SerializedName("c")
    private String closePrice;

    @SerializedName("o")
    private String openPrice;

    @SerializedName("h")
    private String highPrice;

    @SerializedName("l")
    private String lowPrice;

    @SerializedName("v")
    private String totalBaseVolume;

    @SerializedName("q")
    private String totalQuoteVolume;

    public MiniTickerModel(String e, long eventTime, String symbol, String closePrice, String openPrice, String highPrice, String lowPrice, String totalBaseVolume, String totalQuoteVolume) {
        this.e = e;
        this.eventTime = eventTime;
        this.symbol = symbol;
        this.closePrice = closePrice;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.totalBaseVolume = totalBaseVolume;
        this.totalQuoteVolume = totalQuoteVolume;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getTotalBaseVolume() {
        return totalBaseVolume;
    }

    public void setTotalBaseVolume(String totalBaseVolume) {
        this.totalBaseVolume = totalBaseVolume;
    }

    public String getTotalQuoteVolume() {
        return totalQuoteVolume;
    }

    public void setTotalQuoteVolume(String totalQuoteVolume) {
        this.totalQuoteVolume = totalQuoteVolume;
    }
}
