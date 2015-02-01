package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.HomeDriveCard;
import andronerds.com.contestapp.cards.HomeTripCard;
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
public class HomeFragment extends Fragment
{
    //@InjectView(R.id.home_drive_card_frame) CardViewNative homeDriveCard;
    @InjectView(R.id.home_trip_list) CardListView homeTripList;

    private ArrayList<Card> mTripCardsList = new ArrayList<>();
    private CardArrayAdapter mCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);
        HomeDriveCard card = new HomeDriveCard(view.getContext());
        Log.d("CARDVIEW", card.toString());
        //Log.d("CARDCONTAINER", homeDriveCard.toString());
        //homeDriveCard.setCard(card);
        mTripCardsList.add(card);
        for(int i = 0; i < 3; i++)
        {
            mTripCardsList.add(new HomeTripCard(view.getContext()));
        }
        mCardAdapter = new CardArrayAdapter(getActivity(), mTripCardsList);
        mCardAdapter.setEnableUndo(true);
        homeTripList.setAdapter(mCardAdapter);

        return view;
    }
}
