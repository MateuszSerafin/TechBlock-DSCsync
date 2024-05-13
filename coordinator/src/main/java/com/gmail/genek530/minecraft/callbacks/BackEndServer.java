package com.gmail.genek530.minecraft.callbacks;

import com.gmail.genek530.minecraft.PacketRequests;

import javax.annotation.Nullable;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.Instant;

public class BackEndServer {

    private ProxyServer underWhichProxy;

    private String backendName;

    private Instant lastPingTime;

    public BackEndServer(){

    }


    //null = server offline packet died w/e
    @Nullable
    private String createCallBackAndWaitResponse(String whatPacket){
        CallBackResponse responseWaiter = new CallBackResponse();
        PacketRequests.createBackendCallback(underWhichProxy, this, whatPacket, responseWaiter);

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

    public String getBackendName() {
        return backendName;
    }
}
