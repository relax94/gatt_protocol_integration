package com.example.dmytropavlenko.gatt_protocol_integration.Services.Callbacks;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

/**
 * Created by Dmytro.Pavlenko on 14.08.2017.
 */

public class BleGattScanCallback extends BluetoothGattCallback {
    public static String TAG = "BleGattScanCallback";

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//        if (status == BluetoothGatt.GATT_SUCCESS) {
//                characteristic = gatt.getService(Consts.UUID_SERVICE_1811).getCharacteristic(Consts.UUID_CHARACTERISTIC_2A46);
//                //servicio para alertar de la notificación
//                characteristicAlerta=gatt.getService(Consts.UUID_SERVICE_1802).getCharacteristic(Consts.UUID_CHARACTERISTIC_2A06);
//
//                isConnectedToGatt = true;
//        }
        Log.d(TAG, "Service discovered status " + (status == BluetoothGatt.GATT_SUCCESS ? "true" : "false"));
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED:
                Log.d(TAG, "Gatt state: connected");
                gatt.discoverServices();
                break;
            default:
                Log.d(TAG, "Gatt state: not connected");
                break;
        }
    }

        /* @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.d(TAG, "Gatt state: connected");
                    gatt.discoverServices();
                    isConnectedToGatt = true;
                    raiseonConnect();
                    break;
                default:
                    Log.d(TAG, "Gatt state: not connected");
                    isConnectedToGatt = false;
                    raiseonDisconnect();
                    break;
            }*/
    //  }

      /*  @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.d(TAG, "Write successful: " + Arrays.toString(characteristic.getValue()));
            raiseonWrite(gatt, characteristic, status);
            super.onCharacteristicWrite(gatt, characteristic, status);


        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.d(TAG, "Read successful: " + Arrays.toString(characteristic.getValue()));
            raiseonRead(gatt, characteristic, status);
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.d(TAG, " - Notification UUID: " + characteristic.getUuid().toString());
            Log.d(TAG, " - Notification value: " + Arrays.toString(characteristic.getValue()));
            raiseonNotification(gatt, characteristic);
            super.onCharacteristicChanged(gatt, characteristic);
        }*/

}
