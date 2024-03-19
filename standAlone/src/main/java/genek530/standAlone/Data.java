package genek530.standAlone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import genek530.commons.internal.updateData;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.validActions;
import genek530.commons.internal.authUser;
import genek530.commons.internal.sharedUser;

import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data {


    //Wszystkie pontuja do tego samego objektu tylko innny lookup
    //private bo pewnie bym kiedys cos spierdolil i zle sie odwoalal do tego
    public static Map<Long, sharedUser> idlookup = new HashMap<>();

    //Jest dla jajec ale lepiej nie uzywac
    @Deprecated
    private static Map<String, sharedUser> nicklookup = new HashMap<>();

    private static Map<UUID, sharedUser> UUIDlookup = new HashMap<>();



    //user role plis nie rozjebac
    //bo bendzie przypau
    public static HashMap<Long, Long> addSkip = new HashMap<>();
    public static HashMap<Long, Long> remSkip = new HashMap<>();


    public static void userJustSynchronized(sharedUser sharedUser){
        idlookup.put(sharedUser.getDiscordID(), sharedUser);
        nicklookup.put(sharedUser.getNick(), sharedUser);
        UUIDlookup.put(sharedUser.getMcUUID(), sharedUser);
        //gracz wlasnie byl zsynchornizowany ale moze byc dalej w authie
        removeauthUser(sharedUser.getDiscordID());
        shutdown();
    }

    public static void desyncUser(sharedUser sharedUser){
        idlookup.remove(sharedUser.getDiscordID());
        nicklookup.remove(sharedUser.getNick());
        UUIDlookup.remove(sharedUser.getMcUUID());
        shutdown();
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


    public static void addauthUser(authUser authUser){
        waitingforAuth.add(authUser);
    }

    public static boolean removeauthUser(long discordID){
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




    public static void shutdown(){
        File dataDir = Main.dataDir.toFile();
        File data = new File(dataDir + "/db.json");
        Gson gson = Main.gson;
        try {
            FileWriter writer = new FileWriter(data);
            gson.toJson(new ArrayList<>(idlookup.values()), writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initialize(){
        File dataDir = Main.dataDir.toFile();
        File data = new File(dataDir + "/db.json");
        if(!data.exists()){
            return;
        }
        Gson gson = Main.gson;
        Type listType = new TypeToken<ArrayList<sharedUser>>(){}.getType();
        try {
            for(sharedUser sharedUser : (List<sharedUser>) gson.fromJson(new FileReader(data), listType)){
                userJustSynchronized(sharedUser);
            };
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //nie robic z tym nic poza tą klasą, lub do upadte informacji na podserverach
    public static List<sharedUser> sharedUserList(boolean wiemcorobie){
        return new ArrayList<>(idlookup.values());
    }
    public static List<authUser> authUserList(boolean wiemcorobie){
        return waitingforAuth;
    }
}
