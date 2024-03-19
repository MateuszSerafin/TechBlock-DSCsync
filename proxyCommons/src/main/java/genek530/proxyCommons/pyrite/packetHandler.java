package genek530.proxyCommons.pyrite;

import genek530.commons.redis.redisPacket;
import genek530.commons.redis.validActions;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.actions;
import genek530.proxyCommons.main;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

public class packetHandler implements PacketContainer {
    @PacketListener
    public void onPacket(redisPacket packet){
        main.logger.info(String.format("Pakiet %s", "Sender: " + packet.getSender() + " Dest:" + packet.getDestination() + " Packet action:" + packet.getValidActions().toString()) + " data:"  +packet.getInformation().toString());


        if(packet.getDestination().equals("standAlone")) return;
        validActions validaction = packet.getValidActions();
        switch(validaction){
            case restartedStandalone:
                Data.fullclean();
                main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.proxyStartup, null), "TBOT");

                break;

            case startupdata:
                if(!packet.getDestination().equals(main.bootUUID)) return;
                actions.startup(packet.getInformation());
                break;

            case adddsctoMC:
                main.logger.info(String.valueOf(main.conf.isUpdatujpermisije()));
                if(!main.conf.isUpdatujpermisije()) return;
                actions.adddsctomc(packet.getInformation());
                break;

            case remdsctoMC:
                if(!main.conf.isUpdatujpermisije()) return;
                actions.removedsctomc(packet.getInformation());
                break;
                
            case updateAuthenticatedUsers:
                main.logger.info(packet.getInformation().toString());
                Data.addtoauthorization(packet.getInformation());
                break;

            case createSyncedUser:
                Data.addUserAfterSync(packet.getInformation());
                break;


            case desynchronizeuser:
                if(!main.conf.isUpdatujpermisije()) return;
                actions.desynchronizeuser(packet.getInformation());
                break;
            default:
                break;
        }



    }
}
