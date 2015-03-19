package andronerds.com.contestapp.fragments.insurance;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Vehicle;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/16/15
 */
public class InsuranceVehicleListFragment extends Fragment implements ListView.OnItemClickListener
{
    @InjectView(R.id.insurance_vehicle_list)ListView mVehicleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);
        ButterKnife.inject(this, view);

        Vehicle newVehicle = new Vehicle();
        newVehicle.setYear("2003");
        newVehicle.setMake("Kia");
        newVehicle.setModel("Sportage");
        newVehicle.setCarImage(getResources().getDrawable(R.drawable.nav_vehicle_gray));
        newVehicle.setImageResource(R.drawable.nav_vehicle_gray);

        VehicleListAdapter vehicleListAdapter = new VehicleListAdapter(getActivity(), R.layout.row_vehicle_info);
        vehicleListAdapter.add(newVehicle);

        mVehicleList.setAdapter(vehicleListAdapter);
        mVehicleList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Vehicle passVehicle = (Vehicle) parent.getItemAtPosition(position);
        Bundle args = new Bundle();
        args.putSerializable(Intent.EXTRA_TEXT, passVehicle);

        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();;
        InsuranceInfoFragment insuranceInfoFragment = new InsuranceInfoFragment();
        insuranceInfoFragment.setArguments(args);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.insurance_info_fragment_container, insuranceInfoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class VehicleListAdapter extends ArrayAdapter<Vehicle>
    {
        public VehicleListAdapter(Context context, int resource)
        {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Vehicle vehicle = getItem(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row_vehicle_info, null);

            TextView vehicleModelYear = (TextView) row.findViewById(R.id.vehicle_model_year);
            TextView vehicleMake = (TextView) row.findViewById(R.id.vehicle_make);
            ImageView vehiclePicture = (ImageView) row.findViewById(R.id.vehicle_picture);

            vehicleModelYear.setText(vehicle.getYear() + " " + vehicle.getModel());
            vehicleMake.setText(vehicle.getMake());

            Picasso.with(getContext())
                    .load(vehicle.getImageResource())
                    .into(vehiclePicture);

            //vehiclePicture.setImageResource(R.drawable.nav_vehicle_gray);
            return row;
        }
    }
}
