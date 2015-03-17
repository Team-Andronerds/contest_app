package andronerds.com.contestapp.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Achievements;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Chris on 2/22/2015.
 */
public class AchievementCard extends Card {

    @InjectView(R.id.achievement_image)ImageView mAchievementImage;

    private Achievements achieve;
    private Boolean didAchieve;
    private View cardView;

    public AchievementCard(Context context) {
        super(context, R.layout.card_achievement);
    }

    public AchievementCard(Context context, Achievements achieve)
    {
        super(context, R.layout.card_achievement);
        this.achieve = achieve;
        this.didAchieve = achieve.didAchieve();
    }

    public AchievementCard(Context context, Achievements achieve, String bgColor)
    {
        super(context,R.layout.card_achievement);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);

        Picasso.with(this.getContext())
                //.load(R.drawable.map_placeholder)
                //.load(R.drawable.ic_launcher)
                .load(achieve.getAchievementImage())
                .fit()
                .into(mAchievementImage);

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.achievement_card_bg);
        TextView title = (TextView) view.findViewById(R.id.achievement_title);
        title.setText(achieve.getTitle());
        TextView desc = (TextView) view.findViewById(R.id.achievement_description);
        desc.setText(achieve.getDescription());

        if(achieve.didAchieve())
        {

            ll.setBackgroundColor(view.getResources().getColor(R.color.white));
        }
        else
        {
            ll.setBackgroundColor(view.getResources().getColor(R.color.non_achievement_color));
            desc.setText("");
        }

    }

    public Achievements getAchievement()
    {
        return this.achieve;
    }

    public View getCardV()
    {
        return this.cardView;
    }

}