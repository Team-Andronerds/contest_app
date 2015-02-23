package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.MyTripsTripCard;
import andronerds.com.contestapp.data.Trip;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/15/15
 */
public class MyTripsFragment extends Fragment
{
    @InjectView(R.id.trips_grid_view)CardGridView mTripsGridView;
    @InjectView(R.id.trip_start_text)TextView mTripStartText;
    @InjectView(R.id.trip_end_text)TextView mTripEndText;
    @InjectView(R.id.selected_trip_map)CircleImageView mCurrentTripMap;

    private ArrayList<Card> mTripCardsList = new ArrayList<>();
    private ArrayList<Trip> mTripsList = new ArrayList<>();
    private CardGridArrayAdapter mTripsGridAdapter;
    private Trip mCurrentTrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);
        ButterKnife.inject(this, view);

        for(int i = 0; i < 3; i++)
        {
            Trip trip = new Trip();
            if(i < 1)
            {
                trip.setmTripMap(R.drawable.pc_map);
                trip.setmTripStart("Oklahoma City, OK");
                trip.setmTripEnd("Stillwater, OK");
            }
            else
            {
                trip.setmTripMap(R.drawable.pc_map);
                trip.setmTripStart("Stillwater, OK");
                trip.setmTripEnd("Owasso, OK");
            }
            mTripsList.add(trip);
            MyTripsTripCard card = new MyTripsTripCard(view.getContext(), mTripsList.get(i));
            card.setOnClickListener(new Card.OnCardClickListener()
            {
                @Override
                public void onClick(Card card, View view)
                {
                    Log.d("TRIP SELECTED", "You have selected a new trip");
                    MyTripsTripCard tripCard = (MyTripsTripCard) card;
                    changeCurrentTripView(tripCard.getTrip());
                }
            });
            mTripCardsList.add(card);
        }

        if(mCurrentTrip == null)
        {
            changeCurrentTripView(mTripsList.get(0));
        }

        mTripsGridAdapter = new CardGridArrayAdapter(getActivity(), mTripCardsList);
        mTripsGridAdapter.setCardGridView(mTripsGridView);
        mTripsGridView.setAdapter(mTripsGridAdapter);
        return view;
    }

    public void changeCurrentTripView(Trip trip)
    {
        mCurrentTrip = trip;
        Picasso.with(getActivity())
                .load(trip.getmTripMap())
                .fit()
                .into(mCurrentTripMap);

        mTripStartText.setText(trip.getmTripStart());
        mTripEndText.setText(trip.getmTripEnd());
    }
}
