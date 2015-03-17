package andronerds.com.contestapp.fragments.insurance;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Vehicle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/16/15
 */
public class InsuranceVehicleListFragment extends Fragment implements ListView.OnItemClickListener
{
    @InjectView(R.id.insurance_vehicle_list)ListView mVehicleList;

    @Optional
    @InjectView(R.id.vehicle_picture)CircleImageView mVehiclePicture;
    @Optional
    @InjectView(R.id.vehicle_model_year)TextView mVehicleModelYear;
    @Optional
    @InjectView(R.id.vehicle_make)TextView mVehicleMake;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);
        ButterKnife.inject(this, view);

        VehicleListAdapter vehicleListAdapter = new VehicleListAdapter(getActivity(), R.layout.row_vehicle_info);

        mVehicleList.setAdapter(vehicleListAdapter);
        mVehicleList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
    }

    private class VehicleListAdapter extends ArrayAdapter<Vehicle>
    {
        public VehicleListAdapter(Context context, int resource)
        {
            super(context, resource);
        }

        @Override
        public int getCount()
        {
            return 0;
        }

        @Override
        public Vehicle getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ButterKnife.inject(this, convertView);
            Vehicle vehicle = getItem(position);
            mVehicleModelYear.setText(vehicle.getModel() + " " + vehicle.getYear());
            mVehicleMake.setText(vehicle.getMake());
            mVehiclePicture.setImageResource(R.drawable.nav_home_gray);
            return convertView;
        }
    }
}
