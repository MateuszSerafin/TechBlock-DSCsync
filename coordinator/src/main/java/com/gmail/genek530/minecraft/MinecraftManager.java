package com.gmail.genek530.minecraft;

import com.gmail.genek530.minecraft.callbacks.ProxyServer;

import javax.annotation.Nullable;
import java.util.HashMap;

public class MinecraftManager {

    private static HashMap<String, ProxyServer> proxies = new HashMap<>();









    @Nullable
    public ProxyServer getNamedProxyServer(String whatProxy){
        return proxies.get(whatProxy);
    }
}
//Each server is unknown it pings them and all of them should respond