package andronerds.com.contestapp.OBD;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Alexander Melton on 3/17/2015.
 */
public class InputThread extends AsyncTask<JSONObject, Integer, Void>  {
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothServerSocket mmServerSocket;
    private static boolean waitingOnResponse = true;
    public static final int MESSAGE_READ = 1;
    public static final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MESSAGE_READ:
                    byte[] readbuf = (byte[]) msg.obj;
                    String readmsg = new String(readbuf);
                    JSONObject job;
                    Log.d("Message is: ", "" + readmsg);
                    try {
                        job = new JSONObject(readmsg);
                        Log.d("JSON is: ", job.toString());
                        switch(job.getString("Purpose")){
                            case "HANDSHAKE":
                                Log.d("FROM: ", job.getString("From"));
                                Message msg1 = Message.obtain();
                                Bundle b = new Bundle();
                                b.putString("Purpose","HANDSHAKE_SUCCESS");
                                b.putString("From",job.getString("From"));
                                msg1.setData(b);
                                OnBoardDiagnostic.mHandler.sendMessage(msg1);

                            case "DISCONNECT":
                                waitingOnResponse = false;
                                break;
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = BluetoothAdapter.getDefaultAdapter().listenUsingInsecureRfcommWithServiceRecord("OBD", MY_UUID);
        } catch (IOException e) {
            Log.d("AcceptThread:", "FAILED");
        }
        mmServerSocket = tmp;
        waitingOnResponse = true;
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }

    @Override
    protected Void doInBackground(JSONObject... params) {
        BluetoothSocket socket = null;
        byte[] buffer = new byte[1024];
        int bytes;

        // Keep listening until exception occurs or a socket is returned
        while(mmServerSocket==null);
        Message msg = Message.obtain();
        Bundle b = new Bundle();
        b.putString("Purpose","ESTABLISH");
        msg.setData(b);
        OnBoardDiagnostic.mHandler.sendMessage(msg);

        while(waitingOnResponse && !OnBoardDiagnostic.isPitching()) {

            try {
                Log.d("Run", "trying accept");
                if (waitingOnResponse & !OnBoardDiagnostic.isPitching()) {
                    socket = mmServerSocket.accept();
                    Log.d("Run:", "mmSeverSocket accept SUCCESS");
                }else{
                    try {
                        mmServerSocket.close();
                        Log.d("Run:", "mmSeverSocket pitching, waiting...");
                    } catch (IOException e) {}
                }
            } catch (IOException e) {
                Log.d("Run:", "mmSeverSocket accept FAIL");

            }

            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                try {
                    bytes = socket.getInputStream().read(buffer);
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    Log.d("Run", "Socket Empty");
                }

            }
        }
        try {
            mmServerSocket.close();
        } catch (IOException e) {}

        Log.d("Disconnecting","...");

        return null;
    }
}
