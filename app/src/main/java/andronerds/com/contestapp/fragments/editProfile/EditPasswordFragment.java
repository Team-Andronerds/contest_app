package andronerds.com.contestapp.fragments.editProfile;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.User;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Chris on 3/17/2015.
 */
public class EditPasswordFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.edit_text_password)EditText mEditTextPassword;
    @InjectView(R.id.edit_text_confirm_password)EditText mEditTextConfirmPassword;
    @InjectView(R.id.change_password_button)Button mChangePasswordButton;
    private String prevActionBarTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
        ButterKnife.inject(this, view);
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        this.prevActionBarTitle = myV.getSupportActionBar().getTitle().toString();
        myV.getSupportActionBar().setTitle("Change Password");
        mChangePasswordButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        String password = mEditTextPassword.getText().toString();
        String confirmPassword = mEditTextConfirmPassword.getText().toString();

        if(password.equals(confirmPassword) && password.length() > 0)
        {
                SharedPreferences sPref = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF,0);
                List<User> user = User.find(User.class,"name = ?", sPref.getString(IdentityStrings.USER_NAME,""));

                if(user.size() != 0)
                {
                    //TODO dont store as plain text
                    user.get(0).setPassword(password);
                    user.get(0).save();
                }

                getActivity().onBackPressed();
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"Passwords Do Not Match",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStop()
    {
        super.onStop();
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        myV.getSupportActionBar().setTitle(this.prevActionBarTitle);
    }
}
