package com.risesmj.rfidr6_chainway.entities

class RFIDTagData(
    val epc: String,
    val tid: String,
    val rssi: String
){

    fun toMap(): HashMap<String,String>{
        var map = HashMap<String,String>()
        map["epc"] = this.epc
        map["tid"] = this.tid
        map["rssi"] = this.rssi
        return map
    }
}
