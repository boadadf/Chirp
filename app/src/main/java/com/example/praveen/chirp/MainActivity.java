package com.example.praveen.chirp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.models.ChirpConnectState;
import io.chirp.connect.models.ChirpError;


public class MainActivity extends AppCompatActivity {

    private ChirpConnect chirpConnect;
    private Context context;

    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;

    TextView status;
    TextView lastChirp;
    TextView versionView;

    Button startStopSdkBtn;
    Button startStopSendingBtn;

    Boolean startStopSdkBtnPressed = false;


    String CHIRP_APP_KEY = "67c14FBCEEd6fd51f48C8DEcD";
    String CHIRP_APP_SECRET = "9BDC65fF4ec6b50189AE8DFcF5aa3bdAf8E69D832eac4cD83A";
    String CHIRP_APP_CONFIG = "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";

    String TAG = "ConnectDemoApp";

    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLayout = findViewById(android.R.id.content);

        status = (TextView) findViewById(R.id.stateValue);
        lastChirp = (TextView) findViewById(R.id.lastChirp);
        versionView = (TextView) findViewById(R.id.versionView);
        startStopSdkBtn = (Button) findViewById(R.id.startStopSdkBtn);
        startStopSendingBtn = (Button) findViewById(R.id.startStopSengingBtn);

        startStopSendingBtn.setAlpha(.4f);
        startStopSendingBtn.setClickable(false);
        startStopSdkBtn.setAlpha(.4f);
        startStopSdkBtn.setClickable(false);

        context = this;

        if (CHIRP_APP_KEY.equals("") || CHIRP_APP_SECRET.equals("")) {
            Log.e(TAG, "CHIRP_APP_KEY or CHIRP_APP_SECRET is not set. " +
                    "Please update with your CHIRP_APP_KEY/CHIRP_APP_SECRET from developers.chirp.io");
            return;
        }

        /**
         * Key and secret initialisation
         */
        chirpConnect = new ChirpConnect(this, CHIRP_APP_KEY, CHIRP_APP_SECRET);

        Log.v(TAG, "Connect Version: " + chirpConnect.getVersion());
        versionView.setText(chirpConnect.getVersion());

        ChirpError setConfigError = chirpConnect.setConfig(CHIRP_APP_CONFIG);
        if (setConfigError.getCode() > 0) {
            Log.e(TAG, setConfigError.getMessage());
        } else {
            startStopSdkBtn.setAlpha(1f);
            startStopSdkBtn.setClickable(true);
        }
        String versionDisplay = chirpConnect.getVersion() + "\n" +
                chirpConnect.getProtocolName() + " v" + chirpConnect.getProtocolVersion();
        versionView.setText(versionDisplay);
        chirpConnect.setListener(connectEventListener);
    }


    ConnectEventListener connectEventListener = new ConnectEventListener() {

        @Override
        public void onSystemVolumeChanged(float v, float v1) {

        }

        @Override
        public void onSending(byte[] data, int channel) {
            /**
             * onSending is called when a send event begins.
             * The data argument contains the payload being sent.
             */
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            Log.v(TAG, "ConnectCallback: onSending: " + hexData + " on channel: " + channel);
            updateLastPayload(hexData);
        }

        @Override
        public void onSent(byte[] data, int channel) {
            /**
             * onSent is called when a send event has completed.
             * The data argument contains the payload that was sent.
             */
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            updateLastPayload(hexData);
            Log.v(TAG, "ConnectCallback: onSent: " + hexData + " on channel: " + channel);
        }

        @Override
        public void onReceiving(int channel) {
            /**
             * onReceiving is called when a receive event begins.
             * No data has yet been received.
             */
            Log.v(TAG, "ConnectCallback: onReceiving on channel: " + channel);
        }

        @Override
        public void onReceived(byte[] data, int channel) {
            /**
             * onReceived is called when a receive event has completed.
             * If the payload was decoded successfully, it is passed in data.
             * Otherwise, data is null.
             */
            String hexData = "null";
            if (data != null) {
                hexData = bytesToHex(data);
            }
            Log.v(TAG, "ConnectCallback: onReceived: " + hexData + " on channel: " + channel);
            updateLastPayload(hexData);
        }

        @Override
        public void onStateChanged(int oldState, int newState) {
            /**
             * onStateChanged is called when the SDK changes state.
             */
            Log.v(TAG, "ConnectCallback: onStateChanged " + oldState + " -> " + newState);
            if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_NOT_CREATED.getCode()) {
                updateStatus("NotCreated");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_STOPPED.getCode()) {
                updateStatus("Stopped");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_PAUSED.getCode()) {
                updateStatus("Paused");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_RUNNING.getCode()) {
                updateStatus("Running");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_SENDING.getCode()) {
                updateStatus("Sending");
            } else if (newState == ChirpConnectState.CHIRP_CONNECT_STATE_RECEIVING.getCode()) {
                updateStatus("Receiving");
            } else {
                updateStatus(newState + "");
            }

        }

        public void onSystemVolumeChanged(int oldVolume, int newVolume) {
            /**
             * onSystemVolumeChanged is called when the system volume is changed.
             */
            Snackbar snackbar = Snackbar.make(parentLayout, "System volume has been changed to: " + newVolume, Snackbar.LENGTH_LONG);
            snackbar.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            })
                    .setActionTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                    .show();
            Log.v(TAG, "System volume has been changed, notify user to increase the volume when sending data");
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            if (startStopSdkBtnPressed) startSdk();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (startStopSdkBtnPressed) stopSdk();
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpConnect.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            chirpConnect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        stopSdk();
    }

    public void updateStatus(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                status.setText(newStatus);
            }
        });
    }
    public void updateLastPayload(final String newPayload) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastChirp.setText(newPayload);
            }
        });
    }

    public void stopSdk() {
        ChirpError error = chirpConnect.stop();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }
        startStopSendingBtn.setAlpha(.4f);
        startStopSendingBtn.setClickable(false);
        startStopSdkBtn.setText("Start Sdk");
    }

    public void startSdk() {
        ChirpError error = chirpConnect.start();
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
            return;
        }
        startStopSendingBtn.setAlpha(1f);
        startStopSendingBtn.setClickable(true);
        startStopSdkBtn.setText("Stop Sdk");
    }

    public void startStopSdk(View view) {
        /**
         * Start or stop the SDK.
         * Audio is only processed when the SDK is running.
         */
        startStopSdkBtnPressed = true;
        if (chirpConnect.getState() == ChirpConnectState.CHIRP_CONNECT_STATE_STOPPED) {
            startSdk();
        } else {
            stopSdk();
        }
    }

    public void sendPayload(View view) {
        /**
         * A payload is a byte array dynamic size with a maximum size defined by the config string.
         *
         * Generate a random payload, and send it.
         */
        long maxPayloadLength = chirpConnect.maxPayloadLength();
        long size = (long) new Random().nextInt((int) maxPayloadLength) + 1;
        byte[] payload = chirpConnect.randomPayload((byte) size);
        long maxSize = chirpConnect.maxPayloadLength();
        if (maxSize < payload.length) {
            Log.e(TAG, "Invalid Payload");
            return;
        }
        ChirpError error = chirpConnect.send(payload);
        if (error.getCode() > 0) {
            Log.e(TAG, error.getMessage());
        }
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}