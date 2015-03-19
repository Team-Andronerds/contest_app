package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.User;
import andronerds.com.contestapp.data.Vehicle;
import andronerds.com.contestapp.pictureUtils.PictureUtil;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/15/15
 */
public class MyVehicleFragment extends Fragment implements View.OnClickListener
{
    @InjectView(R.id.profile_image)CircleImageView mProfileImage;
    @InjectView(R.id.profile_name)TextView mProfileName;
    @InjectView(R.id.my_vehicle_info)TextView mVehicleInfo;
    @InjectView(R.id.my_vehicle_vin)TextView mVehicleVin;
    @InjectView(R.id.my_vehicle_color)View mVehicleColor;
    @InjectView(R.id.my_vehicle_email)TextView mVehicleEmail;
    @InjectView(R.id.edit_profile_button)TextView mEditProfileButton;

    private String email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_vehicle, container, false);
        ButterKnife.inject(this, view);

        mEditProfileButton.setOnClickListener(this);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        String profileName = sharedPreferences.getString(IdentityStrings.USER_NAME,"");
        String profilePic = sharedPreferences.getString(IdentityStrings.USER_PROFILE_PIC,"");
        String profileEmail = sharedPreferences.getString(IdentityStrings.USER_EMAIL,"");
        boolean usingGooglePlus = sharedPreferences.getBoolean(IdentityStrings.USER_GPLUS,false);
        email = profileEmail;

        Log.d("Pic Test", profilePic);

        List<Vehicle> myVehicles = Vehicle.find(Vehicle.class, "name = ?", sharedPreferences.getString(IdentityStrings.USER_NAME, "Name"));
        List<User> users = User.find(User.class, "name = ?", sharedPreferences.getString(IdentityStrings.USER_NAME, ""));

        if(!profileName.equals("")) {
            mProfileName.setText(sharedPreferences.getString(IdentityStrings.USER_NAME, ""));
        }
        else
        {
            mProfileName.setText("User Name");
        }

        if(!profilePic.equals("")) {
            if(usingGooglePlus) {
                Picasso.with(this.getActivity())
                        .load(sharedPreferences.getString(IdentityStrings.USER_PROFILE_PIC, ""))
                        .fit()
                        .into(mProfileImage);
            }
            else
            {
                mProfileImage.setImageBitmap(PictureUtil.loadImageFromStorage(profilePic));
            }
        }
        else
        {
            Picasso.with(this.getActivity())
                    .load(R.drawable.ic_profile_null)
                    .fit()
                    .into(mProfileImage);
        }

        if(!profileEmail.equals(""))
        {
            mVehicleEmail.setText(profileEmail);
        }


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

        if(usingGooglePlus)
        {
            mEditProfileButton.setEnabled(false);
            mEditProfileButton.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onClick(View v)
    {
        Fragment editProfileFrag = new EditProfileFragment();
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.my_vehicle_fragment_container, editProfileFrag)
                .addToBackStack(null) // enables back key
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // if you need transition
                .commit();

        //Toast.makeText(this.getActivity().getApplicationContext(),email,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sp = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF,0);
        Log.d("ON RESUME PIC TEST",sp.getString(IdentityStrings.USER_PROFILE_PIC,""));
    }
}
