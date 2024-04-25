package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.SynchronizedUser;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendPermUpdate;
import genek530.commons.redis.validActions;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.main;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.model.user.User;

public class lpPermissionAdd {
    public static void onNodeAddEvent(NodeAddEvent event){
        //if(!main.conf.isUpdatujpermisije()) return;
        //nie jest rejestrowany gdy nie updatuje permisji to proxy
        if(!Data.whatpermissonstohandle.contains(event.getNode().getKey())) {
            main.logger.info("WAT PERMISSION TO HANDLER XDDD");
            return;
        }
        if(!event.isUser()) {
            main.logger.info("EVENT IS USER XDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            return;
        }
        User target = (User) event.getTarget();
        SynchronizedUser shareduser = Data.getsharedUser(target.getUniqueId());
        if(shareduser == null){
            main.logger.info("szared user null XDDDDDD");
            return;
        }
        if(Data.skipAdding.containsKey(target.getUniqueId())){
            Data.skipAdding.remove(target.getUniqueId());
            main.logger.info("le skip dis momentos J D K U R  W E ");
            return;
        }

        main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.addMCtoDsc, main.gson.toJson(new sendPermUpdate(shareduser.getMcUUID(), event.getNode().getKey()))), "TBOT");
    }
}
