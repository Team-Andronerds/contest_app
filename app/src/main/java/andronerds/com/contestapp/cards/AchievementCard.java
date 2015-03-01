package andronerds.com.contestapp.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Chris on 2/22/2015.
 */
public class AchievementCard extends Card {

    @InjectView(R.id.achievement_image)ImageView mAchievementImage;


    public AchievementCard(Context context)
    {
        super(context, R.layout.card_achievement);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);

        //mTripMapView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.map_placeholder));

        Picasso.with(this.getContext())
                //.load(R.drawable.map_placeholder)
                .load(R.drawable.ic_launcher)
                .fit()
                .into(mAchievementImage);

        /*Picasso.with(this.getContext())
                .load(R.drawable.ic_chevron_right_grey600_24dp)
                .centerInside()
                .fit()
                .into(mMoreDetailsImage);*/
    }

}