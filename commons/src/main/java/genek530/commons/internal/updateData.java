package genek530.commons.internal;

import java.util.List;

public class updateData {
    private List<SynchronizedUser> synchronizedUserList;
    private List<RequiresSynchUser> requiresSynchUserList;
    private List<String> whatPermstosendupadte;

    public updateData(List<SynchronizedUser> synchronizedUserList, List<RequiresSynchUser> requiresSynchUserList, List<String> whatPermstosendupadte){
        this.synchronizedUserList = synchronizedUserList;
        this.requiresSynchUserList = requiresSynchUserList;
        this.whatPermstosendupadte = whatPermstosendupadte;
    }
    public List<String> getWhatPermstosendupadte() {
        return whatPermstosendupadte;
    }

    public List<RequiresSynchUser> getAuthUserList() {
        return requiresSynchUserList;
    }
    public List<SynchronizedUser> getSharedUserList() {
        return synchronizedUserList;
    }
}
