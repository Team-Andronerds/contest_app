package andronerds.com.contestapp.obd;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import andronerds.com.contestapp.data.Vehicle;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;

/**
 * Created by Dilancuan on 3/16/2015.
 */
public class OnBoardDiagnostic extends NavDrawerActivity{
    private static BluetoothAdapter adapt;
    private static BroadcastReceiver receiver;
    private static Set<BluetoothDevice> pairedDevices;
    private static ArrayList<BluetoothDevice> pairedDevicesList;
    private static ArrayList<BluetoothDevice> discoveredPairedDevices;
    private static ArrayList<BluetoothDevice> discoveredNotPairedDevices;
    private static ArrayAdapter<String> pairedAdapter;
    private static IntentFilter filter;
    private static InputThread in = new InputThread();
    private static OutputThread out = new OutputThread();
    private static Fragment mParent;
    private static JSONObject trip = new JSONObject();
    private static Vehicle myVehicle = new Vehicle();
    private static int instance = 0;
    private static boolean discoverFlag = false;
    private static boolean pitchingState = false;
    private static boolean catchingState = false;
    private static boolean newMessage = false;
    private static boolean driveMode = false;
    private static boolean initialize;
    private static boolean waitingOnBluetooth = false;
    private static boolean failed = false;
    private static String secureAddress;
    private static boolean active = false;
    public static final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("HANDLER","recieved");
            Bundle b = msg.getData();
            Log.d("MainThread bundle:", b.toString());
            switch(b.getString("Purpose")){
                case "HANDSHAKE_SUCCESS":
                    Log.d("State:", "Active");
                    secureAddress = b.getString("From");
                    waitingOnBluetooth = false;
                    try{
                        JSONObject job = new JSONObject();
                        job.put("Purpose","CAR");
                        OnBoardDiagnostic.sendMessage(job);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    break;
                case "HANDSHAKE":
                    break;
                case "FAILED":
                    setState(false);
                    disconnect();
                    failed = true;
                    Toast.makeText(mParent.getActivity().getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                    mParent.getFragmentManager().popBackStack();
                    FragmentTransaction ft = mParent.getActivity().getFragmentManager().beginTransaction();
                    ft.remove(mParent).commit();
                    break;
                case "Driving":
                    String time = DateFormat.getDateTimeInstance().format(new Date());
                    Log.d("TIME", time);
                    addToTrip(b.getString("Event"), time);
                    break;
            }

        }
    };
    private OnBoardDiagnostic(){

    }

    @Override
    protected Toolbar init() {
        return null;
    }

