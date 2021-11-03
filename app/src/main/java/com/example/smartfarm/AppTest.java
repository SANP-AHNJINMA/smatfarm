package com.example.smartfarm;

import android.app.Application;


public class AppTest extends Application {

    private String id;
    private String houseid = "1";
    private String URL =  "http://210.182.153.118/";

    public String getHouseid(){return houseid;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getURL(){return URL;}
    public void setURL(String URL){this.URL = URL;}
}