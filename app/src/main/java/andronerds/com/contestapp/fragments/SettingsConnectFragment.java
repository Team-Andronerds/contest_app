package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import andronerds.com.contestapp.OBD.OnBoardDiagnostic;
import andronerds.com.contestapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Alexander Melton on 3/16/2015.
 */
public class SettingsConnectFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private int mNumRows;
    private ArrayList<String> mSettingsRows;
    private String address = "";
    private int pos = -1;
    ArrayAdapter<String> deviceAdapter;
    ArrayAdapter<String> deviceAddressAdapter;
    ArrayList<BluetoothDevice> allDevices;
    ArrayList<BluetoothDevice> pairedDevicesList;
    ArrayList<BluetoothDevice> notPairedDevicesList;

    @InjectView(R.id.Paired_List)ListView mDeviceList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings_connect, container, false);
        ButterKnife.inject(this, view);
        mDeviceList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        deviceAddressAdapter = new ArrayAdapter<>(this.getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,0);
        deviceAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_list_item_checked){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                text.setTextSize(24);
                return view;
            }
        };

        mDeviceList.setAdapter(deviceAdapter);
        mDeviceList.setOnItemClickListener(this);
        //
        pairedDevicesList = OnBoardDiagnostic.getLocalPaired();
        notPairedDevicesList = OnBoardDiagnostic.getLocalNotPaired();
        //
        allDevices = new ArrayList<>();
        allDevices.addAll(pairedDevicesList);
        allDevices.addAll(notPairedDevicesList);
        Log.d("Paired List: ", pairedDevicesList.toString());
        Log.d("Not Paired List: ", notPairedDevicesList.toString());

        for(BluetoothDevice device: allDevices){
            deviceAdapter.add(device.getName());
            deviceAddressAdapter.add(device.getAddress());
            Log.d("For loop", device.getName() + " " + "\n" + device.getAddress());
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(pairedDevicesList.toString(),deviceAddressAdapter.getItem(position));
        if(pairedDevicesList.toString().contains(deviceAddressAdapter.getItem(position))){
            if(address.equals("")){
                mDeviceList.setItemChecked(position, true);

                address = deviceAddressAdapter.getItem(position);
                pos = position;
                Log.d("Address is: ", address);
                //start progress dialog
                OnBoardDiagnostic.connect(address);

            }else if(position == pos){
                mDeviceList.setItemChecked(position, false);
                pos = -1;
                address = "";
                OnBoardDiagnostic.disconnect();
            }else{
                mDeviceList.clearChoices();
                mDeviceList.setItemChecked(position, true);

                address = deviceAddressAdapter.getItem(position);
                pos = position;
                OnBoardDiagnostic.disconnect();
                OnBoardDiagnostic.connect(address);
                Log.d("Address is: ", address);
            }
        }else{
            mDeviceList.setItemChecked(position, false);
            pos = -1;
            Toast.makeText(this.getActivity().getApplicationContext(), "Device is not paired", Toast.LENGTH_SHORT).show();
        }
    }


}
