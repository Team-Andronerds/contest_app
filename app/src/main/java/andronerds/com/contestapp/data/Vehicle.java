package andronerds.com.contestapp.data;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Chris on 3/16/2015.
 */
public class Vehicle extends User implements Serializable {

    private Color color;
    private Drawable carImage;
    private String make;
    private String model;
    private String vin;
    private String year;
    private int imageResource;


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

    public int getImageResource()
    {
        return imageResource;
    }

    public Drawable getCarImage()
    {
        return carImage;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void setCarImage(Drawable carImage)
    {
        this.carImage = carImage;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setVin(String vin)
    {
        this.vin = vin;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public void setImageResource(int imageResource)
    {
        this.imageResource = imageResource;
    }
}
