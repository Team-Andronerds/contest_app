package andronerds.com.contestapp.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Trip;
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
    private Trip mTrip;

    @InjectView(R.id.trip_map)ImageView mTripMapView;
    @InjectView(R.id.more_details_image)ImageView mMoreDetailsImage;

    public MyTripsTripCard(Context context, Trip trip)
    {
        super(context, R.layout.card_my_trips_trip);
        this.mTrip = trip;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);

        //mTripMapView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.map_placeholder));

        Picasso.with(this.getContext())
                .load(mTrip.getmTripMap())
                .fit()
                .into(mTripMapView);

        Picasso.with(this.getContext())
                .load(R.drawable.ic_right_white_arrow)
                .centerInside()
                .fit()
                .into(mMoreDetailsImage);
    }

    public Trip getTrip()
    {
        return this.mTrip;
    }
}
