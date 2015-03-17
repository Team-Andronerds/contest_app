package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/15/15
 */
public class EmergencyFragment extends Fragment implements View.OnClickListener
{
    @InjectView(R.id.emergency_icon)ImageView mEmergencyIcon;
    @InjectView(R.id.emergency_button)Button mEmergencyCallButton;
    @InjectView(R.id.accident_information_button)Button mAccidentReportButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.inject(this, view);

        Picasso.with(this.getActivity())
                .load(R.drawable.ic_emergency)
                .fit()
                .into(mEmergencyIcon);

        mEmergencyCallButton.setOnClickListener(this);
        mAccidentReportButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if(id == mEmergencyCallButton.getId())
        {

            String uri = "tel:" + "911" ;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }
        else if(id == mAccidentReportButton.getId())
        {

        }
    }
}
