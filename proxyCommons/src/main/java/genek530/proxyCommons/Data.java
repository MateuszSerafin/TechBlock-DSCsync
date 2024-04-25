package genek530.proxyCommons;

import genek530.commons.internal.RequiresSynchUser;
import genek530.commons.internal.SynchronizedUser;
import genek530.commons.redis.sendMultiplePermsUpdate;
import genek530.proxyCommons.utils.lputil;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data {
    //Wszystkie pontuja do tego samego objektu tylko innny lookup
    //private bo pewnie bym kiedys cos spierdolil i zle sie odwoalal do tego
    private static Map<Long, SynchronizedUser> idlookup = new ConcurrentHashMap<>();

    //Jest dla jajec ale lepiej nie uzywac
    @Deprecated
    private static Map<String, SynchronizedUser> nicklookup = new ConcurrentHashMap<>();

    private static Map<UUID, SynchronizedUser> UUIDlookup = new ConcurrentHashMap<>();



    //LPAPI ma problem trzeba trzymac gdzies poprzednie dane
    public static Map<UUID, List<String>> perms = new HashMap<>();

    public static List<String> whatpermissonstohandle = new ArrayList<>();
    public static Map<UUID, String> skipAdding = new HashMap<>();
    public static Map<UUID, String> skipRemoving = new HashMap<>();

    public static void addUserAfterSync(SynchronizedUser SynchronizedUser){
        idlookup.put(SynchronizedUser.getDiscordID(), SynchronizedUser);
        nicklookup.put(SynchronizedUser.getNick(), SynchronizedUser);
        UUIDlookup.put(SynchronizedUser.getMcUUID(), SynchronizedUser);
        removeauthUser(SynchronizedUser.getDiscordID());
    }

    public static void addUserAfterSync(Object sendMultiplePermsUpdateipromise){
        assert sendMultiplePermsUpdateipromise instanceof String;
        sendMultiplePermsUpdate sharedUsr = main.gson.fromJson(sendMultiplePermsUpdateipromise.toString(), sendMultiplePermsUpdate.class);

        addUserAfterSync(sharedUsr.getSharedUser(true));
        lputil.initialsync(sharedUsr.getPermsList(), sharedUsr.getMcuuid());
    }


    public static void desyncUser(SynchronizedUser SynchronizedUser){
        idlookup.remove(SynchronizedUser.getDiscordID());
        nicklookup.remove(SynchronizedUser.getNick());
        UUIDlookup.remove(SynchronizedUser.getMcUUID());
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
    public static SynchronizedUser getsharedUser(long discordID){
        return idlookup.get(discordID);
    }
    @Nullable
    public static SynchronizedUser getsharedUser(UUID mcUUID){
        return UUIDlookup.get(mcUUID);
    }

    @Deprecated
    @Nullable
    public static SynchronizedUser getsharedUser(String nick){
        return nicklookup.get(nick);
    }


    private static List<RequiresSynchUser> waitingforAuth = new CopyOnWriteArrayList<>();

    public static void addtoauthorization(Object authUserIpromise){
        assert authUserIpromise instanceof String;
        RequiresSynchUser RequiresSynchUser = main.gson.fromJson(authUserIpromise.toString(), RequiresSynchUser.class);

        //tldr proxy zmienia haslo wiec call do tego = nowy objekt
        waitingforAuth.remove(RequiresSynchUser);
        waitingforAuth.add(RequiresSynchUser);
    }

    private static boolean removeauthUser(long discordID){
        RequiresSynchUser toreturn = null;
        for (RequiresSynchUser tempAuthkey : waitingforAuth) {
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
    public static RequiresSynchUser getauthUser(String nick){
        RequiresSynchUser toreturn = null;

        for (RequiresSynchUser tempAuthkey : waitingforAuth) {
            if(nick.equals(tempAuthkey.getMcNick())){
                toreturn = tempAuthkey;
                break;
            }
        }
        return toreturn;
    }
    @Nullable
    public static RequiresSynchUser getauthUser(long discordID){
        RequiresSynchUser toreturn = null;

        for (RequiresSynchUser tempAuthkey : waitingforAuth) {
            if(discordID == tempAuthkey.getDiscordID()){
                toreturn = tempAuthkey;
                break;
            }
        }
        return toreturn;
    }

    public static void removetempauth(RequiresSynchUser user){
        waitingforAuth.remove(user);
    }


    public static void addtoauth(List<RequiresSynchUser> requiresSynchUsers){
        waitingforAuth.addAll(requiresSynchUsers);
    }
    public static void addtoSharedUsers(List<SynchronizedUser> synchronizedUserList){
        for (SynchronizedUser shr : synchronizedUserList){
            addUserAfterSync(shr);
        }
    }
}
