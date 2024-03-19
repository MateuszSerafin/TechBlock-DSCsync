package genek530.proxyCommons;

import java.util.List;
import java.util.Map;

public class configWazparser {
    private String token;
    private List<String> activity;
    private long guild;

    private boolean updatujpermisije;


    private Map<String, Long> msgchannelid;


    private Map<String, String> synchroczatuwiadomosci;

    public void setSynchroczatuwiadomosci(Map<String, String> synchroczatuwiadomosci) {
        this.synchroczatuwiadomosci = synchroczatuwiadomosci;
    }

    public Map<String, String> getSynchroczatuwiadomosci() {
        return synchroczatuwiadomosci;
    }

    public Map<String, Long> getMsgchannelid() {
        return msgchannelid;
    }

    public void setMsgchannelid(Map<String,Long> msgchannelid) {
        this.msgchannelid = msgchannelid;
    }

    private boolean czatsynchro;

    public void setCzatsynchro(boolean czatsynchro) {
        this.czatsynchro = czatsynchro;
    }

    public boolean isCzatsynchro() {
        return czatsynchro;
    }

    public void setUpdatujpermisije(boolean updatujpermisije) {
        this.updatujpermisije = updatujpermisije;
    }

    public boolean isUpdatujpermisije() {
        return updatujpermisije;
    }

    public Map<Long, String> perms;

    public Map<String, String> redis;

    public void setRedis(Map<String, String> redis) {
        this.redis = redis;
    }

    public Map<String, String> getRedis() {
        return redis;
    }

    public void setPerms(Map<Long, String> perms) {
        this.perms = perms;
    }

    public Map<Long, String> getPerms() {
        return perms;
    }

    private Map<String, String> wiadomosci;

    public Map<String, String> getWiadomosci() {
        return wiadomosci;
    }

    public void setWiadomosci(Map<String, String> wiadomosci) {
        this.wiadomosci = wiadomosci;
    }


    public void setActivity(List<String> activity) {
        this.activity = activity;
    }

    public void setGuild(long guild) {
        this.guild = guild;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getActivity() {
        return activity;
    }

    public long getGuild() {
        return guild;
    }

    public String getToken() {
        return token;
    }

    //hehe wąż parser bo snake yaml xdddd
    //btw jest puste ale nie usuwac bo wybuchnie
    public configWazparser(){

    }
}
