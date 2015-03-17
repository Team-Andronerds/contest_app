package andronerds.com.contestapp.fragments.driveToWin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.AchievementCard;
import andronerds.com.contestapp.data.Achievements;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by Chris on 2/22/2015.
 */
public class AchievementsFragment extends Fragment{

    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static AchievementsFragment newInstance(int page, String title) {
        AchievementsFragment achievementsFragment = new AchievementsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        achievementsFragment.setArguments(args);
        return achievementsFragment;
    }


    @InjectView(R.id.achievements_list_view)CardListView mAchievementsListView;
    @InjectView(R.id.nonachievements_list_view)CardListView mAchievementsNotEarnedListView;


    private ArrayList<Card> mAchievementList;
    private CardArrayAdapter mAchievementsAdapter;

    private ArrayList<Card> mAchievementsNotEarnedList;
    private CardArrayAdapter mAchievementsNotEarnedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);
        ButterKnife.inject(this, view);
        mAchievementsNotEarnedList = new ArrayList<>();
        mAchievementList = new ArrayList<>();
        SharedPreferences userProfilePrefs = view.getContext().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        List<Achievements> achList = Achievements.find(Achievements.class, "name = ?", userProfilePrefs.getString(IdentityStrings.USER_NAME, "Name"));
        AchievementCard ac;


        for (Achievements achieve : achList) {
            if (achieve.didAchieve()) {
              mAchievementList.add(new AchievementCard(view.getContext(), achieve));
            }
            else {
               achieve.setAchievementImage(R.drawable.ic_no_achievement);
               ac = new AchievementCard(view.getContext(), achieve);
               mAchievementsNotEarnedList.add(ac);
            }
        }

        int cardSize = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 125, getResources().getDisplayMetrics()));
        mAchievementsListView.getLayoutParams().height = cardSize * mAchievementList.size();
        mAchievementsNotEarnedListView.getLayoutParams().height = cardSize * mAchievementsNotEarnedList.size();

        mAchievementsAdapter = new CardArrayAdapter(getActivity(),mAchievementList);
        mAchievementsAdapter.setCardListView(mAchievementsListView);
        mAchievementsListView.setAdapter(mAchievementsAdapter);

        mAchievementsNotEarnedAdapter = new CardArrayAdapter(getActivity(),mAchievementsNotEarnedList);
        mAchievementsNotEarnedAdapter.setCardListView(mAchievementsNotEarnedListView);
        mAchievementsNotEarnedListView.setAdapter(mAchievementsNotEarnedAdapter);
        mAchievementList = null;
        mAchievementsNotEarnedList = null;
        return view;
    }
}