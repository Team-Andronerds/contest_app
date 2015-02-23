package andronerds.com.contestapp.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import andronerds.com.contestapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/1/15
 */
public class HomeTripCard extends Card
{
    @InjectView(R.id.trip_map_view) ImageView tripMapView;
    @InjectView(R.id.trip_from_text) TextView tripFromText;
    @InjectView(R.id.trip_to_text) TextView tripToText;
    @InjectView(R.id.trip_points_earned) TextView tripPtsEarnedText;

    public HomeTripCard(Context context)
    {
        super(context, R.layout.card_home_trips);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);

        tripMapView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_launcher));
    }
}
