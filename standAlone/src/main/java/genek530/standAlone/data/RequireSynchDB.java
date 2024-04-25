package genek530.standAlone.data;

import genek530.commons.internal.RequiresSynchUser;
import genek530.commons.internal.SynchronizedUser;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RequireSynchDB {

    private static List<RequiresSynchUser> waitingForSynchronization = new CopyOnWriteArrayList<>();

    public static void addToWaitingList(RequiresSynchUser RequiresSynchUser){waitingForSynchronization.add(RequiresSynchUser);
    }

    public static boolean removeWaitingUser(long discordID){
        RequiresSynchUser toreturn = null;
        for (RequiresSynchUser tempAuthkey : waitingForSynchronization) {
            if(discordID == tempAuthkey.getDiscordID()){
                toreturn = tempAuthkey;
                break;
            }
        }
        if(toreturn != null){
            waitingForSynchronization.remove(toreturn);
            return true;
        }
        return false;
    }
    public static void removeWaitingUser(RequiresSynchUser user){
        waitingForSynchronization.remove(user);
    }

    @Nullable
    public static RequiresSynchUser getRequireSynchUser(long discordID){
        RequiresSynchUser toreturn = null;
        for (RequiresSynchUser tempAuthkey : waitingForSynchronization) {
            if(discordID == tempAuthkey.getDiscordID()){
                toreturn = tempAuthkey;
                break;
            }
        }
        return toreturn;
    }
}
