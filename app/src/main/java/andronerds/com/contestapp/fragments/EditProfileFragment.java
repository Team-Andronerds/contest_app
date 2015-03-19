package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.fragments.editProfile.EditEmailFragment;
import andronerds.com.contestapp.fragments.editProfile.EditPasswordFragment;
import andronerds.com.contestapp.fragments.editProfile.EditPictureFragment;
import andronerds.com.contestapp.fragments.editProfile.EditUserNameFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Chris on 3/17/2015.
 */

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.edit_profile_username_button)Button mEditNameButton;
    @InjectView(R.id.edit_profile_email_button)Button mEditEmailButton;
    @InjectView(R.id.edit_profile_password_button)Button mEditPasswordButton;
    @InjectView(R.id.edit_profile_picture_button)Button mEditPictureButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.inject(this, view);

        mEditEmailButton.setOnClickListener(this);
        mEditNameButton.setOnClickListener(this);
        mEditPasswordButton.setOnClickListener(this);
        mEditPictureButton.setOnClickListener(this);

        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        myV.getSupportActionBar().setTitle("Edit Profile");

        return view;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        myV.getSupportActionBar().setTitle("View Profile");
    }

    @Override
    public void onClick(View v)
    {
        Button button = (Button) v;

        if(button == mEditEmailButton)
        {
            Fragment editEmailFrag = new EditEmailFragment();
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_vehicle_fragment_container, editEmailFrag)
                    .addToBackStack(null) // enables back key
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
        else if(button == mEditNameButton)
        {
            Fragment editNameFrag = new EditUserNameFragment();
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_vehicle_fragment_container, editNameFrag)
                    .addToBackStack(null) // enables back key
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        }
        else if(button == mEditPasswordButton)
        {
            Fragment editPasswordFrag = new EditPasswordFragment();
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_vehicle_fragment_container, editPasswordFrag)
                    .addToBackStack(null) // enables back key
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
        else if(button == mEditPictureButton)
        {
            Fragment editPicFrag = new EditPictureFragment();
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_vehicle_fragment_container, editPicFrag, "PICS")
                    .addToBackStack(null) // enables back key
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}
