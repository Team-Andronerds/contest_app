package andronerds.com.contestapp.obd;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.os.AsyncTask;
import android.util.Log;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.fragments.PairedListLoadingFragment;
import andronerds.com.contestapp.fragments.SettingsConnectFragment;

/**
 * Created by Dilancuan on 3/17/2015.
 */
public class RefreshThread extends AsyncTask<Fragment, Integer, Void> {
    private Fragment parent;
    private PairedListLoadingFragment connectFrag;

    public RefreshThread(Fragment frag){
        parent = frag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        FragmentManager fm = parent.getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        connectFrag = new PairedListLoadingFragment();
        ft.addToBackStack(null);
        ft.replace(R.id.settings_fragment_container, connectFrag,"loading");
        ft.commit();
    }

    protected Void doInBackground(Fragment... params){
        while(BluetoothAdapter.getDefaultAdapter().isDiscovering()){}
        Log.d("Loading", "DONE");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        FragmentManager fm = connectFrag.getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SettingsConnectFragment connectFrag = new SettingsConnectFragment();


        ft.replace(R.id.settings_fragment_container, connectFrag,"devices");
        ft.commit();
    }
}
