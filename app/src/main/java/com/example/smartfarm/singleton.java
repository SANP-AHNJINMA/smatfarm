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
    private static singleton instance = null;

    public static synchronized singleton getInstance(){
        if(null == instance){
            instance = new singleton();
        }
        return instance;
    }
}