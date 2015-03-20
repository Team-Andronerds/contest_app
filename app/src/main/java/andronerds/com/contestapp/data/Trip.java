package andronerds.com.contestapp.data;

import java.util.HashMap;

/**
 * @author Aaron Weaver         (waaronl@okstate.edu)
 * @version ContestApp v1.0
 * @since 2/22/15
 */
public class Trip extends User
{
    private String mTripStart;
    private String mTripEnd;

    private LatLng mTripStartLatLng;
    private LatLng mTripEndLatLng;

    private int mTripMap;
    private HashMap<String, String> mTripIssues;
    private int mTripMileage = 0;
    private int mHarshBrakeCount = 0;
    private int mSpeedingCount = 0;
    private int mLowGasCount = 0;
    private int mHarshAccelCount = 0;
    private int mHarshTurnCount = 0;
    private int mPoints = 0;

    public Trip() {}

    public Trip(String mTripStart, String mTripEnd, int mTripMap, HashMap<String, String> mTripIssues,int mileage,int brakeCount,int speedingCount, int gasCount,
                int accelCount, int turnCount, int points, String userName)
    {
        this.mTripStart = mTripStart;
        this.mTripEnd = mTripEnd;
        this.mTripMap = mTripMap;
        this.mTripIssues = mTripIssues;
        this.mHarshAccelCount = accelCount;
        this.mHarshBrakeCount = brakeCount;
        this.mHarshTurnCount = turnCount;
        this.mPoints = points;
        this.mLowGasCount = gasCount;
        this.mSpeedingCount = speedingCount;
        this.mTripMileage = mileage;
        this.setName(userName);
    }

    public Trip(String mTripStart, String mTripEnd, int mTripMap, HashMap<String, String> mTripIssues)
    {
        this.mTripStart = mTripStart;
        this.mTripEnd = mTripEnd;
        this.mTripMap = mTripMap;
        this.mTripIssues = mTripIssues;
    }

    public Trip(String mTripStart, String mTripEnd, int mTripMap, HashMap<String, String> mTripIssues, String userName)
    {
        this.mTripStart = mTripStart;
        this.mTripEnd = mTripEnd;
        this.mTripMap = mTripMap;
        this.mTripIssues = mTripIssues;
        this.setName(userName);
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

    public int getTripMileage() {return this.mTripMileage;}

    public int getHarshBrakeCount() {return this.mHarshBrakeCount;}

    public int getHarshTurnCount() {return this.mHarshTurnCount;}

    public int getSpeedingCount() {return this.mSpeedingCount;}

    public int getLowGasCount() {return this.mLowGasCount;}

    public int getHarshAccelCount() {return this.mHarshAccelCount;}

    public int getPoints() {return this.mPoints;}

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

    public void setTripMileageCount(int miles){this.mTripMileage = miles;}

    public void setHarshBrakeCount(int brakes){this.mHarshBrakeCount = brakes;}

    public void setHarshTurnCount(int turns){this.mHarshTurnCount = turns;}

    public void setHarshAccelCount(int accels){this.mHarshAccelCount = accels;}

    public void setSpeedingCount(int speedingCount){this.mSpeedingCount = speedingCount;}

    public void setLowGasCount(int lowGas){this.mLowGasCount = lowGas;}

    public void setPoints(int points){this.mPoints = points;}
}
