package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 3/17/2015.
 */
public class PairedListLoadingFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_paired_list_loading, container, false);
        ButterKnife.inject(this, view);
        NavDrawerActivity parent = (NavDrawerActivity)this.getActivity();
        parent.getSupportActionBar().setTitle("Select Your OBD");
        return view;
    }
}
