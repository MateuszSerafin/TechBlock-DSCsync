package genek530.standAlone.redis;

import genek530.commons.redis.redisPacket;
import genek530.commons.redis.validActions;
import genek530.standAlone.Data;
import genek530.standAlone.Main;
import genek530.standAlone.actions;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

public class packetHandler implements PacketContainer {
    @PacketListener
    public void onTestPacket(redisPacket packet) {
        Main.logger.info(String.format("Pakiet %s", "Sender: " + packet.getSender() + " Dest:" + packet.getDestination() + " Packet action:" + packet.getValidActions().toString()));

        if(!packet.getDestination().equals("standAlone")) return;

        validActions validaction = packet.getValidActions();
        switch(validaction){
            case proxyStartup:
                Main.pyrite.sendPacket(new redisPacket("standAlone", packet.getSender(), validActions.startupdata, Main.gson.toJson(actions.updateData())), "TBOT");
                break;

            case ackAuthorization:
                actions.userJustSynchronized(packet.getInformation());
                Main.logger.info(packet.getInformation().toString());
                break;

            case addMCtoDsc:
                actions.addMCtodsc(packet.getInformation());
                break;
            case remMCtoDsc:
                actions.remMCtodsc(packet.getInformation());
                break;

            case syncuserstandAlone:
                actions.syncUser(packet.getInformation());
                break;

            default:
                break;
        }


    }
}
