package genek530.proxyCommons;

import genek530.commons.internal.authUser;
import genek530.commons.internal.sharedUser;
import genek530.commons.redis.sendMultiplePermsUpdate;
import genek530.proxyCommons.utils.lputil;
import net.luckperms.api.node.Node;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data {
    //Wszystkie pontuja do tego samego objektu tylko innny lookup
    //private bo pewnie bym kiedys cos spierdolil i zle sie odwoalal do tego
    private static Map<Long, sharedUser> idlookup = new ConcurrentHashMap<>();

    //Jest dla jajec ale lepiej nie uzywac
    @Deprecated
    private static Map<String, sharedUser> nicklookup = new ConcurrentHashMap<>();

    private static Map<UUID, sharedUser> UUIDlookup = new ConcurrentHashMap<>();



    //LPAPI ma problem trzeba trzymac gdzies poprzednie dane
    public static Map<UUID, List<String>> perms = new HashMap<>();

    public static List<String> whatpermissonstohandle = new ArrayList<>();
    public static Map<UUID, String> skipAdding = new HashMap<>();
    public static Map<UUID, String> skipRemoving = new HashMap<>();

    public static void addUserAfterSync(sharedUser sharedUser){
        idlookup.put(sharedUser.getDiscordID(), sharedUser);
        nicklookup.put(sharedUser.getNick(), sharedUser);
        UUIDlookup.put(sharedUser.getMcUUID(), sharedUser);
        removeauthUser(sharedUser.getDiscordID());
    }

    public static void addUserAfterSync(Object sendMultiplePermsUpdateipromise){
        assert sendMultiplePermsUpdateipromise instanceof String;
        sendMultiplePermsUpdate sharedUsr = main.gson.fromJson(sendMultiplePermsUpdateipromise.toString(), sendMultiplePermsUpdate.class);

        addUserAfterSync(sharedUsr.getSharedUser(true));
        lputil.initialsync(sharedUsr.getPermsList(), sharedUsr.getMcuuid());
    }


    public static void desyncUser(sharedUser sharedUser){
        idlookup.remove(sharedUser.getDiscordID());
        nicklookup.remove(sharedUser.getNick());
        UUIDlookup.remove(sharedUser.getMcUUID());
    }


    public static void fullclean(){
        idlookup.clear();
        nicklookup.clear();
        UUIDlookup.clear();
        whatpermissonstohandle.clear();
        skipRemoving.clear();
        skipAdding.clear();
        perms.clear();

    }



    public static boolean isPlayerSynced(long discordID){
        return idlookup.containsKey(discordID);
    }

    @Nullable
    public static sharedUser getsharedUser(long discordID){
        return idlookup.get(discordID);
    }
    @Nullable
    public static sharedUser getsharedUser(UUID mcUUID){
        return UUIDlookup.get(mcUUID);
    }

    @Deprecated
    @Nullable
    public static sharedUser getsharedUser(String nick){
        return nicklookup.get(nick);
    }


    private static List<authUser> waitingforAuth = new CopyOnWriteArrayList<>();

    public static void addtoauthorization(Object authUserIpromise){
        assert authUserIpromise instanceof String;
        authUser authUser = main.gson.fromJson(authUserIpromise.toString(), authUser.class);

        //tldr proxy zmienia haslo wiec call do tego = nowy objekt
        waitingforAuth.remove(authUser);
        waitingforAuth.add(authUser);
    }

    private static boolean removeauthUser(long discordID){
        authUser toreturn = null;
        for (authUser tempAuthkey : waitingforAuth) {
            if(discordID == tempAuthkey.getDiscordID()){
                toreturn = tempAuthkey;
                break;
            }
        }
        if(toreturn != null){
            waitingforAuth.remove(toreturn);
            return true;
        }
        return false;
    }


    @Nullable
    public static authUser getauthUser(String nick){
        authUser toreturn = null;

        for (authUser tempAuthkey : waitingforAuth) {
            if(nick.equals(tempAuthkey.getMcNick())){
                toreturn = tempAuthkey;
                break;
            }
        }
        return toreturn;
    }
    @Nullable
    public static authUser getauthUser(long discordID){
        authUser toreturn = null;

        for (authUser tempAuthkey : waitingforAuth) {
            if(discordID == tempAuthkey.getDiscordID()){
                toreturn = tempAuthkey;
                break;
            }
        }
        return toreturn;
    }

    public static void removetempauth(authUser user){
        waitingforAuth.remove(user);
    }


    public static void addtoauth(List<authUser> authUsers){
        waitingforAuth.addAll(authUsers);
    }
    public static void addtoSharedUsers(List<sharedUser> sharedUserList){
        for (sharedUser shr : sharedUserList){
            addUserAfterSync(shr);
        }
    }
}
