package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.sharedUser;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendMultiplePermsUpdate;
import genek530.commons.redis.validActions;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.discord.discordContainer;
import genek530.proxyCommons.discord.messageHandler;
import genek530.proxyCommons.discord.util.messageBuilder;
import genek530.proxyCommons.main;
import genek530.proxyCommons.utils.lputil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.UUID;

public class onServerjoinchange {


    public static void handle(UUID playerUUID, String mcNick, String connectedTO){
        if(main.conf.isCzatsynchro()){
            sharedUser shrdUsr = Data.getsharedUser(playerUUID);
            if(shrdUsr == null){
                messageHandler.sendMessagetoDiscord(messageBuilder.joinedServer(mcNick, connectedTO), connectedTO);

            } else {
                messageHandler.sendMessagetoDiscord(messageBuilder.joinedServer(shrdUsr, connectedTO), connectedTO);
            }
        }
        Data.perms.put(playerUUID, lputil.getPerms(playerUUID));
        handlePermissions(playerUUID);
    }



    public static void handle(UUID playerUUID, String mcNick, String connectedTO, String connectingfrom){
        if(main.conf.isCzatsynchro()){
            sharedUser shrdusr = Data.getsharedUser(playerUUID);
            if(shrdusr == null){
                messageHandler.sendMessagetoDiscord(messageBuilder.changedServer(mcNick, connectedTO, connectingfrom), connectedTO);
            } else {
                messageHandler.sendMessagetoDiscord(messageBuilder.changedServer(shrdusr, connectedTO, connectingfrom), connectedTO);
            }
        }
        }




    private static void handlePermissions(UUID playerUUID){
        if(!main.conf.isUpdatujpermisije()) return;
        //Jak plugin/bot byl offline gracz moze byc nie zsynchronizowany
        sharedUser shareduser = Data.getsharedUser(playerUUID);
        if(shareduser == null) return;

        main.logger.info(playerUUID.toString() + " sie zalogował bedzie synchronizowany");
        main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.syncuserstandAlone, new sendMultiplePermsUpdate(shareduser, lputil.getPerms(shareduser))), "TBOT");
    }
}
