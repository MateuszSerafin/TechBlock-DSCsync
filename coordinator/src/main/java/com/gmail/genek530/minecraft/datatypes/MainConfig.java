package com.gmail.genek530.minecraft.datatypes;

import com.gmail.genek530.discord.configs.BotOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MainConfig {

    public MainConfig() {};

    private ArrayList<String> proxyServers;

    private BotOptions mainBot;

    private ArrayList<HashMap<String, String>> proxyBots;

    private HashMap<String, String> redis;

    public BotOptions getMainBot() {
        return mainBot;
    }

    public void setMainBot(BotOptions mainBot) {
        this.mainBot = mainBot;
    }

    public void setProxyBots(ArrayList<HashMap<String, String>> proxyBots) {
        this.proxyBots = proxyBots;
    }

    public ArrayList<HashMap<String, String>> getProxyBots() {
        return proxyBots;
    }

    public void setProxyServers(ArrayList<String> proxyServers) {
        this.proxyServers = proxyServers;
    }

    public ArrayList<String> getProxyServers() {
        return proxyServers;
    }

    public HashMap<String, String> getRedis() {
        return redis;
    }

    public void setRedis(HashMap<String, String> redis) {
        this.redis = redis;
    }
}
