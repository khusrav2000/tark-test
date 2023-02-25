import 'package:flutter/cupertino.dart';
import 'package:test_tark_flutter/models/mini_ticker.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'package:bloc/bloc.dart';
import 'package:test_tark_flutter/helpers/constants.dart';

part 'live_tickers_state.dart';

class LiveTickersCubit extends Cubit<LiveTickersState> {

  String streams = '';
  WebSocketChannel _channel =
  WebSocketChannel.connect(Uri.parse(wsUri));

  LiveTickersCubit() : super(LiveTickersInitial()) {
    streams = '';
    for (int i = 0; i < tickerList.length; i ++) {
      streams += tickerList[i].symbol;
      streams += '@miniTicker';
      if (i < tickerList.length - 1) {
        streams += '/';
      }
    }
    _channel = WebSocketChannel.connect(Uri.parse('$wsUri/stream?streams=$streams'));
  }

  Stream getRealData() {
    return _channel.stream;
  }

}

