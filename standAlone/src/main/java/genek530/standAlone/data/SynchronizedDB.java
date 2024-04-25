package genek530.standAlone.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import genek530.commons.internal.SynchronizedUser;
import genek530.standAlone.Main;

import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class SynchronizedDB {

    private static final Map<Long, SynchronizedUser> idlookup = new HashMap<>();

    private static final Map<UUID, SynchronizedUser> UUIDlookup = new HashMap<>();

    public static boolean isPlayerSynced(long discordID){
        return idlookup.containsKey(discordID);
    }
    public static boolean isPlayerSynced(UUID mcUUID){
        return UUIDlookup.containsKey(mcUUID);
    }

    @Nullable
    public static SynchronizedUser getSynchronizedUser(long discordID){
        return idlookup.get(discordID);
    }
    @Nullable
    public static SynchronizedUser getSynchronizedUser(UUID mcUUID){
        return UUIDlookup.get(mcUUID);
    }

    public static void deSynchronizeUser(SynchronizedUser SynchronizedUser){
        idlookup.remove(SynchronizedUser.getDiscordID());
        UUIDlookup.remove(SynchronizedUser.getMcUUID());
    }

    public static boolean saveSynchronizedUser(UUID mcUUID){
        try {
        File dataDir = Main.dataDir.toFile();
        File data = new File(dataDir + "/" + mcUUID.toString());
        Gson gson = Main.gson;
        FileWriter writer = new FileWriter(data);

        SynchronizedUser syncedUser = getSynchronizedUser(mcUUID);
        if(syncedUser == null){
            Main.logger.info(String.format("Tried to save data for %s but it was null...", mcUUID.toString()));
            return false;
        }
        Main.logger.info(String.format("Saving data for %s", mcUUID.toString()));
        gson.toJson(syncedUser, writer);
        writer.close();
        return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void init() throws Exception {
        File dataDir = Main.dataDir.toFile();
        Gson gson = Main.gson;

        //Type listType = new TypeToken<ArrayList<SynchronizedUser>>(){}.getType();
        for(File userData : dataDir.listFiles()){
            SynchronizedUser syncedUser = gson.fromJson(new FileReader(userData), SynchronizedUser.class);
            UUIDlookup.put(syncedUser.getMcUUID(), syncedUser);
            idlookup.put(syncedUser.getDiscordID(), syncedUser);
        }
    }
}
