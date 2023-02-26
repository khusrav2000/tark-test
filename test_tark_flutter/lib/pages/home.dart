import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:test_tark_flutter/cubit/live_tickers_cubit.dart';
import 'package:test_tark_flutter/helpers/constants.dart';
import 'package:test_tark_flutter/models/mini_ticker.dart';
import 'package:collection/collection.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  List<MiniTicker> tickers = tickerList;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final realDataStream =
      BlocProvider.of<LiveTickersCubit>(context).getRealData();
    return Scaffold(
      backgroundColor: homeBackgroundColor,
      body: Column(children: [
        Padding(
          padding: const EdgeInsets.all(20.0),
          child: SizedBox(height: 40,
            child: TextField(
              onChanged: (value) => filterTickers(value),
              textAlignVertical: TextAlignVertical.center,
              maxLines: 1,
              style: const TextStyle(color: Colors.white),
              decoration: const InputDecoration(
                  hintText: "Search coin pairs",
                  hintStyle: TextStyle(color: Color.fromRGBO(255, 255, 255, 0.4)),
                  contentPadding: EdgeInsets.zero,
                  prefixIcon: Icon(Icons.search, color: Color.fromRGBO(255, 255, 255, 0.4),),
                  filled: true,
                  fillColor: Color.fromRGBO(41, 48, 61, 1),
                  border: OutlineInputBorder(
                    borderSide: BorderSide.none,
                      borderRadius: BorderRadius.all(Radius.circular(20.0)))),
            ),
          ),
        ),
        Expanded(
          child: StreamBuilder(
              stream: realDataStream,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  final realData = MiniTicker.fromJson(
                      jsonDecode(snapshot.data.toString())
                      as Map<String, dynamic>);
                  return ListView.builder(
                      itemCount: tickers.length,
                      itemBuilder: (BuildContext context, int index) {
                        var ticker = tickers.firstWhereOrNull((element) => element.symbol == realData.symbol.toLowerCase());
                        if (ticker != null) {
                          ticker.closePrice = realData.closePrice;
                          ticker.openPrice = realData.openPrice;
                          ticker.totalQuoteVolume = realData.totalQuoteVolume;
                        }
                        return _listItem(index);
                      }
                  );
                }
                else{
                  return const Center(child: Text('No Data', style:
                    TextStyle(color: Color.fromRGBO(255, 255, 255, 0.4)),
                  ),);
                }
              }
          )
        ),
      ],)
    );
  }

  Container _listItem(int index) {
    var priceChangePercent = _priceChangePercent(tickers[index]);
    return Container (
      padding: const EdgeInsets.fromLTRB(20, 10, 20, 10),
      child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(children: [
                  Text(tickers[index].symbolOne!, style: const TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                    fontSize: 22,
                  ),),
                  Text('/${tickers[index].symbolTwo!}', style: const TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 0.4),
                    fontSize: 16,
                  ),),
                ],),
                Text(totalQuoteVolume(tickers[index].totalQuoteVolume), style: const TextStyle(
                  color: Color.fromRGBO(255, 255, 255, 0.4),
                  fontSize: 16,
                ),)
              ],
            ),
            Row(
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: [
                    Text(formatString(tickers[index].closePrice), textAlign: TextAlign.center,
                      style: TextStyle(
                        color: _priceChangeColor(priceChangePercent),
                        fontWeight: FontWeight.bold,
                        fontSize: 20,
                      ),),
                    Text('${formatString(tickers[index].closePrice)} \$', style: const TextStyle(
                      color: Color.fromRGBO(255, 255, 255, 0.4),
                      fontSize: 16,
                    ),)
                  ],
                ),
                Container(
                  width: 90,
                  height: 40,
                  margin: const EdgeInsets.fromLTRB(20, 0, 0, 0),
                  alignment: Alignment.center,
                  decoration: BoxDecoration(
                      color: _priceChangeColor(priceChangePercent),
                      borderRadius: BorderRadius.circular(4)
                  ),
                  child: Text('${priceChangePercent >= 0.0 ? '+' : ''}${format(priceChangePercent)}%', style: const TextStyle(
                    color: Colors.white,
                    fontSize: 18,
                  ),
                  ), // return Text,)
                ),
              ],
            ),
          ]
      ),
    );
  }

  double _priceChangePercent(MiniTicker miniTicker) {
    var openPrice = double.tryParse(miniTicker.openPrice);
    var closePrice = double.tryParse(miniTicker.closePrice);
    var change = 0.0;
    if (openPrice != null && closePrice != null) {
      change = closePrice / openPrice * 100 - 100;
    }

    return change;
  }
  
  Color _priceChangeColor(double value) {
    if (value < 0.0) {
      return const Color.fromRGBO(228, 67, 88, 1);
    }
    return const Color.fromRGBO(45, 189, 133, 1);
  }

  String formatString(String value) {
    var newValue = double.tryParse(value);
    newValue ??= 0.0;
    return format(newValue);
  }

  String totalQuoteVolume(String volume) {
    var newValue = double.tryParse(volume);
    newValue ??= 0.0;
    newValue /= 1000000.0;
    return '${format(newValue)} M';
  }

  String format(double n) {
    return n.toStringAsFixed(2);
  }

  void filterTickers(String query) {
    List<MiniTicker> dummySearchList = [];
    print(query);
    if (query.isNotEmpty) {
      print("not empty");
      for (MiniTicker miniTicker in tickerList) {
        if (miniTicker.symbol.toLowerCase().contains(query.toLowerCase())) {
          dummySearchList.add(miniTicker);
        }
      }
      setState(() {
        tickers = dummySearchList;
      });
    } else {
      print('empty');
      setState(() {
        tickers = tickerList;
      });
    }

  }
}