package genek530.commons.redis;

import genek530.commons.internal.sharedUser;

import java.util.List;
import java.util.UUID;

public class sendMultiplePermsUpdate {
    //Nie chcialem robic nowej klasy na to ale bardziej czytlenie berdzie :(
    //nie chcialem kombinowac aby sendpermupdat emial List<String> w sobie bo by to srake robilo
    //uzywane przy full syncu



    private sharedUser shrdusr;
    private List<String> permsList;

    public sendMultiplePermsUpdate(sharedUser sharedUser, List<String> listapermisji){
        this.shrdusr = sharedUser;
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
    public sharedUser getSharedUser(boolean wiemcorobie) {
        return shrdusr;
    }
}
