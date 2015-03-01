package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import andronerds.com.contestapp.LoginActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.adapters.SettingsListAdapter;
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

    @InjectView(R.id.sign_out_button)Button mSignOutButton;
    @InjectView(R.id.settings_list)ListView mSettingsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.inject(this, view);
        mSettingsRows = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.settings_menu_rows)));

        SettingsListAdapter listAdapter = new SettingsListAdapter(this.getActivity(), R.layout.adapter_settings, mSettingsRows);
        mSignOutButton.setOnClickListener(this);
        mSettingsList.setAdapter(listAdapter);
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
        }
    }
}
