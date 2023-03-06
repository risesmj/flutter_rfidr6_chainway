package com.risesmj.rfidr6_chainway.interfaces

import android.content.Context
import com.risesmj.rfidr6_chainway.entities.RFIDTagData

interface RFIDService {
    val results: MutableList<RFIDTagData>

    fun init(context: Context)
    fun readOne(): RFIDTagData?
    fun startRead(): Boolean
    fun stopRead()
    fun write(data: RFIDTagData, destiny: RFIDTagData?)
    fun isConnected(): Boolean
    fun isConnecting(): Boolean
    fun isDisconnected(): Boolean
}