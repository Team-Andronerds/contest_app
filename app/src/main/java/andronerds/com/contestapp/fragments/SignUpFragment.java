package andronerds.com.contestapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import andronerds.com.contestapp.LoginActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.User;
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

    public Toast buildToast(String message)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_custom, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
        TextView toastText = (TextView) view.findViewById(R.id.toast_text);
        toastText.setText(message);
        Toast toast = new Toast(getActivity());
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        return toast;
    }

    @Override
    public void onClick(View v)
    {
        LoginActivity loginActivity = (LoginActivity) getActivity();

        int id = v.getId();
        if(id == mSignUp.getId())
        {
            boolean isValidAccount = true;
            boolean toastShown = false;
            Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher m = p.matcher(mEmail.getText().toString());
            if(!m.matches())
            {
                isValidAccount = false;
                if(!toastShown)
                {
                    buildToast("Invalid email address").show();
                    toastShown = true;
                }
            }
            if(!User.find(User.class, "email = ?", mEmail.getText().toString()).isEmpty())
            {
                isValidAccount = false;
                if(!toastShown)
                {
                    buildToast("That email address has been taken").show();
                    toastShown = true;
                }
            }
            if(!User.find(User.class, "name = ?", mUsername.getText().toString()).isEmpty())
            {
                isValidAccount = false;
                if(!toastShown)
                {
                    buildToast("That username has been taken").show();
                    toastShown = true;
                }
            }
            if(!mPassword.getText().toString().equals(mConfirmPassword.getText().toString()))
            {
                isValidAccount = false;
                if(!toastShown)
                {
                    buildToast("Passwords entered do not match").show();
                    toastShown = true;
                }
            }
            if(mPassword.getText().toString().isEmpty())
            {
                isValidAccount = false;
                if(!toastShown)
                {
                    buildToast("No password entered").show();
                    toastShown = true;
                }
            }
            if(mEmail.getText().toString().isEmpty())
            {
                isValidAccount = false;
                if(!toastShown)
                {
                    buildToast("No email entered").show();
                    toastShown = true;
                }
            }
            if(mUsername.getText().toString().isEmpty())
            {
                isValidAccount = false;
                if(!toastShown)
                {
                    buildToast("No username entered").show();
                    toastShown = true;
                }
            }

            if(isValidAccount)
            {
                buildToast("Your account has been created").show();
                loginActivity.signUpProcess(mEmail.getText().toString(),
                        mUsername.getText().toString(), mPassword.getText().toString());
            }
        }
    }
}