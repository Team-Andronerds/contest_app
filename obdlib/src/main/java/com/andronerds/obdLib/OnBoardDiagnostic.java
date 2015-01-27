package com.andronerds.obdLib;

/**
 * Created by Dilancuan on 1/26/2015.
 */
public final class OnBoardDiagnostic {
    private static OBDTrip trip;

    private OnBoardDiagnostic(){

    }

    public static String getString(){
        return "yo";
    }

    public static void pair(){

    }

    public static void newTrip(String s){
        trip = new OBDTrip(s);
    }

    public static void startTrip(){
        trip.start();
    }
}
