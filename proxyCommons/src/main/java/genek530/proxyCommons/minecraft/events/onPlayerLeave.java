package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.SynchronizedUser;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.discord.messageHandler;
import genek530.proxyCommons.discord.util.messageBuilder;
import genek530.proxyCommons.main;

import java.util.UUID;

public class onPlayerLeave {
    public static void handle(String whatplayer, UUID mcUUID, String server){
        if(main.conf.isCzatsynchro()){
            SynchronizedUser sharedusr = Data.getsharedUser(mcUUID);
            if(sharedusr == null){
                messageHandler.sendMessagetoDiscord(messageBuilder.quitserver(whatplayer, server), server);
            } else {
                messageHandler.sendMessagetoDiscord(messageBuilder.quitserver(sharedusr, server), server);
            }
        }
        Data.perms.remove(mcUUID);

    }
}
