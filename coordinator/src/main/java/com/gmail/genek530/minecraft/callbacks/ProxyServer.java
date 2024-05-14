package com.gmail.genek530.minecraft.callbacks;

import com.gmail.genek530.minecraft.PacketRequests;
import com.gmail.genek530.minecraft.datatypes.BasicOnlinePlayer;
import com.gmail.genek530.tbot.commons.ValidPackets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//if this object is inacessible through minecraft manager it's assumed it's offline.
public class ProxyServer {

    private String nameOfProxy;

    //todo this will contain another class at some point
    private HashMap<String, BackEndServer> subServers = new HashMap<>();

    private Instant lastPingTime = Instant.now();

    public ProxyServer(String nameOfProxy){
        this.nameOfProxy = nameOfProxy;
    }


    @Nullable
    public List<BasicOnlinePlayer> getOnlinePlayers(){
        String jsonData = createCallBackAndWaitResponse(ValidPackets.getAllPlayersOnProxy);
        Gson gson = new Gson();
        ArrayList<BasicOnlinePlayer> players = gson.fromJson(jsonData, new TypeToken<ArrayList<BasicOnlinePlayer>>() {}.getType());
        return players;
    }


    //null = server offline packet died w/e
    @Nullable
    private String createCallBackAndWaitResponse(String whatPacket){
        CallBackResponse responseWaiter = new CallBackResponse();
        PacketRequests.createProxyCallback(this, whatPacket, responseWaiter);

        for (int i = 0; i < 30; i++) {
            if(responseWaiter.WasSet()) return responseWaiter.getData();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public boolean isExpired(){
        if(Duration.between(lastPingTime, Instant.now()).toSeconds() > 120){
            return true;
        }
        return false;
    }

    public void tick(){
        lastPingTime = Instant.now();
    }

    public String getNameOfProxy() {
        return nameOfProxy;
    }

    public HashMap<String, BackEndServer> getSubServers() {
        return subServers;
    }
}
