package com.risesmj.rfidr6_chainway

import androidx.annotation.NonNull
import com.risesmj.rfidr6_chainway.handlers.RFIDHandlerChannel
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger,
            RFIDHandlerChannel.channel).setMethodCallHandler(RFIDHandlerChannel(this))
    }
}
