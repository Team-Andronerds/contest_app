package andronerds.com.contestapp.fragments.trips;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import andronerds.com.contestapp.MyTripsActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.MapLogoCard;
import andronerds.com.contestapp.data.Trip;
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

        MapLogoCard mapLogoCard = new MapLogoCard(getActivity());
        mMapCard.setCard(mapLogoCard);
        mMapCard.setClickable(true);
        mMapCard.setOnClickListener(this);
        mFromText.setText(currentTrip.getmTripStart());
        mToText.setText(currentTrip.getmTripEnd());

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
        MyTripsActivity activity = (MyTripsActivity) getActivity();
        activity.openGMaps(currentTrip);
    }
}
