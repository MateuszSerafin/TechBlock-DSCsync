package genek530.standAlone;

import genek530.commons.internal.RequiresSynchUser;
import genek530.commons.internal.SynchronizedUser;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Data {





    @Deprecated
    @Nullable
    public static SynchronizedUser getsharedUser(String nick){
        return nicklookup.get(nick);
    }











    //nie robic z tym nic poza tą klasą, lub do upadte informacji na podserverach
    public static List<SynchronizedUser> sharedUserList(boolean wiemcorobie){
        return new ArrayList<>(idlookup.values());
    }
    public static List<RequiresSynchUser> authUserList(boolean wiemcorobie){
        return waitingforAuth;
    }
}



