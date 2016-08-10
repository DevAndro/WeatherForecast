package com.akhil.weather.forecast.model.network.json;

/**
 * Created by Akhilesh on 5/8/16.
 */

public class MyPojo
{
    private Query query;

    public Query getQuery ()
    {
        return query;
    }

    public void setQuery (Query query)
    {
        this.query = query;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [query = "+query+"]";
    }
}
