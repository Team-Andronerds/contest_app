package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import andronerds.com.contestapp.LoginActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.adapters.SettingsListAdapter;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import andronerds.com.contestapp.obd.OnBoardDiagnostic;
import andronerds.com.contestapp.obd.RefreshThread;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/23/15
 */
public class SettingsFragment extends Fragment implements View.OnClickListener
{
    private int mNumRows;
    private ArrayList<String> mSettingsRows;

    @InjectView(R.id.settings_list)ListView mSettingsList;
    @InjectView(R.id.sign_out_button)Button mSignOutButton;
    @InjectView(R.id.connect_button)Button mConnectButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.inject(this, view);
        //mSettingsRows = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.settings_menu_rows)));
        mSettingsRows = new ArrayList<>();

        SettingsListAdapter listAdapter = new SettingsListAdapter(this.getActivity(), R.layout.adapter_settings, mSettingsRows);
        mSettingsList.setAdapter(listAdapter);
        mSignOutButton.setOnClickListener(this);
        if(OnBoardDiagnostic.isActive()){
            mConnectButton.setText("Disconnect from OBD");
            mConnectButton.setBackgroundColor(0xFFC0392B);
        }
        mConnectButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        switch(id) {
            case R.id.sign_out_button:
                Bundle bundle = new Bundle();
                bundle.putString(IdentityStrings.BUNDLE_SIGN_OUT, IdentityStrings.BUNDLE_SIGN_OUT);
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.connect_button:

                if(OnBoardDiagnostic.isActive() && !OnBoardDiagnostic.getDriveMode()){
                    OnBoardDiagnostic.disconnect();
                    mConnectButton.setText("Connect to OBD");
                    mConnectButton.setBackgroundColor(0xFF436EEE);

                }else if(OnBoardDiagnostic.getDriveMode()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please turn off drive mode before disconnecting", Toast.LENGTH_SHORT).show();
                }else{
                    OnBoardDiagnostic.refresh((NavDrawerActivity)this.getActivity());
                    //this.getFragmentManager().saveFragmentInstanceState(this);
                    new RefreshThread(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
                }
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(OnBoardDiagnostic.isActive()){
            mConnectButton.setText("Disconnect from OBD");
            mConnectButton.setBackgroundColor(0xFFC0392B);
        }

    }
}
