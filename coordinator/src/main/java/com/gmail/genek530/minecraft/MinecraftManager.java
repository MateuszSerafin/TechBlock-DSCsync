package com.gmail.genek530.minecraft;

import com.gmail.genek530.Main;
import com.gmail.genek530.minecraft.callbacks.ProxyServer;
import com.gmail.genek530.tbot.commons.PyritePacket;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MinecraftManager {

    protected static HashMap<String, ProxyServer> proxies = new HashMap<>();






    public static void tickOrAddProxyServer(String proxyServer){
        if(proxies.containsKey(proxyServer)){
            proxies.get(proxyServer).tick();
            return;
        }
        ProxyServer proxy = new ProxyServer(proxyServer);
        proxies.put(proxyServer, proxy);
    }

    @Nullable
    public ProxyServer getNamedProxyServer(String whatProxy){
        return proxies.get(whatProxy);
    }
}

//if 3x30 hello packet not received assume server is dead. This should be used more or less for discovery of servers and depending on it to check if server is online
//or not is discouraged as 120 sec timer is kinda a lot. If server is here but doesn't respond to packet within 30 seconds
//assume it's offline
class AllTicker implements Runnable {
    @Override
    public void run() {
        while(true){
            for(Map.Entry<String, ProxyServer> server : MinecraftManager.proxies.entrySet()){
                if(server.getValue().isExpired()){
                    Main.getLogger().info(String.format("Removing %s due to packets", server.getKey()));
                    MinecraftManager.proxies.remove(server.getKey());
                }
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}