package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.SynchronizedUser;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.discord.messageHandler;
import genek530.proxyCommons.discord.util.messageBuilder;
import genek530.proxyCommons.main;

import java.util.UUID;

public class onChatEvent {
    public static void chat(String nick, UUID mcUUID, String mesadz, String server){
        if(!main.conf.isCzatsynchro()) return;
        SynchronizedUser SynchronizedUser = Data.getsharedUser(mcUUID);

        if(SynchronizedUser == null){
            messageHandler.sendMessagetoDiscord(messageBuilder.mctodscUnsync(nick, mesadz, server), server);
            return;
        }
        messageHandler.sendMessagetoDiscord(messageBuilder.mctodscsync(nick, SynchronizedUser.getDiscordID(), mesadz, server), server);
    }
}
