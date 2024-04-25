package genek530.commons.redis;

import genek530.commons.internal.SynchronizedUser;

import java.util.List;
import java.util.UUID;

public class sendMultiplePermsUpdate {
    //Nie chcialem robic nowej klasy na to ale bardziej czytlenie berdzie :(
    //nie chcialem kombinowac aby sendpermupdat emial List<String> w sobie bo by to srake robilo
    //uzywane przy full syncu



    private SynchronizedUser shrdusr;
    private List<String> permsList;

    public sendMultiplePermsUpdate(SynchronizedUser SynchronizedUser, List<String> listapermisji){
        this.shrdusr = SynchronizedUser;
        this.permsList = listapermisji;
    }

    public UUID getMcuuid() {
        return shrdusr.getMcUUID();
    }

    public List<String> getPermsList() {
        return permsList;
    }


    //shared User jest uzywany w ackAuthorization jest dodawany do Daty na standalonie
    //oraz tylko brac z niego UUID i inne rzeczy
    public SynchronizedUser getSharedUser(boolean wiemcorobie) {
        return shrdusr;
    }
}
