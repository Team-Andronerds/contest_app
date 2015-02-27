package andronerds.com.contestapp.data;

import java.io.Serializable;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/26/15
 */
public class UserProfile implements Serializable
{
    private String mName;
    private String mPhotoUrl;
    private String mGPlusProfile;
    private String mEmail;

    public UserProfile() {}

    public String getName()
    {
        return mName;
    }

    public String getPhotoUrl()
    {
        return mPhotoUrl;
    }

    public String getGPlusProfile()
    {
        return mGPlusProfile;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public void setName(String mName)
    {
        this.mName = mName;
    }

    public void setPhotoUrl(String mPhotoUrl)
    {
        this.mPhotoUrl = mPhotoUrl;
    }

    public void setGPlusProfile(String mGPlusProfile)
    {
        this.mGPlusProfile = mGPlusProfile;
    }

    public void setEmail(String mEmail)
    {
        this.mEmail = mEmail;
    }
}
