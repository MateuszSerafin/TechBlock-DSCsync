package com.gmail.genek530.modules.verificationdscmc;

import com.gmail.genek530.modules.verificationdscmc.common.RequireAuthUser;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RequireAuthUserDB {
    private static List<RequireAuthUser> waitingForAuth = new CopyOnWriteArrayList<>();


    public static void addUserToAuth(RequireAuthUser authUser){
        waitingForAuth.add(authUser);
    }


    @Nullable
    public static RequireAuthUser getRequireAuthUser(String nick){
        RequireAuthUser toreturn = null;

        for (RequireAuthUser tempAuthkey : waitingForAuth) {
            if(nick.equals(tempAuthkey.getMcNick())){
                toreturn = tempAuthkey;
                break;
            }
        }
        return toreturn;
    }

    @Nullable
    public static RequireAuthUser getRequireAuthUser(long discordID){
        RequireAuthUser toreturn = null;
        for (RequireAuthUser tempAuthkey : waitingForAuth) {
            if(discordID == tempAuthkey.getDiscordID()){
                toreturn = tempAuthkey;
                break;
            }
        }
        return toreturn;
    }

    public static boolean removeAuthUser(long discordID){
        RequireAuthUser toreturn = null;
        for (RequireAuthUser tempAuthkey : waitingForAuth) {
            if(discordID == tempAuthkey.getDiscordID()){
                toreturn = tempAuthkey;
                break;
            }
        }
        if(toreturn != null){
            waitingForAuth.remove(toreturn);
            return true;
        }
        return false;
    }

    public static void removeAuthUser(RequireAuthUser user){
        waitingForAuth.remove(user);
    }
}
