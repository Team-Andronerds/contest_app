package andronerds.com.contestapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by Chris on 3/18/2015.
 */

public class PictureUtil
{
    public static Bitmap loadImageFromStorage(String path)
    {
        File image = new File(path,"profile.jpg");
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);

        return bitmap;
    }
}
