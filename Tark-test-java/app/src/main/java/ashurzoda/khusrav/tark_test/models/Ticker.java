package ashurzoda.khusrav.tark_test.models;

public class Ticker {
    private String symbolOne;
    private String symbolTwo;
    private String price;
    private String volume;
    private String changePercent;

    public Ticker(String symbolOne, String symbolTwo, String price, String volume, String changePercent) {
        this.symbolOne = symbolOne;
        this.symbolTwo = symbolTwo;
        this.price = price;
        this.volume = volume;
        this.changePercent = changePercent;
    }

    public String getSymbolOne() {
        return symbolOne;
    }

    public void setSymbolOne(String symbolOne) {
        this.symbolOne = symbolOne;
    }

    public String getSymbolTwo() {
        return symbolTwo;
    }

    public void setSymbolTwo(String symbolTwo) {
        this.symbolTwo = symbolTwo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setH24Change(String changePercent) {
        this.changePercent = changePercent;
    }
}
