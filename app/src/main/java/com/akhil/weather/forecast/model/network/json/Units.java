package com.akhil.weather.forecast.model.network.json;

/**
 * Created by Akhilesh on 5/8/16.
 */

public class Units
{
    private String distance;

    private String pressure;

    private String speed;

    private String temperature;

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getPressure ()
    {
        return pressure;
    }

    public void setPressure (String pressure)
    {
        this.pressure = pressure;
    }

    public String getSpeed ()
    {
        return speed;
    }

    public void setSpeed (String speed)
    {
        this.speed = speed;
    }

    public String getTemperature ()
    {
        return temperature;
    }

    public void setTemperature (String temperature)
    {
        this.temperature = temperature;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [distance = "+distance+", pressure = "+pressure+", speed = "+speed+", temperature = "+temperature+"]";
    }
}
