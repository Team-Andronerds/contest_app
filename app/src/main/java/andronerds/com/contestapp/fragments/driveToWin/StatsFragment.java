package andronerds.com.contestapp.fragments.driveToWin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/1/15
 */
public class StatsFragment extends Fragment
{
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static StatsFragment newInstance(int page, String title) {
        StatsFragment fragmentFirst = new StatsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

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
