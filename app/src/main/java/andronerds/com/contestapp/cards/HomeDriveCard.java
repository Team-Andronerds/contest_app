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
 * @since 1/29/15
 */
public class HomeDriveCard extends Card
{
    @InjectView(R.id.drive_card_image) ImageView driveCardImage;
    @InjectView(R.id.drive_card_points_text) TextView driveCardText;

    public HomeDriveCard(Context context)
    {
        super(context, R.layout.home_drive_card);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);

        driveCardImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_launcher));
    }
}
