package genek530.proxyCommons;

public interface serverCallbacks {
    //wszystko co proxyCommons musi callowac do servera proxy

    public void sendMessagetoServer(String messadz, String server);

    public void sendMessagewholeproxy(String messadz);


    public void createasyncrunnablereapeating(Runnable runnable, int intervalinsec);


    public int getPlayersOnline();

}
