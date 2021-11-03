package com.example.smartfarm;

public class singleton {
    private String id;
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }

    private String houseid = "1";
    public String getHouseid()
    {
        return houseid;
    }

    private String URL = "http://192.168.0.3/";
    public String getURL()
    {
        return URL;
    }
    public void setURL(String URL)
    {
        this.URL = URL;
    }
    private static singleton instance = null;

    public static synchronized singleton getInstance(){
        if(null == instance){
            instance = new singleton();
        }
        return instance;
    }
}