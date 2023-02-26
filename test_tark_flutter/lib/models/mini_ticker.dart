class MiniTicker {
  String? symbolOne;
  String? symbolTwo;
  String symbol;
  String closePrice;
  String openPrice;
  String totalQuoteVolume;

  MiniTicker({
    this.symbolOne,
    this.symbolTwo,
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
  MiniTicker(symbolOne: 'BTC', symbolTwo: 'USDT', symbol: 'btcusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0'),
  MiniTicker(symbolOne: 'ETH', symbolTwo: 'USDT',symbol: 'ethusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0'),
  MiniTicker(symbolOne: 'XRP', symbolTwo: 'USDT',symbol: 'xrpusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0'),
  MiniTicker(symbolOne: 'BNB', symbolTwo: 'USDT',symbol: 'bnbusdt', closePrice: '0.0', openPrice: '0.0', totalQuoteVolume: '0.0')
];