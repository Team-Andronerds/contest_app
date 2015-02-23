package andronerds.com.contestapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import andronerds.com.contestapp.fragments.LoginFragment;
import butterknife.ButterKnife;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/22/15
 */
public class LoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment driveFragment = new LoginFragment();
        fragmentTransaction.add(R.id.login_fragment_container, driveFragment);
        fragmentTransaction.commit();

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintColor(this.getResources().getColor(R.color.toolbar_color));
        tintManager.setStatusBarTintEnabled(true);
    }
}
