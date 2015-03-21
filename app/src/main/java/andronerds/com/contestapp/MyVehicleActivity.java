package andronerds.com.contestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import andronerds.com.contestapp.obd.OnBoardDiagnostic;
import andronerds.com.contestapp.fragments.MyVehicleFragment;
import andronerds.com.contestapp.fragments.editProfile.EditPictureFragment;
import andronerds.com.contestapp.navDrawer.NavDrawerActivity;
import andronerds.com.contestapp.utils.IdentityStrings;
import andronerds.com.contestapp.utils.IntUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0A
 * @since 2/19/15
 */
public class MyVehicleActivity extends NavDrawerActivity
{
    private CharSequence mTitle = "My Vehicle";

    @InjectView(R.id.my_vehicle_toolbar)Toolbar mToolbar;

    public static boolean didHit = false;

    @Override
    protected Toolbar init()
    {
        setContentView(R.layout.activity_my_vehicle);
        ButterKnife.inject(this);

        if(OnBoardDiagnostic.isActive()){
            try{
                JSONObject job = new JSONObject();
                job.put("Purpose","CAR");
                OnBoardDiagnostic.sendMessage(job);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyVehicleFragment myVehicleFragment = new MyVehicleFragment();
        fragmentTransaction.add(R.id.my_vehicle_fragment_container, myVehicleFragment);
        fragmentTransaction.commit();

        //mToolbar.showOverflowMenu();
        mToolbar.setTitle("View Profile");
        setSupportActionBar(mToolbar);

        return mToolbar;
    }

    public Toolbar getToolbar() { return mToolbar;}

    @Override
    public void onBackPressed()
    {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("CAMERA TEST", data.toString());
        didHit = true;
        if (requestCode == IntUtils.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            SharedPreferences shPref = getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF,0);
            SharedPreferences.Editor editor = shPref.edit();
            editor.putString(IdentityStrings.USER_PROFILE_PIC,picturePath);
            editor.commit();
        }
        else if(requestCode == IntUtils.USE_CAMERA && resultCode == RESULT_OK && null != data)
        {
            Log.d("CAMERA TEST", data.toString());
            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            Bitmap b = (Bitmap)data.getExtras().get("data");

            if(b != null) {
                EditPictureFragment pictureFrag = (EditPictureFragment) getFragmentManager().findFragmentByTag("PICS");
                pictureFrag.saveCameraImage(b);
            }
            else
            {
                Log.d("CAMERA", "BITMAP IS NULL");
            }


            /*b.compress(Bitmap.CompressFormat.PNG, 200);
            byte[] bArray = byteArrayBitmapStream.toByteArray();

            Bundle extras = new Bundle();
            extras.putByteArray("camera", bArray);
            EditPictureFragment pictureFrag = (EditPictureFragment)getFragmentManager().findFragmentByTag("PICS");
            pictureFrag.setArguments(extras);*/
            //mImageView.setImageBitmap(imageBitmap);
        }
    }
}
