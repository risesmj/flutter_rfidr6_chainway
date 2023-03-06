import 'package:flutter/material.dart';
import 'package:rfidr6_chainway/app/service/rfid_service.dart';

class TestPage extends StatefulWidget {
  const TestPage({super.key});

  @override
  State<TestPage> createState() => _TestPageState();
}

class _TestPageState extends State<TestPage> {
  late final RFIDService service;

  @override
  void initState() {
    super.initState();
    service = RFIDService();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('RFID R6 Demo'),
      ),
      body: SizedBox(
        width: MediaQuery.of(context).size.width,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          mainAxisSize: MainAxisSize.max,
          children: [
            ElevatedButton(
              onPressed: () {
                service.init();
              },
              child: const Text('Init'),
            ),
            ElevatedButton(
              onPressed: () {
                service.readOne();
              },
              child: const Text('Read'),
            ),
            ElevatedButton(
              onPressed: () {
                service.startRead();
              },
              child: const Text('Start Read'),
            ),
            ElevatedButton(
              onPressed: () {
                service.stopRead();
              },
              child: const Text('Stop Read'),
            ),
          ],
        ),
      ),
    );
  }
}
