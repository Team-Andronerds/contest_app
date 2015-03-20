package andronerds.com.contestapp.fragments.trips;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class TripInfoFragment extends Fragment
{
    @InjectView(R.id.info_from_text)TextView mFromText;
    @InjectView(R.id.info_to_text)TextView mToText;
    @InjectView(R.id.map_view_card)CardView mMapCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_trip_info, container, false);
        ButterKnife.inject(this, view);
        Bundle args = getArguments();
        Trip trip = (Trip) args.getSerializable(Intent.EXTRA_TEXT);

        mMapCard.setCard(new MapLogoCard(getActivity()));
        mFromText.setText(trip.getmTripStart());
        mToText.setText(trip.getmTripEnd());

        return view;
    }
}
