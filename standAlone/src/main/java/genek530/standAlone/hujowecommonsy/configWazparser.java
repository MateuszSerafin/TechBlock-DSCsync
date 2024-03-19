package genek530.standAlone.hujowecommonsy;

import java.util.Map;

public class configWazparser {
    private String token;
    private String activity;
    private long guild;

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

    private Map<String, Map<String, String>> wiadomosci;

    public Map<String, Map<String, String>> getWiadomosci() {
        return wiadomosci;
    }

    public void setWiadomosci(Map<String, Map<String, String>> wiadomosci) {
        this.wiadomosci = wiadomosci;
    }


    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setGuild(long guild) {
        this.guild = guild;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActivity() {
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
