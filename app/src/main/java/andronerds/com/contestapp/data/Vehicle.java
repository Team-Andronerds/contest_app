package andronerds.com.contestapp.data;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * Created by Chris on 3/16/2015.
 */
public class Vehicle extends User {

    private Color color;
    private Drawable carImage;
    private String make;
    private String model;
    private String vin;
    private String year;


    public Vehicle()
    {

    }

    public Vehicle(String make, String model, String vin, String year, Drawable carImage, Color color, String name)
    {
        this.make = make;
        this.model = model;
        this.vin = vin;
        this.year = year;
        this.color = color;
        this.carImage = carImage;
        this.setName(name);
    }

    public String getMake()
    {
        return this.make;
    }

    public String getModel()
    {
        return this.model;
    }

    public String getYear()
    {
        return this.year;
    }

    public String getVin()
    {
        return this.vin;
    }

    public Color getColor()
    {
        return this.color;
    }

}
