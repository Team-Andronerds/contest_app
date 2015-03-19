package andronerds.com.contestapp.fragments.insurance;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andronerds.com.contestapp.R;
import butterknife.ButterKnife;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v0.1A
 * @since 2/15/15
 */
public class InsuranceInfoFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_insurance_info, container, false);
        ButterKnife.inject(this, view);
        return view;
    }
}
