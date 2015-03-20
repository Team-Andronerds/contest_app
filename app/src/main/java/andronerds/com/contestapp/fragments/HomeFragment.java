package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andronerds.com.contestapp.MainActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.HomeDriveCard;
import andronerds.com.contestapp.cards.MyTripsTripCard;
import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.fragments.trips.TripInfoFragment;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 1/26/15
 */
public class HomeFragment extends Fragment implements Card.OnCardClickListener
{
    @InjectView(R.id.home_trip_list) CardListView homeTripList;
    @InjectView(R.id.no_recent_trips) TextView mNoRecentTrips;

    private ArrayList<Card> mTripCardsList = new ArrayList<>();
    private CardArrayAdapter mCardAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        HomeDriveCard card = new HomeDriveCard(getActivity());
        Log.d("CARDVIEW", card.toString());

        mTripCardsList.add(card);
        /*
        for(int i = 0; i < 3; i++)
        {
            mTripCardsList.add(new HomeTripCard(getActivity()));
        }
        */
        addRecentTrips();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        if(mTripCardsList.size() > 1)
        {
            mNoRecentTrips.setVisibility(View.GONE);
        }
        if(mTripCardsList.size() == 1)
        {
            mNoRecentTrips.setVisibility(View.VISIBLE);
        }

        mCardAdapter = new CardArrayAdapter(getActivity(), mTripCardsList);
        mCardAdapter.setEnableUndo(true);
        homeTripList.setAdapter(mCardAdapter);

        return view;
    }

    public void addRecentTrips()
    {
        SharedPreferences settings = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        String userName = settings.getString(IdentityStrings.USER_NAME, "");

        List<Trip> recentTrips =  Trip.find(Trip.class, "name = ?", userName);

        if(recentTrips.size() > 0)
        {
            for (int i = 0; i < recentTrips.size(); i++)
            {
                if(i >= 3)
                {
                    break;
                }
                MyTripsTripCard tripCard = new MyTripsTripCard(getActivity(), recentTrips.get(i));
                tripCard.setClickable(true);
                tripCard.setOnClickListener(this);
                mTripCardsList.add(tripCard);
            }
        }
    }

    @Override
    public void onClick(Card card, View view)
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        MyTripsTripCard tripCard = (MyTripsTripCard) card;
        Bundle args = new Bundle();
        Trip trip = tripCard.getTrip();
        //args.putSerializable(Intent.EXTRA_TEXT, trip);
        args.putString(Intent.EXTRA_KEY_EVENT, IdentityStrings.FROM_HOME);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        TripInfoFragment infoFragment = new TripInfoFragment();
        infoFragment.setArguments(args);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.home_fragment_container, infoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
