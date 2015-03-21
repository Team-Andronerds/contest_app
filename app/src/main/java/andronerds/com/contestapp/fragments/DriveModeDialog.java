package andronerds.com.contestapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import andronerds.com.contestapp.MyTripsActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.Trip;
import andronerds.com.contestapp.obd.OnBoardDiagnostic;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Alexander on 3/19/2015.
 */
public class DriveModeDialog extends DialogFragment implements View.OnClickListener{

    int pointsEarned;
    int level;
    int temp;

    @InjectView(R.id.pointsMeter)ProgressBar mBar;
    @InjectView(R.id.pointValue)TextView addedPoints;
    @InjectView(R.id.nextLevel)TextView nextLevel;
    @InjectView(R.id.currentLevel)TextView currentLevel;
    @InjectView(R.id.cancel_button)Button cancelButt;
    @InjectView(R.id.view_trips_button)Button viewButt;

    public DriveModeDialog(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_summary_dialog, container, false);
        ButterKnife.inject(this, view);
        cancelButt.setOnClickListener(this);
        viewButt.setOnClickListener(this);
        pointsEarned = OnBoardDiagnostic.getmTrip().getPoints();



        temp = pointsEarned;
        mBar.setProgress(50);
        mBar.setMax(100);
        addedPoints.setText("+" + pointsEarned);


        new AnimatorClass().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void onClick(View v) {

        Trip mTrip = OnBoardDiagnostic.getmTrip();
        SharedPreferences sp = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        String user = sp.getString(IdentityStrings.USER_NAME, "");
        mTrip.setName(user);
        mTrip.save();

        switch (v.getId()) {
            case R.id.cancel_button:
                this.dismiss();
                break;
            case R.id.view_trips_button:

                OnBoardDiagnostic.setTripViw(true);
                Intent intent = new Intent(getActivity().getApplicationContext(), MyTripsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                startActivity(intent);
                this.dismiss();
                break;
        }
    }
    public class AnimatorClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (mBar.getProgress() < pointsEarned) {
                publishProgress();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mBar.incrementProgressBy(1);
            temp--;
            addedPoints.setText("+" + temp);
            Log.d("Progress",mBar.getProgress() + "");
            mBar.refreshDrawableState();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(temp != 0){

                currentLevel.setText("2");
                nextLevel.setText("3");


                level++;
                mBar.setProgress(0);
                mBar.setMax(100);
                pointsEarned = temp;
                new AnimatorClass().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            }
        }
    }

}
