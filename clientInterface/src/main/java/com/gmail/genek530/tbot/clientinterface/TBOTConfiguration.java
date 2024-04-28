package com.gmail.genek530.tbot.clientinterface;

import java.util.HashMap;

public class TBOTConfiguration {
    public TBOTConfiguration() {};

    private HashMap<String, Boolean> modulesEnabled;

    private HashMap<String, String> messages;

    private HashMap<String, String> redisInfo;

    public HashMap<String, Boolean> getModulesEnabled() {
        return modulesEnabled;
    }

    public void setModulesEnabled(HashMap<String, Boolean> modulesEnabled) {
        this.modulesEnabled = modulesEnabled;
    }

    public void setMessages(HashMap<String, String> messages) {
        this.messages = messages;
    }

    public HashMap<String, String> getMessages() {
        return messages;
    }

    public HashMap<String, String> getRedisInfo() {
        return redisInfo;
    }

    public void setRedisInfo(HashMap<String, String> redisInfo) {
        this.redisInfo = redisInfo;
    }
}
