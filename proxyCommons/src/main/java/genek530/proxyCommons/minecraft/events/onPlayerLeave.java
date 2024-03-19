package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.sharedUser;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.discord.discordContainer;
import genek530.proxyCommons.discord.messageHandler;
import genek530.proxyCommons.discord.util.messageBuilder;
import genek530.proxyCommons.main;

import java.util.HashSet;
import java.util.UUID;

public class onPlayerLeave {
    public static void handle(String whatplayer, UUID mcUUID, String server){
        if(main.conf.isCzatsynchro()){
            sharedUser sharedusr = Data.getsharedUser(mcUUID);
            if(sharedusr == null){
                messageHandler.sendMessagetoDiscord(messageBuilder.quitserver(whatplayer, server), server);
            } else {
                messageHandler.sendMessagetoDiscord(messageBuilder.quitserver(sharedusr, server), server);
            }
        }
        Data.perms.remove(mcUUID);

    }
}
