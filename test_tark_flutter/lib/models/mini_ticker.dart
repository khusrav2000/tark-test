class MiniTicker {
  String symbol;
  String closePrice;
  String openPrice;
  String totalQuoteVolume;

  MiniTicker({
    required this.symbol,
    required this.closePrice,
    required this.openPrice,
    required this.totalQuoteVolume
  });

  factory MiniTicker.fromJson(Map<String, dynamic> json) {
    json = json['data'] as Map<String, dynamic>;
    return MiniTicker(
        symbol: json['s'],
        closePrice: json['c'],
        openPrice: json['o'],
        totalQuoteVolume: json['q']
    );
  }
}

final List<MiniTicker> tickerList = [
  MiniTicker(symbol: 'btcusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0'),
  MiniTicker(symbol: 'ethusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0'),
  MiniTicker(symbol: 'xrpusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0'),
  MiniTicker(symbol: 'bnbusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0')
];