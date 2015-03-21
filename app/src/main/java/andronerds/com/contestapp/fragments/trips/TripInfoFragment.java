package andronerds.com.contestapp.fragments.trips;

import android.app.Fragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import andronerds.com.contestapp.MainActivity;
import andronerds.com.contestapp.MyTripsActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.MapLogoCard;
import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/19/15
 */
public class TripInfoFragment extends Fragment implements View.OnClickListener
{
    private static final String POSTFIX_MILES = "miles";

    private Trip currentTrip;
    private String mFromActivity;

    @InjectView(R.id.info_from_text)TextView mFromText;
    @InjectView(R.id.info_to_text)TextView mToText;
    @InjectView(R.id.map_view_card)CardView mMapCard;

    @InjectView(R.id.points_earned_value)TextView mPointsEarned;
    @InjectView(R.id.distance_travelled_value)TextView mDistanceTravelled;

    @InjectView(R.id.harsh_acceleration_value)TextView mHarshAcceleration;
    @InjectView(R.id.harsh_brakes_value)TextView mHarshBrakes;
    @InjectView(R.id.speeding_count_value)TextView mSpeedingCount;
    @InjectView(R.id.harsh_turns_value)TextView mHarshTurns;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_trip_info, container, false);
        ButterKnife.inject(this, view);
        Bundle args = getArguments();
        currentTrip = (Trip) args.getSerializable(Intent.EXTRA_TEXT);
        mFromActivity = args.getString(Intent.EXTRA_KEY_EVENT);

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        String startAdd = "";
        String endAdd = "";
        try
        {
            List<Address> startAddrList = geocoder.getFromLocation(currentTrip.getmTripStartLat(), currentTrip.getmTripStartLong(), 1);
            Address start = startAddrList.get(0);
            startAdd = start.getAddressLine(1);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            List<Address> endAddrList = geocoder.getFromLocation(currentTrip.getmTripEndLat(), currentTrip.getmTripEndLong(), 1);
            Address end = endAddrList.get(0);
            endAdd = end.getAddressLine(1);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        startAdd = startAdd.replaceAll("\\d","");
        endAdd = endAdd.replaceAll("\\d","");

        MapLogoCard mapLogoCard = new MapLogoCard(getActivity());
        mMapCard.setCard(mapLogoCard);
        mMapCard.setClickable(true);
        mMapCard.setOnClickListener(this);
        mFromText.setText(startAdd);
        mToText.setText(endAdd);

        mHarshAcceleration.setText(Integer.toString(currentTrip.getHarshAccelCount()));
        mHarshBrakes.setText(Integer.toString(currentTrip.getHarshBrakeCount()));
        mSpeedingCount.setText(Integer.toString(currentTrip.getSpeedingCount()));
        mHarshTurns.setText(Integer.toString(currentTrip.getHarshTurnCount()));

        mPointsEarned.setText(Integer.toString(currentTrip.getPoints()));
        mDistanceTravelled.setText(Integer.toString(currentTrip.getTripMileage()) + " " + POSTFIX_MILES);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        Log.d("CARD CLICKED", "Map view card clicked");
        if(mFromActivity.equals(IdentityStrings.FROM_TRIPS))
        {
            MyTripsActivity activity = (MyTripsActivity) getActivity();
            activity.openGMaps(currentTrip);
        }
        if(mFromActivity.equals(IdentityStrings.FROM_HOME))
        {
            MainActivity activity = (MainActivity) getActivity();
            activity.openGMaps(currentTrip);
        }
    }
}
