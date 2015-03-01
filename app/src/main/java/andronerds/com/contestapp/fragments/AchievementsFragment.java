package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.AchievementCard;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by Chris on 2/22/2015.
 */
public class AchievementsFragment extends Fragment{

    @InjectView(R.id.achievements_list_view)CardListView mAchievementsListView;

    private ArrayList<Card> mAchievementList = new ArrayList<>();
    private CardArrayAdapter mAchievementsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
           View view = inflater.inflate(R.layout.fragment_achievement, container, false);
           ButterKnife.inject(this, view);

           for(int i = 0; i < 3; i++)
           {
              mAchievementList.add(new AchievementCard(view.getContext()));
           }

           mAchievementsAdapter = new CardArrayAdapter(getActivity(),mAchievementList);
           mAchievementsAdapter.setCardListView(mAchievementsListView);
           mAchievementsListView.setAdapter(mAchievementsAdapter);
           return view;
        }
    }

