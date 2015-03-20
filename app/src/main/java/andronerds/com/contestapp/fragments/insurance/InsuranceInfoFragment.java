package andronerds.com.contestapp.fragments.insurance;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Vehicle;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/15/15
 */
public class InsuranceInfoFragment extends Fragment
{
    @InjectView(R.id.detail_vehicle_picture)ImageView mVehiclePic;
    @InjectView(R.id.detail_vehicle_make)TextView mVehicleMake;
    @InjectView(R.id.detail_vehicle_model)TextView mVehicleModel;
    @InjectView(R.id.detail_vehicle_year)TextView mVehicleYear;

    Vehicle infoVehicle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_insurance_info, container, false);
        Bundle args = getArguments();
        infoVehicle = (Vehicle) args.getSerializable(Intent.EXTRA_TEXT);
        ButterKnife.inject(this, view);
        setViews();

        return view;
    }

    public void setViews()
    {
        Picasso.with(getActivity())
                .load(infoVehicle.getImageResource())
                .fit()
                .into(mVehiclePic);

        mVehicleMake.setText(infoVehicle.getMake());
        mVehicleModel.setText(infoVehicle.getModel());
        mVehicleYear.setText(infoVehicle.getYear());
    }
}
