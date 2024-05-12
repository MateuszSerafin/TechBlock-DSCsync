package com.gmail.genek530.discord.configs;

import java.util.HashMap;

public class VerifyModuleConfig {

    private String botToken;
    //Listening etc
    private String activityName;
    //Rest of it
    private String activityDescription;

    private HashMap<String, HashMap<String, String>> messages;

    public VerifyModuleConfig(){

    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public HashMap<String, HashMap<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, HashMap<String, String>> messages) {
        this.messages = messages;
    }
}
