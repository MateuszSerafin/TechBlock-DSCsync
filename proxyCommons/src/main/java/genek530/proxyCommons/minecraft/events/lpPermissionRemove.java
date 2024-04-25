package genek530.proxyCommons.minecraft.events;

import genek530.commons.internal.SynchronizedUser;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendPermUpdate;
import genek530.commons.redis.validActions;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.main;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.user.User;

public class lpPermissionRemove {

    public static void onNodeRemoveEvent(NodeRemoveEvent event){
        //if(!main.conf.isUpdatujpermisije()) return;
        //nie jest rejestrowany gdy nie updatuje permisji to proxy
        if(!Data.whatpermissonstohandle.contains(event.getNode().getKey())) return;
        if(!main.conf.isUpdatujpermisije()) return;
        if(!event.isUser()) return;
        User target = (User) event.getTarget();
        SynchronizedUser shareduser = Data.getsharedUser(target.getUniqueId());
        if(shareduser == null) return;
        if(Data.skipRemoving.containsKey(target.getUniqueId())){
            Data.skipRemoving.remove(target.getUniqueId());
            return;
        }

        main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.remMCtoDsc, main.gson.toJson(new sendPermUpdate(shareduser.getMcUUID(), event.getNode().getKey()))), "TBOT");
    }
}
