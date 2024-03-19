package genek530.proxyCommons;

import genek530.commons.internal.sharedUser;
import genek530.commons.internal.updateData;
import genek530.commons.redis.sendPermUpdate;
import genek530.proxyCommons.utils.lputil;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

import java.util.UUID;

public class actions {
    public static void startup(Object updateDataipromise){
        assert updateDataipromise instanceof String;
        updateData updateDat = main.gson.fromJson(updateDataipromise.toString(), updateData.class);
        Data.addtoauth(updateDat.getAuthUserList());
        Data.addtoSharedUsers(updateDat.getSharedUserList());
        Data.whatpermissonstohandle.addAll(updateDat.getWhatPermstosendupadte());
    }

    public static void desynchronizeuser(Object mcUUIDipromise){
        assert mcUUIDipromise instanceof String;
        main.logger.info("DESCYNRHONIZE JUSER");
        UUID mcUUID = main.gson.fromJson(mcUUIDipromise.toString(), UUID.class);

        sharedUser shrdusr = Data.getsharedUser(mcUUID);

        if(shrdusr == null){
            main.logger.info("NULL szared juser");
            return;
        }
        main.logger.info("PLIS POWIEDZ ZE SIE UNSYNUJESZ XDD");
        Data.desyncUser(shrdusr);
        lputil.dsyncUser(mcUUID);
        //sukkecs oby XDDDDlol heheheh XDDD
    }

    public static void adddsctomc(Object sendPermUpdateipromise){
        assert sendPermUpdateipromise instanceof String;
        sendPermUpdate sendPerm = main.gson.fromJson(sendPermUpdateipromise.toString(), sendPermUpdate.class);
        Data.skipAdding.put(sendPerm.getMcUUID(), sendPerm.getPermission());

        User user = main.lapi.getUserManager().getUser(sendPerm.getMcUUID());

        main.logger.info("A HEHEHEHEH IKSDE");
        if(user == null) return;

        main.logger.info("NEGUS");
        user.data().add(Node.builder(sendPerm.getPermission()).build());
        main.logger.info("le troll moment iksde 123");
        main.lapi.getUserManager().saveUser(user);
    }
    public static void removedsctomc(Object sendPermUpdateipromise){
        main.logger.info("poczontek");
        assert sendPermUpdateipromise instanceof String;
        sendPermUpdate sendPerm = main.gson.fromJson(sendPermUpdateipromise.toString(), sendPermUpdate.class);
        Data.skipRemoving.put(sendPerm.getMcUUID(), sendPerm.getPermission());

        main.logger.info("heheh usuwanko");
        User user = main.lapi.getUserManager().getUser(sendPerm.getMcUUID());
        if(user == null) return;
        user.data().remove(Node.builder(sendPerm.getPermission()).build());
        main.lapi.getUserManager().saveUser(user);

        main.logger.info("heheh usuwanko ale konieuc");
    }


}
