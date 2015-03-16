package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.SignInButton;
import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.LoginActivity;
import andronerds.com.contestapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/22/15
 */
public class LoginFragment extends Fragment implements View.OnClickListener
{
    @InjectView(R.id.login_logo)ImageView mLoginLogo;
    @InjectView(R.id.sign_in_button)SignInButton mGPlusSignIn;
    @InjectView(R.id.login_username)EditText mLoginUsername;
    @InjectView(R.id.login_password)EditText mLoginPassword;
    @InjectView(R.id.login_sign_in)Button mLoginSignIn;
    @InjectView(R.id.login_sign_up)Button mLoginSignUp;

    private static final String LOG_TAG = "LOGIN FRAGMENT";

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
        mGPlusSignIn.setOnClickListener(this);
        mLoginSignUp.setOnClickListener(this);
        mLoginSignIn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        LoginActivity loginActivity = (LoginActivity) this.getActivity();
        Log.d(LOG_TAG, "GPlus button clicked");

        if(v.getId() == mGPlusSignIn.getId()
                && !loginActivity.getGoogleServices().isConnecting())
        {
            Log.d(LOG_TAG, "Attempting to sign in to GPlus");
            loginActivity.setSignInClicked(true);
            loginActivity.resolveSignInError();
        }
        else if(v.getId() == mLoginSignUp.getId())
        {
            Log.d(LOG_TAG, "Sign up clicked");
            loginActivity.switchSignUpFragment();
        }
        else if(v.getId() == mLoginSignIn.getId())
        {
            Log.d(LOG_TAG, "Sign in clicked");
            loginActivity.signInProcess(mLoginUsername.getText().toString(),
                    mLoginPassword.getText().toString());
        }
    }
}
