package com.gmail.genek530;

import java.util.HashMap;

public class MainConfig {

    public MainConfig() {};

    private HashMap<String, Boolean> modulesEnabled;

    public HashMap<String, Boolean> getModulesEnabled() {
        return modulesEnabled;
    }

    public void setModulesEnabled(HashMap<String, Boolean> modulesEnabled) {
        this.modulesEnabled = modulesEnabled;
    }
}
