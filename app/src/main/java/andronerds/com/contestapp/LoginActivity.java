package andronerds.com.contestapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.HashMap;

import andronerds.com.contestapp.fragments.LoadingFragment;
import andronerds.com.contestapp.fragments.LoginFragment;
import andronerds.com.contestapp.utils.IdentityStrings;
import butterknife.ButterKnife;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/22/15
 */
public class LoginActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult>
{
    private GoogleApiClient mGoogleServices;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private static final int RC_SIGN_IN = 0;

    private ProgressDialog progressDialog;

    private static final int PROFILE_PIC_SIZE = 300;
    private HashMap<String, String> friendsList;
    private boolean signOut = false;

    private static final String LOG_TAG = "GPLUS_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoadingFragment loadingFragment = new LoadingFragment();
        fragmentTransaction.replace(R.id.login_fragment_container, loadingFragment);
        fragmentTransaction.commit();

        Intent intent = getIntent();
        Bundle bundle = null;

        if(intent != null)
        {
            bundle = intent.getExtras();
            if(bundle != null)
            {
                String signOutStr = bundle.getString(IdentityStrings.BUNDLE_SIGN_OUT);
                if(signOutStr != null)
                {
                    if (signOutStr.equals(IdentityStrings.BUNDLE_SIGN_OUT))
                    {
                        signOut = true;
                    } else
                    {
                        signOut = false;
                    }
                }
            }
        }

        mGoogleServices = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintColor(this.getResources().getColor(R.color.toolbar_color));
        tintManager.setStatusBarTintEnabled(true);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mGoogleServices.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleServices.isConnected()) {
            mGoogleServices.disconnect();
        }
    }

    public void resolveSignInError()
    {
        progressDialog = ProgressDialog.show(this, "Signing in",
                "Signing you in...", true);

        if(mConnectionResult.hasResolution())
        {
            try
            {
                mIntentInProgress = true;
                startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch(IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleServices.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        //fragmentTransaction.commit();

        Log.i("CONNECT FAILED", "Connection to GPlus failed");

        if(progressDialog != null)
        {
            progressDialog.dismiss();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.login_fragment_container, loginFragment);
        fragmentTransaction.commit();

        if(!mIntentInProgress)
        {
            mConnectionResult = result;
        }
        if(mSignInClicked)
        {
            resolveSignInError();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleServices.isConnecting()) {
                mGoogleServices.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mSignInClicked = false;
        Plus.PeopleApi.loadVisible(mGoogleServices, null).setResultCallback(this);
        addProfileToSharedPrefs();
        if(signOut)
        {
            signOutGPlus();
            signOut = false;
        }
        else
        {
            if(progressDialog != null)
                progressDialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    public void signOutGPlus()
    {
        Log.d("SIGN_OUT", "Sign out occured");
        if(mGoogleServices.isConnected())
        {
            Log.d("SIGN_OUT", "Google services signing out");
            Plus.AccountApi.clearDefaultAccount(mGoogleServices);
            mGoogleServices.disconnect();
            removeProfileFromSharedPrefs();
            mGoogleServices.connect();
        }
    }

    public void setSignInClicked(boolean signInClicked) { this.mSignInClicked = signInClicked; }

    public GoogleApiClient getGoogleServices() { return this.mGoogleServices; }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        friendsList = new HashMap<String, String>();
        if(loadPeopleResult.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();

            try {
                int count = personBuffer.getCount();

                for (int i = 0; i < count; i++) {
                    friendsList.put(personBuffer.get(i).getDisplayName(), personBuffer.get(i).getImage().getUrl());
                }
            } finally {
                personBuffer.close();
            }
        } else {
            Log.e(LOG_TAG, "Error requesting data..." + loadPeopleResult.getStatus());
        }
    }

    public void removeProfileFromSharedPrefs()
    {
        SharedPreferences settings = getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void addProfileToSharedPrefs()
    {
        try {
            if(Plus.PeopleApi.getCurrentPerson(mGoogleServices) != null) {
                Person mCurrentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleServices);
                String personName = mCurrentPerson.getDisplayName();
                String personPhotoUrl = mCurrentPerson.getImage().getUrl();
                String personGooglePlusProfile = mCurrentPerson.getUrl();
                final String personEmail = Plus.AccountApi.getAccountName(mGoogleServices);

                Log.i(LOG_TAG, "Person Name: " + personName);
                Log.i(LOG_TAG, "Person Photo URL: " + personPhotoUrl);
                Log.i(LOG_TAG, "Person G+ Profile: " + personGooglePlusProfile);
                Log.i(LOG_TAG, "Person E-Mail: " + personEmail);

                SharedPreferences settings = getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString(IdentityStrings.USER_NAME, personName);
                editor.putString(IdentityStrings.USER_PROFILE_PIC, personPhotoUrl);
                editor.putString(IdentityStrings.USER_G_PLUS_PROFILE, personGooglePlusProfile);
                editor.putString(IdentityStrings.USER_EMAIL, personEmail);
                editor.commit();

                personPhotoUrl = personPhotoUrl.substring(0, personPhotoUrl.length() -2) + PROFILE_PIC_SIZE;
                Log.i(LOG_TAG, "Photo URL Length: " + personPhotoUrl.length());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}