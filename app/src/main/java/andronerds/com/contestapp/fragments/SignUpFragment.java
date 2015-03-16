package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import andronerds.com.contestapp.LoginActivity;
import andronerds.com.contestapp.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/8/15
 */
public class SignUpFragment extends Fragment implements View.OnClickListener
{
    @InjectView(R.id.sign_up_logo)ImageView mLogo;
    @InjectView(R.id.sign_up_email)EditText mEmail;
    @InjectView(R.id.sign_up_username)EditText mUsername;
    @InjectView(R.id.sign_up_password)EditText mPassword;
    @InjectView(R.id.sign_up_confirm_password)EditText mConfirmPassword;
    @InjectView(R.id.sign_up_button)Button mSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.inject(this, view);

        Picasso.with(getActivity())
                .load(R.drawable.pc_login_logo)
                .fit()
                .into(mLogo);

        mSignUp.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        int id = v.getId();
        if(id == mSignUp.getId())
        {
            if(mPassword.getText().toString().equals(mConfirmPassword.getText().toString()))
            {
                loginActivity.signUpProcess(mEmail.getText().toString(),
                        mUsername.getText().toString(), mPassword.getText().toString());
            }
        }
    }
}
