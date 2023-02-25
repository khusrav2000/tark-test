import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:test_tark_flutter/cubit/live_tickers_cubit.dart';
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
    TextEditingController editingController = TextEditingController();
    return Scaffold(
      backgroundColor: Colors.amberAccent,
      body: Column(children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: TextField(
            onChanged: (value) => filterTickers(value),
            decoration: const InputDecoration(
                hintText: "Search coin pairs",
                prefixIcon: Icon(Icons.search),
                border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(20.0)))),
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
                  return const Center(child: Text('No Data'),);
                }
              }
          )
        ),
      ],)
    );
  }

  Container _listItem(int index) {
    return Container (
      margin: const EdgeInsets.all(10),
      child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Column(
            children: [
              Text(tickers[index].symbol, style: const TextStyle(
                color: Colors.black,
                fontWeight: FontWeight.bold,
                fontSize: 22,
              ),),
              Text(totalQuoteVolume(tickers[index].totalQuoteVolume))
            ],
          ),
          Column(
            children: [
              Text(formatString(tickers[index].closePrice)),
              Text('${formatString(tickers[index].closePrice)} \$')
            ],
          ),
          _priceChangePercent(tickers[index]), // return Text
        ]
      ),
    );
  }

  Text _priceChangePercent(MiniTicker miniTicker) {
    var openPrice = double.tryParse(miniTicker.openPrice);
    var closePrice = double.tryParse(miniTicker.closePrice);
    var change = 0.0;
    if (openPrice != null && closePrice != null) {
      change = closePrice / openPrice * 100 - 100;
    }

    return Text(
        '${change >= 0.0 ? '+' : ''}${format(change)}%',
        style: TextStyle(
          color: Colors.white,
          fontSize: 18,
          backgroundColor: change >= 0 ? Colors.green : Colors.red,
        ),
    );
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