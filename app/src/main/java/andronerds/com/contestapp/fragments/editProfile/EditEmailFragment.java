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
public class EditEmailFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.edit_email_button)Button mEditButton;
    @InjectView(R.id.edit_text_email)EditText mEmailEditText;

    private String prevActionBarTitle;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_email, container, false);
        ButterKnife.inject(this, view);
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        this.prevActionBarTitle = myV.getSupportActionBar().getTitle().toString();
        myV.getSupportActionBar().setTitle("Change E-Mail");

        //this.prevActionBarTitle = getActivity().getActionBar().getTitle().toString();
        //getActivity().getActionBar().setTitle("Change E-Mail");
        mEditButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        String email = mEmailEditText.getText().toString();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        String profileName = sharedPreferences.getString(IdentityStrings.USER_NAME,"");
        List<User> users = User.find(User.class, "name = ?", profileName);

        if(!email.equals("") && isValidEmailAddress(email))
        {
            List<User> findEmail = User.find(User.class,"email = ?",email );
            if(users.size() > 0 && findEmail.size() < 1) {

               users.get(0).setEmail(email);
               users.get(0).save();

               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString(IdentityStrings.USER_EMAIL, email);
               editor.commit();
               getActivity().onBackPressed();

            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),"E-Mail is taken",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this.getActivity().getApplicationContext(),"Not a Valid E-Mail Address",Toast.LENGTH_LONG).show();
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        myV.getSupportActionBar().setTitle(this.prevActionBarTitle);
    }

}
