package andronerds.com.contestapp.fragments.editProfile;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import andronerds.com.contestapp.MyVehicleActivity;
import andronerds.com.contestapp.R;
import andronerds.com.contestapp.data.User;
import andronerds.com.contestapp.utils.IdentityStrings;
import andronerds.com.contestapp.utils.IntUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Chris on 3/17/2015.
 */
public class EditPictureFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.change_picture_gallery_button)Button mEditFromGallery;
    @InjectView(R.id.change_picture_camera_button)Button mEditTakePic;

    private String prevActionBarTitle;

    public boolean didPicture = false;
    public boolean didCamera = false;
    public static String path;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_picture, container, false);
        ButterKnife.inject(this, view);
        mEditFromGallery.setOnClickListener(this);
        mEditTakePic.setOnClickListener(this);

        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        this.prevActionBarTitle = myV.getSupportActionBar().getTitle().toString();
        myV.getSupportActionBar().setTitle("Change Picture");


        return view;
    }

    @Override
    public void onClick(View v)
    {
        Button button = (Button) v;

        if(button == mEditFromGallery)
        {
            didPicture = true;
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getActivity().startActivityForResult(i, IntUtils.RESULT_LOAD_IMAGE);
        }
        else if(button == mEditTakePic)
        {
            didCamera = true;
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getActivity().getPackageManager()) != null) {

                getActivity().startActivityForResult(i, IntUtils.USE_CAMERA);
            }
            else
            {
                Log.d("Intent for camera","FAILED");
            }

        }


    }

   private String saveToInternalStorage(Bitmap bitmapImage){



        SharedPreferences shPref = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);

        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        String userName = shPref.getString(IdentityStrings.USER_NAME,"");
        File directory = cw.getDir("imageDir" + userName, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(didPicture)
        {
            SharedPreferences shPref = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmapImage = BitmapFactory.decodeFile(shPref.getString(IdentityStrings.USER_PROFILE_PIC, ""), bmOptions);
            PictureThread pThread = new PictureThread();
            pThread.execute(bitmapImage);
            didPicture = false;

        }
        else if(didCamera)
        {

        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        MyVehicleActivity myV = (MyVehicleActivity) getActivity();
        myV.getSupportActionBar().setTitle(prevActionBarTitle);
    }

    public void saveCameraImage(Bitmap bitmap)
    {

        SharedPreferences shPref = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
        SharedPreferences.Editor editor = shPref.edit();
        editor.putString(IdentityStrings.USER_PROFILE_PIC, saveToInternalStorage(bitmap));
        editor.commit();
        Log.d("SAVE PIC TEST 2", shPref.getString(IdentityStrings.USER_PROFILE_PIC, ""));
        List<User> user = User.find(User.class,"name = ?",shPref.getString(IdentityStrings.USER_NAME,""));
        user.get(0).setProfileImage(shPref.getString(IdentityStrings.USER_PROFILE_PIC, ""));
        user.get(0).save();

        didCamera = false;
        getActivity().onBackPressed();
    }


    private class PictureThread extends AsyncTask<Bitmap,Void,Void> {

        private ProgressDialog dialog;

        protected void onPreExecute()
        {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading Picture");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected Void doInBackground(Bitmap...bitmapImage) {
            //Looper.prepare();
            SharedPreferences shPref = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);

            String path = shPref.getString(IdentityStrings.USER_PROFILE_PIC, "");
            File tempDir = new File(path);

            bitmapImage[0] = Bitmap.createScaledBitmap(bitmapImage[0], bitmapImage[0].getWidth() / 2, bitmapImage[0].getHeight() / 2, false);

            try {
                ExifInterface exif = new ExifInterface(tempDir.getAbsolutePath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bitmapImage[0] = Bitmap.createBitmap(bitmapImage[0], 0, 0, bitmapImage[0].getWidth(), bitmapImage[0].getHeight(), matrix, true); // rotating bitmap
            } catch (Exception e) {

            }

            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            String userName = shPref.getString(IdentityStrings.USER_NAME, "");
            File directory = cw.getDir("imageDir" + userName, Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = new File(directory, "profile.jpg");

            FileOutputStream fos = null;
            try {

                fos = new FileOutputStream(mypath);

                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage[0].compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            EditPictureFragment.path = directory.getAbsolutePath();
            return null;
        }

        protected void onPostExecute(Void v)
        {
            SharedPreferences shPref = getActivity().getSharedPreferences(IdentityStrings.SHARE_PREF_USER_PROF, 0);
            SharedPreferences.Editor editor = shPref.edit();
            editor.putString(IdentityStrings.USER_PROFILE_PIC, EditPictureFragment.path);
            editor.commit();
            Log.d("SAVE PIC TEST 2", shPref.getString(IdentityStrings.USER_PROFILE_PIC, ""));
            List<User> user = User.find(User.class,"name = ?",shPref.getString(IdentityStrings.USER_NAME,""));
            user.get(0).setProfileImage(shPref.getString(IdentityStrings.USER_PROFILE_PIC, ""));
            user.get(0).save();
            dialog.dismiss();
            getActivity().onBackPressed();
        }
    }
}
