package com.gmail.genek530.modules.verificationdscmc;

import java.util.HashMap;

public class VerifyModuleConfig {

    private String botToken;
    //Listening etc
    private String activityName;
    //Rest of it
    private String activityDescription;

    private HashMap<String, HashMap<String, String>> commands;

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

    public HashMap<String, HashMap<String, String>> getCommands() {
        return commands;
    }

    public void setCommands(HashMap<String, HashMap<String, String>> commands) {
        this.commands = commands;
    }
}
