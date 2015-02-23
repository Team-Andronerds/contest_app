package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.MyTripsTripCard;
import butterknife.ButterKnife;
import butterknife.InjectView;
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

    private ArrayList<Card> mTripCardsList = new ArrayList<>();
    private CardGridArrayAdapter mTripsGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);
        ButterKnife.inject(this, view);

        for(int i = 0; i < 3; i++)
        {
            mTripCardsList.add(new MyTripsTripCard(view.getContext()));
        }
        mTripsGridAdapter = new CardGridArrayAdapter(getActivity(), mTripCardsList);
        mTripsGridAdapter.setCardGridView(mTripsGridView);
        mTripsGridView.setAdapter(mTripsGridAdapter);
        return view;
    }
}
