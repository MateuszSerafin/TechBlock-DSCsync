package com.gmail.genek530.tbot.clientinterface;

import java.util.HashMap;

public class TBOTConfiguration {
    public TBOTConfiguration() {};

    private HashMap<String, Boolean> modulesEnabled;

    private HashMap<String, String> messages;

    private HashMap<String, String> redis;

    private HashMap<String, String> commandAlias;

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

    public HashMap<String, String> getRedis() {
        return redis;
    }

    public void setRedis(HashMap<String, String> redisInfo) {
        this.redis = redisInfo;
    }

    public HashMap<String, String> getCommandAlias() {
        return commandAlias;
    }

    public void setCommandAlias(HashMap<String, String> commandAlias) {
        this.commandAlias = commandAlias;
    }
}
