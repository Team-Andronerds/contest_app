package andronerds.com.contestapp.cards;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.utils.IdentityStrings;
import andronerds.com.contestapp.utils.PictureUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 1/29/15
 */
public class HomeDriveCard extends Card
{
    @InjectView(R.id.drive_card_image)CircleImageView driveCardImage;
    @InjectView(R.id.drive_card_points_text) TextView driveCardText;
    @InjectView(R.id.home_card_progress)ProgressBar progBar;
    @InjectView(R.id.drive_card_current_level)TextView mCurrentLevel;

    private int mCurrLevel;
    private int mCurrTotalPoints;
    private int mNeededPoints;
    private int mTotalPoints;
    public HomeDriveCard(Context context)
    {
        super(context, R.layout.card_home_drive);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);
        ButterKnife.inject(this, view);
        SharedPreferences shPref = getContext().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        boolean usingGooglePlus = shPref.getBoolean(IdentityStrings.USER_IS_GOOGLE_PLUS, false);
        String userName = shPref.getString(IdentityStrings.USER_NAME, "");

        int profilePicInt = 0;
        String profilePicString = "";

        try {
            profilePicInt = Integer.parseInt(shPref.getString(IdentityStrings.USER_PROFILE_PIC, ""));
        } catch (NumberFormatException e) {
            System.out.println("Wrong number");
            profilePicString = shPref.getString(IdentityStrings.USER_PROFILE_PIC, "");
        }

        if(profilePicInt != 0)
        {
            Picasso.with(this.getContext())
                    .load(profilePicInt)
                    .fit()
                    .into(driveCardImage);
        }
        else if(!profilePicString.equals(""))
        {
            if(usingGooglePlus) {
                Picasso.with(this.getContext())
                        .load(profilePicString)
                        .fit()
                        .into(driveCardImage);
            }
            else
            {
                driveCardImage.setImageBitmap(PictureUtil.loadImageFromStorage(profilePicString));
            }
        }
        else
        {
            Picasso.with(this.getContext())
                    .load(R.drawable.ic_profile_null)
                    .fit()
                    .into(driveCardImage);
        }

        driveCardImage.setBorderColor(getContext().getResources().getColor(R.color.toolbar_color));
        driveCardImage.setBorderWidth(3);



        mCurrTotalPoints = getTotalPoints(userName);
        mCurrLevel = calcLevel(mCurrTotalPoints);
        mTotalPoints = calcCurrPoints();
        mNeededPoints = mCurrLevel * 100;

        int progress = (mTotalPoints * 100) /  mNeededPoints;
        progBar.setIndeterminate(false);
        progBar.setProgress(progress);
        driveCardText.setText(mTotalPoints + " / " + mNeededPoints);
        mCurrentLevel.setText("Level " + mCurrLevel);


    }


    public int getTotalPoints(String userName)
    {

        List<Trip> trips = Trip.find(Trip.class,"name = ?",userName);

        Log.d("TRIP SIZE", Integer.toString(trips.size()));

        if(trips.size() != 0)
        {
            int total = 0;
            for(Trip trip : trips)
            {
                total += trip.getPoints();
            }

            Log.d("TRIP TEST", Integer.toString(total));
            return total;
        }
        else
        {
            return 0;
        }
    }

    public int calcLevel(int points)
    {
        if(points >= 100) {
            int level = 0;
            for(int i = 0; points >= 0; i++)
            {
                points -= 100 * i;
                level = i;
            }

            Log.d("MATH TEST", Integer.toString(level));

            return level;
        }
        else
        {
            return 1;
        }
    }

    public int calcCurrPoints()
    {
        if(mCurrLevel > 1) {
            int points = 0;
            for(int i = mCurrLevel-1; i > 0; i--)
            {
                points += i * 100;
            }
            Log.d("MATH CURR LEVEL PROG",Integer.toString(points));
            return mCurrTotalPoints - points;
        }
        else
        {
            return mCurrTotalPoints;
        }
    }

}
