package com.gmail.genek530.discord.common;

import java.util.UUID;

public class VerifiedUser {
    //uzywac tej klasy tylko jak user jest 10000000000% na discordzie synchornizowany i na mc

    long discordID;
    UUID mcUUID;
    String nick;

    public VerifiedUser(long discordID, UUID mcUUID, String nick){
        this.discordID = discordID;
        this.mcUUID = mcUUID;
        this.nick = nick;
    }

    public long getDiscordID() {
        return discordID;
    }
    public String getNick() {
        return nick;
    }
    public UUID getMcUUID() {
        return mcUUID;
    }
}
