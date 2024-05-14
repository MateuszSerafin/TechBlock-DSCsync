package com.gmail.genek530.minecraft;

import com.gmail.genek530.Main;
import com.gmail.genek530.minecraft.callbacks.BackEndServer;
import com.gmail.genek530.minecraft.callbacks.ProxyServer;
import com.gmail.genek530.tbot.commons.PyritePacket;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftManager {

    protected static HashMap<String, ProxyServer> proxies = new HashMap<>();


    public static void init(){
        Thread thread = new Thread(new AllTicker());
        thread.start();
    }


    public static void tickOrAddProxyServer(String proxyServer){
        if(proxies.containsKey(proxyServer)){
            proxies.get(proxyServer).tick();
            return;
        }
        ProxyServer proxy = new ProxyServer(proxyServer);
        proxies.put(proxyServer, proxy);
    }

    public static void tickOrAddBackEndServer(String proxyResponsible, String nameOfServer){
        ProxyServer proxyServer = proxies.get(proxyResponsible);
        if(proxyServer == null){
            Main.getLogger().info(String.format("Tried to register %1$s but proxy seems down %2$s", nameOfServer, proxyResponsible));
            return;
        }

        BackEndServer backEndServer = proxies.get(proxyResponsible).getSubServers().get(nameOfServer);
        if(backEndServer == null){
            proxies.get(proxyResponsible).getSubServers().put(nameOfServer, new BackEndServer(proxyServer, nameOfServer));
            return;
        }
        backEndServer.tick();
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
                    continue;
                }
                for(Map.Entry<String,BackEndServer> backEndServerEntry : server.getValue().getSubServers().entrySet()){
                    if(backEndServerEntry.getValue().isExpired()){
                        Main.getLogger().info(String.format("Removing backendserver %s due to packets", backEndServerEntry.getKey()));
                        server.getValue().getSubServers().remove(backEndServerEntry.getKey());
                        continue;
                    }
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