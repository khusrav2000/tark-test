package ashurzoda.khusrav.tark_test.models;

import com.google.gson.annotations.SerializedName;

public class StreamTickers {
    @SerializedName("stream")
    private String stream;

    @SerializedName("data")
    private MiniTickerModel data;

    public StreamTickers(String stream, MiniTickerModel data) {
        this.stream = stream;
        this.data = data;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public MiniTickerModel getData() {
        return data;
    }

    public void setData(MiniTickerModel data) {
        this.data = data;
    }
}
