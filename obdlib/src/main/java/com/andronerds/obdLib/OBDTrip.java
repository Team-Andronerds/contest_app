package com.andronerds.obdLib;

/**
 * Created by Dilancuan on 1/26/2015.
 */
class OBDTrip {
    private String testString;
    public OBDTrip(String s){
        testString = s;
    }

    public void start(){
        System.out.println(testString);
    }

    public void stop(){

    }
}
