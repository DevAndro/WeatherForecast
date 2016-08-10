package com.akhil.weather.forecast.model.network.json;

/**
 * Created by Akhilesh on 5/8/16.
 */

public class Guid
{
    private String isPermaLink;

    public String getIsPermaLink ()
    {
        return isPermaLink;
    }

    public void setIsPermaLink (String isPermaLink)
    {
        this.isPermaLink = isPermaLink;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isPermaLink = "+isPermaLink+"]";
    }
}
