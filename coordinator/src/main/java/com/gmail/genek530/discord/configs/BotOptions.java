package com.gmail.genek530.discord.configs;

import java.util.ArrayList;

public class BotOptions {


    private String token;

    private String activityName;

    private ArrayList<String> descriptions;

    private int changeFrequency;



    public BotOptions(){

    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public String getToken() {
        return token;
    }

    public int getChangeFrequency() {
        return changeFrequency;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setChangeFrequency(int changeFrequency) {
        this.changeFrequency = changeFrequency;
    }

    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
