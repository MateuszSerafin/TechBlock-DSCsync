package genek530.commons.internal;

import java.util.UUID;

public class SynchronizedUser {
    //uzywac tej klasy tylko jak user jest 10000000000% na discordzie synchornizowany i na mc

    long discordID;
    UUID mcUUID;
    String nick;

    public SynchronizedUser(long discordID, UUID mcUUID, String nick){
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
