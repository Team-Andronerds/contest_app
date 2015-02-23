package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.SignInButton;
import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/22/15
 */
public class LoginFragment extends Fragment
{
    @InjectView(R.id.login_logo)ImageView mLoginLogo;
    @InjectView(R.id.sign_in_button)SignInButton mGPlusSignIn;
    @InjectView(R.id.login_username)EditText mLoginUsername;
    @InjectView(R.id.login_password)EditText mLoginPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        Picasso.with(this.getActivity())
                .load(R.drawable.pc_login_logo)
                .fit()
                .into(mLoginLogo);

        mGPlusSignIn.setSize(SignInButton.SIZE_WIDE);

        return view;
    }
}
