package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.cards.AchievementCard;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/15/15
 */
public class DriveToWinFragment extends Fragment
{
    private ArrayList<Card> mAchievementList = new ArrayList<>();
    private CardGridArrayAdapter mAchievementsGridAdapter;


}
