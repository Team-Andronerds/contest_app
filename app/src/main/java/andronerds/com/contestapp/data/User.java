package andronerds.com.contestapp.data;

import com.orm.SugarRecord;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 3/8/15
 */
public class User extends SugarRecord<User>
{
    private String name;
    private String email;
    private String profileImage;
    private String hashedPassword;
    private String salt;

    public User()
    {

    }

    public User(String name, String email,
                String profileImage, String hashedPassword, String salt)
    {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setProfileImage(String profileImage)
    {
        this.profileImage = profileImage;
    }

    public void setHashedPassword(String hashedPassword)
    {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }
}
