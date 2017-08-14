package com.example.dmytropavlenko.gatt_protocol_integration.Services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.dmytropavlenko.gatt_protocol_integration.Services.Callbacks.BleGattScanCallback;
import com.example.dmytropavlenko.gatt_protocol_integration.Services.Callbacks.BleScanCallback;

/**
 * Created by Dmytro.Pavlenko on 14.08.2017.
 */

public class BluetoothService {

    public static String TAG = "BluetoothService";

    private Context context;
    private Handler mHandler;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothGatt bluetoothGattDevice;

    private boolean isConnectedToGatt = false;
    private boolean mScanning;
    private static final long SCAN_PERIOD = 10000;


    public BluetoothService(Context context) {
        this.context = context;
    }

    private void initManagers() {
        final BluetoothManager bluetoothManager = (BluetoothManager)context.getSystemService(Context.BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();
        this.mHandler = new Handler();
    }

    private void scanLeDevice(final boolean enable) {
        final BluetoothAdapter.LeScanCallback mLeScanCallback = new BleScanCallback();
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    public void connectByMacAddress(String macAddress) {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            return;
        }
        // isConnectedToGatt = false;

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            this.bluetoothDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
        } catch (Exception e) {
            this.bluetoothDevice = null;
            e.printStackTrace();
        }
        if (this.bluetoothDevice != null) {
            if (!connectGatt()) {
                isConnectedToGatt = false;
                Log.w(TAG, "No found devices MiBand 2");
            }
        }
    }

    private boolean connectGatt() {
        if (this.bluetoothDevice == null) {
            return false;
        }

        this.bluetoothGattDevice = this.bluetoothDevice.connectGatt(this.context, false, new BleGattScanCallback());//false

        if (this.bluetoothGattDevice == null) {
            return false;
        }
        return this.bluetoothGattDevice.connect();
    }

}
