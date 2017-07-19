package com.example.dmytropavlenko.gatt_protocol_integration;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BluetoothAdapter.LeScanCallback {

    private static final String TAG = "MAIN_ACTIVITY";

    @BindView(R.id.scanBteDevices)
    Button scanBteDevicesButton;

    @BindView(R.id.deviceMacAddressEditText)
    EditText deviceMacAddressEditText;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothGatt bluetoothGattDevice;

    private boolean mScanning;
    private Handler mHandler;

    private boolean isConnectedToGatt = false;



    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.initManagers();
    }


    private void initManagers() {
        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();
        this.mHandler = new Handler();
    }

    private void searchDevices() {
    }

    private void scanLeDevice(final boolean enable) {
        final BluetoothAdapter.LeScanCallback mLeScanCallback = this;
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
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

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Log.d("device ", device.getAddress());
    }

    @OnClick(R.id.scanBteDevices)
    public void onBteDevicesButtonClicked() {
        Toast.makeText(this, "Start scanning", Toast.LENGTH_SHORT).show();
        this.connectByMac();
    }

    public void connectByMac() {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            return;
        }
        // isConnectedToGatt = false;

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {

            this.bluetoothDevice = mBluetoothAdapter.getRemoteDevice(this.deviceMacAddressEditText.getText().toString());
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

    public boolean connectGatt() {
        if (this.bluetoothDevice == null)
            return false;

        // closeGatt();//older gatt??
        this.bluetoothGattDevice = this.bluetoothDevice.connectGatt(this, false, myGattCallback);//false

        if (this.bluetoothGattDevice == null)
            return false;

        return this.bluetoothGattDevice.connect();
    }

    private BluetoothGattCallback myGattCallback = new BluetoothGattCallback() {
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //Servicio y caracteristica necessaria para el enviio de texto
//                characteristic = gatt.getService(Consts.UUID_SERVICE_1811).getCharacteristic(Consts.UUID_CHARACTERISTIC_2A46);
//                //servicio para alertar de la notificación
//                characteristicAlerta=gatt.getService(Consts.UUID_SERVICE_1802).getCharacteristic(Consts.UUID_CHARACTERISTIC_2A06);
//
//                isConnectedToGatt = true;
            }
            Log.d(TAG, "Service discovered status " + (status == BluetoothGatt.GATT_SUCCESS ? "true" : "false"));
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.d(TAG, "Gatt state: connected");
                    gatt.discoverServices();
                    isConnectedToGatt = true;
                    break;
                default:
                    Log.d(TAG, "Gatt state: not connected");
                    isConnectedToGatt = false;
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


    };

}
