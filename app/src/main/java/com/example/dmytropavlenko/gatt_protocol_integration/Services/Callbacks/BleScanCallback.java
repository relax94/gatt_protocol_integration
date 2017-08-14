package com.example.dmytropavlenko.gatt_protocol_integration.Services.Callbacks;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

/**
 * Created by Dmytro.Pavlenko on 14.08.2017.
 */

public class BleScanCallback implements BluetoothAdapter.LeScanCallback {
    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Log.d("device ", device.getAddress());
    }
}
