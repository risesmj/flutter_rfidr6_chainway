class RFIDTagEntity {
  final String epc;
  final String tid;
  final String rssi;

  RFIDTagEntity({
    required this.epc,
    required this.tid,
    required this.rssi,
  });

  factory RFIDTagEntity.fromMap(map) {
    return RFIDTagEntity(
      epc: map['epc'],
      tid: map['tid'],
      rssi: map['rssi'],
    );
  }
}
