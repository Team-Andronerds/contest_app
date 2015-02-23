package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
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

        mVehicleInfo.setText("Kia Sportage 2003");
        return view;
    }

}
