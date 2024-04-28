package com.gmail.genek530.modules.verificationdscmc;

import com.gmail.genek530.Main;
import com.gmail.genek530.modules.verificationdscmc.common.RequireAuthUser;
import com.gmail.genek530.modules.verificationdscmc.common.VerifiedUser;
import com.google.gson.Gson;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VerifiedUserDB {

    private static final Map<Long, VerifiedUser> idlookup = new HashMap<>();

    private static final Map<UUID, VerifiedUser> UUIDlookup = new HashMap<>();

    public static boolean isPlayerSynced(long discordID){
        return idlookup.containsKey(discordID);
    }
    public static boolean isPlayerSynced(UUID mcUUID){
        return UUIDlookup.containsKey(mcUUID);
    }

    @Nullable
    public static VerifiedUser getSynchronizedUser(long discordID){
        return idlookup.get(discordID);
    }
    @Nullable
    public static VerifiedUser getSynchronizedUser(UUID mcUUID){
        return UUIDlookup.get(mcUUID);
    }

    public static void deSynchronizeUser(VerifiedUser VerifiedUser) {
        idlookup.remove(VerifiedUser.getDiscordID());
        UUIDlookup.remove(VerifiedUser.getMcUUID());
        try{
            File data = new File(Main.getDataDir() + "/modules/verification/" + VerifiedUser.getMcUUID().toString());
            if(data.exists()) Files.delete(data.toPath());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void SynchronizeUser(RequireAuthUser authUser, UUID mcUUID){
        VerifiedUser verifiedUser =  new VerifiedUser(authUser.getDiscordID(), mcUUID, authUser.getMcNick());
        idlookup.put(authUser.getDiscordID(), verifiedUser);
        UUIDlookup.put(mcUUID, verifiedUser);
        saveSynchronizedUser(verifiedUser.getMcUUID());
    }


    public static boolean saveSynchronizedUser(UUID mcUUID){
        try {
            File dataDir = Main.getDataDir();
            File data = new File(dataDir + "/modules/verification/" + mcUUID.toString());
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(data);

            VerifiedUser syncedUser = getSynchronizedUser(mcUUID);
            if(syncedUser == null){
                Main.getLogger().info(String.format("Tried to save data for %s but it was null...", mcUUID.toString()));
                return false;
            }
            Main.getLogger().info(String.format("Saving data for %s", mcUUID.toString()));
            gson.toJson(syncedUser, writer);
            writer.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void init() throws Exception {
        File dataDir = new File(Main.getDataDir() + "/modules/verification/");

        if(!dataDir.exists()){
            Main.getLogger().info("Creating user data directory because it doesn't exist..");
            dataDir.mkdir();
        }

        Gson gson = new Gson();

        //Type listType = new TypeToken<ArrayList<SynchronizedUser>>(){}.getType();
        for(File userData : dataDir.listFiles()){
            VerifiedUser syncedUser = gson.fromJson(new FileReader(userData), VerifiedUser.class);
            UUIDlookup.put(syncedUser.getMcUUID(), syncedUser);
            idlookup.put(syncedUser.getDiscordID(), syncedUser);
            Main.getLogger().info(String.format("Loaded user %s", syncedUser.getNick()));
        }
    }
}