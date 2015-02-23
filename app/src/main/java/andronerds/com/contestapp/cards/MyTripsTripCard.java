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
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/20/15
 */
public class MyTripsTripCard extends Card
{
    @InjectView(R.id.trip_map)ImageView mTripMapView;
    @InjectView(R.id.more_details_image)ImageView mMoreDetailsImage;

    public MyTripsTripCard(Context context)
    {
        super(context, R.layout.card_my_trips_trip);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);

        //mTripMapView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.map_placeholder));

        Picasso.with(this.getContext())
                .load(R.drawable.map_placeholder)
                .fit()
                .into(mTripMapView);

        /*Picasso.with(this.getContext())
                .load(R.drawable.ic_chevron_right_grey600_24dp)
                .centerInside()
                .fit()
                .into(mMoreDetailsImage);*/
    }
}
