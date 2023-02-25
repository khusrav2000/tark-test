import 'package:flutter/material.dart';
import 'package:test_tark_flutter/cubit/live_tickers_cubit.dart';
import 'package:test_tark_flutter/pages/home.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Tark Test',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MultiBlocProvider(
        providers: [
          BlocProvider(create: (BuildContext context) => LiveTickersCubit())
        ],
        child: const SafeArea(child: Home(),),
      )
    );
  }
}


