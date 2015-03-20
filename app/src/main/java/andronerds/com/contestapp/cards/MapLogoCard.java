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
 * @version ContestApp v1.0
 * @since 3/19/15
 */
public class MapLogoCard extends Card
{
    @InjectView(R.id.maps_logo)ImageView mMapsLogo;

    public MapLogoCard(Context context)
    {
        super(context, R.layout.card_trip_info);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);

        Picasso.with(getContext())
                .load(R.drawable.ic_maps_logo)
                .fit()
                .into(mMapsLogo);
    }
}
