package andronerds.com.contestapp.data;

import java.util.HashMap;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/22/15
 */
public class Trip
{
    private String mTripStart;
    private String mTripEnd;
    private int mTripMap;
    private HashMap<String, String> mTripIssues;

    public Trip() {}

    public Trip(String mTripStart, String mTripEnd, int mTripMap, HashMap<String, String> mTripIssues)
    {
        this.mTripStart = mTripStart;
        this.mTripEnd = mTripEnd;
        this.mTripMap = mTripMap;
        this.mTripIssues = mTripIssues;
    }

    public String getmTripStart()
    {
        return mTripStart;
    }

    public String getmTripEnd()
    {
        return mTripEnd;
    }

    public int getmTripMap()
    {
        return mTripMap;
    }

    public HashMap<String, String> getmTripIssues()
    {
        return mTripIssues;
    }

    public void setmTripStart(String mTripStart)
    {
        this.mTripStart = mTripStart;
    }

    public void setmTripEnd(String mTripEnd)
    {
        this.mTripEnd = mTripEnd;
    }

    public void setmTripMap(int mTripMap)
    {
        this.mTripMap = mTripMap;
    }

    public void setmTripIssues(HashMap<String, String> mTripIssues)
    {
        this.mTripIssues = mTripIssues;
    }
}
