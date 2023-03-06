package com.risesmj.rfidr6_chainway.services

import android.content.Context
import android.util.Log
import com.rscja.deviceapi.RFIDWithUHFA8
import com.rscja.deviceapi.interfaces.ConnectionStatus
import com.risesmj.rfidr6_chainway.entities.RFIDTagData
import com.risesmj.rfidr6_chainway.interfaces.RFIDService

object RFIDServiceBase: RFIDService {
    private val uhf: RFIDWithUHFA8 = RFIDWithUHFA8.getInstance()
    private var isInitialized = false
    override val results: MutableList<RFIDTagData> = mutableListOf()

    init {
        uhf.setInventoryCallback {
            results.add(
                RFIDTagData(
                    epc = it.epc,
                    tid = it.tid,
                    rssi = it.rssi
                )
            )
        }
    }

    override fun init(context: Context) {
        if (!isInitialized){
            isInitialized = uhf.init(context)
            uhf.setEPCAndTIDMode()
        }
    }

    override fun readOne(): RFIDTagData? {
        if(isInitialized) {
            try {
                val result = uhf.inventorySingleTag()

                if (result != null) {
                    return RFIDTagData(
                        epc = result.epc,
                        tid = result.tid,
                        rssi = result.rssi
                    )
                }

            } catch (e: Exception) {

            }
        }

        return null
    }

    override fun startRead(): Boolean {
        if(isInitialized) {
            results.clear()
            return uhf.startInventoryTag()
        }

        return false
    }

    override fun stopRead() {
        if(isInitialized) {
            uhf.stopInventory()
            results.forEach{
                Log.d(
                    "RFIDServiceStopRead",
                    "EPC: ${it.epc} / TID: ${it.tid} / RSSI: ${it.rssi}" )
            }
        }
    }

    override fun write(data: RFIDTagData, destiny: RFIDTagData?) {
        uhf.writeData("00000000",
            RFIDWithUHFA8.Bank_EPC,
            0,
            0,
            "",
            RFIDWithUHFA8.Bank_EPC,
            0,
            0,
            data.epc
        )
    }

    override fun isConnected(): Boolean = uhf.connectStatus == ConnectionStatus.CONNECTED
    override fun isConnecting(): Boolean = uhf.connectStatus == ConnectionStatus.CONNECTING
    override fun isDisconnected(): Boolean = uhf.connectStatus == ConnectionStatus.DISCONNECTED

}