package andronerds.com.contestapp.utils;

import andronerds.com.contestapp.data.UserProfile;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/26/15
 */
public class ProfileUtils
{
    private static UserProfile userProfile;

    public static void setUserProfile(UserProfile newUserProfile) { userProfile = newUserProfile; }

    public static UserProfile getUserProfile() { return userProfile; }
}
