package genek530.commons.internal;

import java.util.List;

public class updateData {
    private List<sharedUser> sharedUserList;
    private List<authUser> authUserList;
    private List<String> whatPermstosendupadte;

    public updateData(List<sharedUser> sharedUserList, List<authUser> authUserList, List<String> whatPermstosendupadte){
        this.sharedUserList = sharedUserList;
        this.authUserList = authUserList;
        this.whatPermstosendupadte = whatPermstosendupadte;
    }
    public List<String> getWhatPermstosendupadte() {
        return whatPermstosendupadte;
    }

    public List<authUser> getAuthUserList() {
        return authUserList;
    }
    public List<sharedUser> getSharedUserList() {
        return sharedUserList;
    }
}
