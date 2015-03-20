package andronerds.com.contestapp.fragments.editProfile;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.data.User;
import andronerds.com.contestapp.data.Vehicle;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Chris on 3/17/2015.
 */
public class EditUserNameFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.edit_text_user_name)EditText mEditTextUserName;
    @InjectView(R.id.edit_user_name_button)Button mUserNameButton;
    private String prevActionBarTitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user_name, container, false);
        ButterKnife.inject(this, view);
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        this.prevActionBarTitle = myV.getSupportActionBar().getTitle().toString();
        myV.getSupportActionBar().setTitle("Change User Name");
        mUserNameButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF,0);
        String oldUserName = sharedPreferences.getString(IdentityStrings.USER_NAME,"");
        String userName = mEditTextUserName.getText().toString();
        List<User> user = User.find(User.class, "name = ?",oldUserName);
        List<User> userTest = User.find(User.class, "name = ?",userName);
        List<Vehicle> userVehicle = Vehicle.find(Vehicle.class, "name = ?", oldUserName);
        List<Trip> userTrips = Trip.find(Trip.class,"name = ?", oldUserName);

        if(!userName.equals(""))
        {
            if(userTest.size() == 0) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(IdentityStrings.USER_NAME, userName);

                if (user.size() != 0) {
                    user.get(0).setName(userName);
                    user.get(0).save();

                    if(userVehicle.size() != 0)
                    {
                        userVehicle.get(0).setName(userName);
                        userVehicle.get(0).save();
                    }

                    if(userTrips.size() != 0)
                    {
                        for(Trip trip : userTrips)
                        {
                            trip.setName(userName);
                            trip.save();
                        }
                    }
                }

                editor.commit();
                getActivity().onBackPressed();
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),"User Name is Taken",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"Enter a User Name",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        myV.getSupportActionBar().setTitle(this.prevActionBarTitle);
    }
}
