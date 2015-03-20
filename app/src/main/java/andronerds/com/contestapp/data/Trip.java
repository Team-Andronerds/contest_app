package andronerds.com.contestapp.data;

import com.google.android.gms.maps.model.LatLng;
import com.orm.SugarRecord;

import java.util.HashMap;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/22/15
 */
public class Trip extends SugarRecord<Trip>
{
    private String mTripStart;
    private String mTripEnd;

    private LatLng mTripStartLatLng;
    private LatLng mTripEndLatLng;

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

    public LatLng getmTripStartLatLng()
    {
        return mTripStartLatLng;
    }

    public LatLng getmTripEndLatLng()
    {
        return mTripEndLatLng;
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

    public void setmTripStartLatLng(LatLng mTripStartLatLng)
    {
        this.mTripStartLatLng = mTripStartLatLng;
    }

    public void setmTripEndLatLng(LatLng mTripEndLatLng)
    {
        this.mTripEndLatLng = mTripEndLatLng;
    }
}
