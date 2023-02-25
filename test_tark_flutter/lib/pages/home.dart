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
      backgroundColor: Colors.amberAccent,
      body: StreamBuilder(
        stream: realDataStream,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            final realData = MiniTicker.fromJson(
                jsonDecode(snapshot.data.toString())
                as Map<String, dynamic>);
            return ListView.builder(
                itemCount: tickerList.length,
                itemBuilder: (BuildContext context, int index) {
                  var ticker = tickerList.firstWhereOrNull((element) => element.symbol == realData.symbol.toLowerCase());
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
              Text(tickerList[index].symbol, style: const TextStyle(
                color: Colors.black,
                fontWeight: FontWeight.bold,
                fontSize: 20,
              ),),
              Text(tickerList[index].totalQuoteVolume)
            ],
          ),
          Column(
            children: [
              Text(tickerList[index].openPrice),
              Text(tickerList[index].closePrice)
            ],
          ),
          const Text('price change'),
        ]
      ),
    );
  }
}