package com.gmail.genek530.minecraft.datatypes;

import java.util.UUID;

public class BasicOnlinePlayer {

    private UUID mcUUID;

    private String mcNick;

    public BasicOnlinePlayer(UUID mcUUID, String mcNick){
        this.mcUUID = mcUUID;
        this.mcNick = mcNick;
    }

    public UUID getMcUUID() {
        return mcUUID;
    }

    public String getMcNick() {
        return mcNick;
    }
}
