package com.akhil.weather.forecast.model.network.json;

/**
 * Created by Akhilesh on 5/8/16.
 */

public class Results
{
    private Channel channel;

    public Channel getChannel ()
    {
        return channel;
    }

    public void setChannel (Channel channel)
    {
        this.channel = channel;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [channel = "+channel+"]";
    }
}
