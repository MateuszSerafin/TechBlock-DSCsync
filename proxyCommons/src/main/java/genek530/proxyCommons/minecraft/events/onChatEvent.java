package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.sharedUser;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.discord.discordContainer;
import genek530.proxyCommons.discord.messageHandler;
import genek530.proxyCommons.discord.util.messageBuilder;
import genek530.proxyCommons.main;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashSet;
import java.util.UUID;

public class onChatEvent {
    public static void chat(String nick, UUID mcUUID, String mesadz, String server){
        if(!main.conf.isCzatsynchro()) return;
        sharedUser sharedUser = Data.getsharedUser(mcUUID);

        if(sharedUser == null){
            messageHandler.sendMessagetoDiscord(messageBuilder.mctodscUnsync(nick, mesadz, server), server);
            return;
        }
        messageHandler.sendMessagetoDiscord(messageBuilder.mctodscsync(nick,sharedUser.getDiscordID(), mesadz, server), server);
    }
}
