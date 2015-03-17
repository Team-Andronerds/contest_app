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
    @InjectView(R.id.my_vehicle_image)CircleImageView mVehicleImage;
    @InjectView(R.id.my_vehicle_info)TextView mVehicleInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_vehicle, container, false);
        ButterKnife.inject(this, view);

        Picasso.with(this.getActivity())
            .load(R.drawable.pc_kia_sportage)
            .fit()
            .into(mVehicleImage);

        SharedPreferences userProfilePrefs = view.getContext().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);

        List<Vehicle> myVehicles = Vehicle.find(Vehicle.class, "name = ?", userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));

        if(myVehicles.size() == 0) {
            mVehicleInfo.setText("2003 Kia Sportage");
        }
        else
        {
            Vehicle mobile = myVehicles.get(0);
            mVehicleInfo.setText(mobile.getYear() + " " + mobile.getModel() + " " + mobile.getMake());
            MyVehicleActivity vAct = (MyVehicleActivity)getActivity();
            vAct.getToolbar().setBackgroundColor(Color.parseColor(mobile.getColor()));
        }

        return view;
    }
}
