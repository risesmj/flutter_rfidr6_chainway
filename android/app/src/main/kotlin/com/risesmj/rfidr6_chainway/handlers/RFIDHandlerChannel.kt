package com.risesmj.rfidr6_chainway.handlers

import android.content.Context
import com.risesmj.rfidr6_chainway.interfaces.RFIDService
import com.risesmj.rfidr6_chainway.services.RFIDServiceBase
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class RFIDHandlerChannel(private val context: Context): MethodChannel.MethodCallHandler {
    private val service: RFIDService = RFIDServiceBase

    companion object{
        const val channel = "rfid6/channel"
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if(call.method == "init") {
            service.init(context)
            result.success(service.isConnected())
            return
        }

        if(call.method == "readOne") {
            val info = service.readOne()

            if(info != null){
                result.success(info.toMap())
            }

            return
        }

        if(call.method == "startRead") {
            result.success(service.startRead())
            return
        }

        if(call.method == "stopRead") {
            service.stopRead()
            result.success(service.results.map{ it.toMap() }.toList())
            return
        }

        if(call.method == "getStatus") {
            if(service.isConnected()){
                result.success("connected");
            }

            if(service.isConnecting()){
                result.success("connecting");
            }

            if(service.isDisconnected()){
                result.success("disconnected");
            }
            return
        }

        result.notImplemented()
    }
}