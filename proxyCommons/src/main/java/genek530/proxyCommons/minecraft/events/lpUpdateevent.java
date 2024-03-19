package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.sharedUser;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendMultiplePermsUpdate;
import genek530.commons.redis.validActions;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.main;
import genek530.proxyCommons.utils.lputil;
import net.luckperms.api.event.user.UserDataRecalculateEvent;

import java.util.List;
import java.util.UUID;

public class lpUpdateevent {
    public static void handle(UserDataRecalculateEvent event){
        handlePermissions(event.getUser().getUniqueId());
    }

    private static void handlePermissions(UUID playerUUID){
        if(!main.conf.isUpdatujpermisije()) return;
        //Jak plugin/bot byl offline gracz moze byc nie zsynchronizowany
        sharedUser shareduser = Data.getsharedUser(playerUUID);
        if(shareduser == null) return;

        List<String> nowperms = lputil.getPerms(shareduser);

        for (String prevoiusperms : Data.perms.get(playerUUID)) {
          if(!nowperms.contains(prevoiusperms)){
              main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.remMCtoDsc, prevoiusperms), "TBOT");
          }
        }
        for(String nowperm : nowperms) {
           if(!Data.perms.get(playerUUID).contains(nowperm)){
               main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.addMCtoDsc, nowperm), "TBOT");
           }
        }
        Data.perms.replace(playerUUID, nowperms);

        //main.logger.info(playerUUID.toString() + " update danych");
        //main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.syncuserstandAlone, new sendMultiplePermsUpdate(shareduser, lputil.getPerms(shareduser))), "TBOT");
    }
}
