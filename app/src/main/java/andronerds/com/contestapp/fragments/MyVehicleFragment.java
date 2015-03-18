package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.User;
import andronerds.com.contestapp.data.Vehicle;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/15/15
 */
public class MyVehicleFragment extends Fragment
{
    @InjectView(R.id.profile_image)CircleImageView mProfileImage;
    @InjectView(R.id.profile_name)TextView mProfileName;
    @InjectView(R.id.my_vehicle_info)TextView mVehicleInfo;
    @InjectView(R.id.my_vehicle_vin)TextView mVehicleVin;
    @InjectView(R.id.my_vehicle_color)View mVehicleColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_vehicle, container, false);
        ButterKnife.inject(this, view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        mProfileName.setText(sharedPreferences.getString(IdentityStrings.USER_NAME,""));

        Picasso.with(this.getActivity())
            .load(sharedPreferences.getString(IdentityStrings.USER_PROFILE_PIC, ""))
            .fit()
            .into(mProfileImage);

        List<Vehicle> myVehicles = Vehicle.find(Vehicle.class, "name = ?", sharedPreferences.getString(IdentityStrings.USER_NAME, "Name"));
        List<User> users = User.listAll(User.class);

        if(myVehicles.size() == 0) {
            mVehicleInfo.setText("YEAR MAKE MODEL");
        }
        else
        {
            Vehicle vehicle = myVehicles.get(0);
            mVehicleInfo.setText(vehicle.getYear() + " " + vehicle.getModel() + " " + vehicle.getMake());
            mVehicleVin.setText(vehicle.getVin());
            mVehicleColor.setBackgroundColor(Color.parseColor(vehicle.getColor()));
        }

        if(users.size() == 0)
        {


        }

        return view;
    }
}
