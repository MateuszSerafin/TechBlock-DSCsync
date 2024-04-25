package genek530.proxyCommons.utils;

import genek530.commons.internal.SynchronizedUser;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.main;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class lputil {
    public static List<String> getPerms(SynchronizedUser SynchronizedUser){
        User user = main.lapi.getUserManager().getUser(SynchronizedUser.getMcUUID());;

        ArrayList<String> arrayList = new ArrayList<String>();
        for (Node node : user.getNodes()) {
            if(Data.whatpermissonstohandle.contains(node.getKey())){
                arrayList.add(node.getKey());
            }
        }
        return arrayList;
    }


    public static List<String> getPerms(UUID playerUUID){
        User user = main.lapi.getUserManager().getUser(playerUUID);;

        ArrayList<String> arrayList = new ArrayList<String>();
        for (Node node : user.getNodes()) {
            if(Data.whatpermissonstohandle.contains(node.getKey())){
                arrayList.add(node.getKey());
            }
        }
        return arrayList;
    }

    public static void initialsync(List<String> perms,  UUID mcUUID){
        User user = main.lapi.getUserManager().getUser(mcUUID);

        if(user == null) return;

        boolean flag = false;
        for (String perm : perms) {
            user.data().add(Node.builder(perm).build());
            flag = true;
        }
        if(flag) main.lapi.getUserManager().saveUser(user);
    }

    public static void dsyncUser(UUID mcUUID){
        User user = main.lapi.getUserManager().getUser(mcUUID);;
        for (String s : Data.whatpermissonstohandle) {
            user.data().remove(Node.builder(s).build());
        }
        main.lapi.getUserManager().saveUser(user);
    }
}