    public static void initialize(NavDrawerActivity parent){
        adapt = BluetoothAdapter.getDefaultAdapter();
        pairedDevicesList = new ArrayList<>();
        discoveredNotPairedDevices = new ArrayList<>();
        discoveredPairedDevices = new ArrayList<>();
        pairedAdapter = new ArrayAdapter<>(parent, android.R.layout.simple_list_item_1,0);
        final NavDrawerActivity mParent = parent;
        checkBt(parent);
        getPairedDevices();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    for(int i = 0; i < pairedDevices.size(); i++){
                        if(pairedDevicesList.contains(device) && !discoveredPairedDevices.contains(device)){
                            discoveredPairedDevices.add(device);
                            Log.d("Paired:", device.toString());
                        }else if(!discoveredNotPairedDevices.contains(device) && !pairedDevicesList.contains(device)){
                            discoveredNotPairedDevices.add(device);
                            Log.d("Not Paired:", device.toString());
                        }
                    }

                }else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                    discoverFlag = true;
                }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                    Log.d("Discovery","Done");
                    discoverFlag = false;

                }else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                    if(adapt.getState() == adapt.STATE_OFF){
                        Toast.makeText(mParent.getApplicationContext(), "Some Features Require Bluetooth To Work Correctly", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        initialize = true;
    }

    public static ArrayList<BluetoothDevice> getLocalPaired(){
        return discoveredPairedDevices;
    }

    public static ArrayList<BluetoothDevice> getLocalNotPaired(){
        return discoveredNotPairedDevices;
    }

    private static void checkBt(NavDrawerActivity parent){

        if(adapt == null){
            Toast.makeText(parent.getApplicationContext(), "Bluetooth Is Not Supported", Toast.LENGTH_LONG).show();
            parent.finish();
        }else{
            if(!adapt.isEnabled()){
                enableBt(parent);
            }
        }
    }

    private static void enableBt(NavDrawerActivity parent){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        parent.startActivityForResult(enableBtIntent, 1);
    }

    public static boolean isActive(){
        return active;
    }

    public static void setState(boolean state){
        active = state;
    }

    public static void setDriveMode(boolean mode){
        driveMode = mode;
        if(driveMode){
            try{
                JSONObject job = new JSONObject();
                job.put("Purpose", "DRIVEMODEON");
                sendMessage(job);
                listen();
            }catch(JSONException e){

            }
        }else{
            try{
                JSONObject job = new JSONObject();
                job.put("Purpose", "DRIVEMODEOFF");
                sendMessage(job);
            }catch(JSONException e){

            }
        }
    }

    public static void addToTrip(String event, String timestamp){
        if(event.equals("lowGas") || event.equals("poi") || event.equals("crash")){

        }else{
            instance++;
            try{
                trip.put("Instance " + instance, event);
                trip.accumulate("Instance " + instance, timestamp);
            }catch(JSONException e){e.printStackTrace();}
        }

    }

    public static void startNewTrip(){
        String time = DateFormat.getDateTimeInstance().format(new Date());
        try{
            trip = new JSONObject();
            instance = 0;
            trip.put("START", time);
        }catch(JSONException e){e.printStackTrace();}
    }

    public static void finishTrip(){
        String time = DateFormat.getDateTimeInstance().format(new Date());
        try{
            trip.put("FINISH", time);
            Log.d("Final Trip:", trip.toString(2));
        }catch(JSONException e){e.printStackTrace();}
    }

    public static JSONObject getTrip(){ return trip;}

    public static int getInstanceCount(){ return instance;}

    public static Boolean isInitialized(){
        return initialize;
    }

    public static Boolean isDiscovering(){
        return discoverFlag;
    }

    public static boolean isPitching(){
        return pitchingState;
    }

    public static boolean isCatching(){
        return catchingState;
    }

    public static boolean getNewMessage(){
        return newMessage;
    }

    public static boolean getDriveMode(){ return driveMode; }

    public static boolean isWaitingOnBluetooth(){ return waitingOnBluetooth;}

    public static void setWaitingOnBluetooth(Boolean x){
        waitingOnBluetooth = x;
    }

    public static void setVehicle(JSONObject job){
        Log.d("CAR", "Setting Vehicle");
        try{
            Log.d("Vin",job.getString("Vin"));
            Log.d("Color",job.getString("Color"));
            myVehicle = new Vehicle();
            myVehicle.setMake(job.getString("Make"));
            myVehicle.setColor(job.getString("Color"));
            myVehicle.setModel(job.getString("Model"));
            myVehicle.setYear(job.getString("Year"));
            myVehicle.setVin(job.getString("Vin"));
        }catch(JSONException e){e.printStackTrace();}
    }

    public static Vehicle getVehicle(){
        return myVehicle;
    }

    public static void sendMessage(JSONObject job){
        Message msg = Message.obtain();
        Bundle b = new Bundle();
        b.putString("job",job.toString());
        msg.setData(b);
        OutputThread.mHandler.sendMessage(msg);
    }

    public static void refresh(NavDrawerActivity parent){
        pairedDevicesList.clear();
        discoveredPairedDevices.clear();
        discoveredNotPairedDevices.clear();
        registerReceivers(parent);
        getPairedDevices();
        startDiscovery();
    }

    private static void registerReceivers(NavDrawerActivity parent){
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        parent.registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        parent.registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        parent.registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        parent.registerReceiver(receiver, filter);
    }

    private static void getPairedDevices(){
        pairedDevices = adapt.getBondedDevices();
        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                pairedDevicesList.add(device);
            }
        }
    }

    public static void unregReceiver(NavDrawerActivity parent){
        parent.unregisterReceiver(receiver);
    }

    public static void connect(String address, Fragment parent, String name){
        mParent = parent;
        waitingOnBluetooth = true;
        android.app.ProgressDialog progress;
        progress = android.app.ProgressDialog.show(parent.getActivity(), null,
                "Connecting to " + name, true);
        new ProgressDialog().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, progress);
        adapt.cancelDiscovery();
        setState(true);
        JSONObject job = new JSONObject();
        try {
            job.put("To", address);
            job.put("From",adapt.getAddress());
            pitchingState = true;
            /*
            if(out.getStatus()== AsyncTask.Status.valueOf("RUNNING")){
                Log.d("connect", "CANCELING OUT");
                out.cancel(true);
            }else if(in.getStatus() == AsyncTask.Status.valueOf("RUNNING")){
                Log.d("connect", "CANCELING IN");
                in.cancel();
            }
            */
            listen();
            out = new OutputThread();
            out.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, job);

            JSONObject handShake = new JSONObject();
            try{
                handShake.put("Purpose","HANDSHAKE");
                handShake.put("From",adapt.getAddress());
            }catch(JSONException e){}
            OnBoardDiagnostic.sendMessage(handShake);

        }catch(JSONException e){}
    }

    public static void listen(){
        /*
        if(out.getStatus() == AsyncTask.Status.valueOf("RUNNING")){
            out.cancel(true);
            Log.d("listen()", "CANCELING OUT");
        }else if(in.getStatus() == AsyncTask.Status.valueOf("RUNNING")){
            in.cancel(true);
            Log.d("listen()", "CANCELING IN");
        }
        */
        in = new InputThread();
        in.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static void disconnect(){
        adapt.cancelDiscovery();
        waitingOnBluetooth = false;
        JSONObject disconnect = new JSONObject();
        try{
            disconnect.put("Purpose","DISCONNECT");
        }catch(JSONException e){}
        setState(false);
        sendMessage(disconnect);
    }

    public static void setPitchingState(boolean s){
        pitchingState = s;
    }

    private static void startDiscovery(){
        adapt.cancelDiscovery();
        adapt.startDiscovery();
    }
}
