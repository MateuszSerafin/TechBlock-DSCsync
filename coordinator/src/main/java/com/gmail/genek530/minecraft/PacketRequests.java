package com.gmail.genek530.minecraft;

import com.gmail.genek530.Main;
import com.gmail.genek530.minecraft.callbacks.CallBackResponse;
import com.gmail.genek530.tbot.commons.PyritePacket;
import com.google.gson.Gson;
import io.github.thatkawaiisam.pyrite.Pyrite;
import io.github.thatkawaiisam.pyrite.PyriteCredentials;

import java.util.*;

public class PacketRequests {
    private static Pyrite pyrite;


    //String is json data, depends on callback will be different.
    protected static HashMap<UUID, CallBackResponse> callBacks = new HashMap<>();


    public static void init(String ip, Integer port, String password){
        PacketResponses standAloneHandler = new PacketResponses();
        pyrite = new Pyrite(new PyriteCredentials(ip, password, port));
        pyrite.registerContainer(standAloneHandler);

        Thread thread = new Thread(new Ticker());
        thread.start();
    }


    public static void sendToSpecificClient(String destination, String action, String Data){
        pyrite.sendPacket(new PyritePacket("coordinator", destination, action, Data), "TBOT");
    }

    public static UUID createCallback(String destination, String action, CallBackResponse callBackResponse){
        Main.getLogger().info(String.format("Creating call back to %s", destination));
        UUID callBackUUID = UUID.randomUUID();

        callBacks.put(callBackUUID, callBackResponse);
        pyrite.sendPacket(new PyritePacket("coordinator", destination, action, callBackUUID.toString()), "TBOT");
        return callBackUUID;
    }

    //todo this will error
    public static void handOffDataToCallback(String onepacket){
        Gson gson = new Gson();
        List<String> callback = gson.fromJson(onepacket, ArrayList.class);
        UUID callBackUUID = UUID.fromString(callback.get(0));
        callBacks.get(callBackUUID).set(callback.get(1));
    }

}
class Ticker implements Runnable{
    @Override
    public void run() {
        while(true){
            for(Map.Entry<UUID, CallBackResponse> entry : PacketRequests.callBacks.entrySet()){
                if(entry.getValue().isExpired()){
                    Main.getLogger().info(String.format("Expired callback %s....", entry.getKey()));
                    PacketRequests.callBacks.remove(entry.getKey());
                }
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

