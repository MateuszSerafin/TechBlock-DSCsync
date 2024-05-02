package com.gmail.genek530;

import java.util.HashMap;

public class MainConfig {

    public MainConfig() {};

    private HashMap<String, Boolean> modulesEnabled;

    private HashMap<String, String> redis;

    public HashMap<String, Boolean> getModulesEnabled() {
        return modulesEnabled;
    }

    public void setModulesEnabled(HashMap<String, Boolean> modulesEnabled) {
        this.modulesEnabled = modulesEnabled;
    }

    public HashMap<String, String> getRedis() {
        return redis;
    }

    public void setRedis(HashMap<String, String> redis) {
        this.redis = redis;
    }
}
