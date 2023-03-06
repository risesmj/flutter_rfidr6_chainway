import 'package:flutter/services.dart';
import 'package:rfidr6_chainway/app/entities/rfid_tag_entity.dart';
import 'package:rfidr6_chainway/app/enums/connection_status.dart';

class RFIDService {
  late MethodChannel _platform;
  List<RFIDTagEntity> results = [];

  //flags
  bool _isStartedRead = false;
  bool _isInitialized = false;

  RFIDService() {
    _platform = const MethodChannel('rfid6/channel');
  }

  Future<bool> init({forceInit = false}) async {
    if (_isInitialized && !forceInit) return false;

    bool obj = false;

    await _invokeNative(
      methodName: 'init',
      onSuccess: (o) {
        if (o is bool) {
          obj = o;
        }
      },
    );

    _isInitialized = obj;

    return obj;
  }

  Future<RFIDTagEntity?> readOne() async {
    if (!_isInitialized) return null;

    RFIDTagEntity? obj;

    await _invokeNative(
      methodName: 'readOne',
      onSuccess: (o) {
        if (o is Map) {
          obj = RFIDTagEntity.fromMap(o);
        }
      },
    );

    return obj;
  }

  Future<bool> startRead() async {
    if (!_isInitialized) return false;
    if (_isStartedRead) return false;

    bool obj = false;

    await _invokeNative(
      methodName: 'startRead',
      onSuccess: (o) {
        if (o is bool) {
          obj = o;
        }
      },
    );

    _isStartedRead = obj;

    return obj;
  }

  Future<void> stopRead() async {
    if (!_isInitialized) return;

    await _invokeNative(
      methodName: 'stopRead',
      onSuccess: (o) {
        if (o is List) {
          results = o
              .map<RFIDTagEntity>(
                (e) => RFIDTagEntity.fromMap(e),
              )
              .toList();
        }
      },
    );
    _isStartedRead = false;
  }

  Future<ConnectionStatus> getStatus() async {
    ConnectionStatus obj = ConnectionStatus.disconnected;

    await _invokeNative(
      methodName: 'readOne',
      onSuccess: (o) {
        if (o is String) {
          obj = ConnectionStatus.values
              .firstWhere((element) => element.toString() == o);
        }
      },
    );

    return obj;
  }

  Future<bool> _invokeNative({
    required methodName,
    void Function(dynamic)? onSuccess,
  }) async {
    try {
      var result = await _platform.invokeMethod(methodName);

      if (result != null) {
        onSuccess?.call(result);
        return true;
      }
    } catch (_) {}

    return false;
  }
}
