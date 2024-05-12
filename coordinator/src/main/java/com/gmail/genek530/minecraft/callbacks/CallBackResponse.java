package com.gmail.genek530.minecraft.callbacks;

import java.time.Duration;
import java.time.Instant;

public class CallBackResponse {

    private boolean wasSet = false;

    private String data;

    private Instant creationTime = Instant.now();

    public CallBackResponse(){

    }

    public void set(String data) {
        this.data = data;
        this.wasSet = true;
    }

    public boolean WasSet() {
        return wasSet;
    }


    public String getData() {
        return data;
    }

    public boolean isExpired() {
        if(Duration.between(creationTime, Instant.now()).toSeconds() > 30){
            return true;
        }
        return false;
    }
}
