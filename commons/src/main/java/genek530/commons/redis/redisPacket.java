package genek530.commons.redis;

import io.github.thatkawaiisam.pyrite.packet.Packet;

public class redisPacket extends Packet {

    private String sender;
    private String destination;
    private validActions validActions;
    private Object information;

    public redisPacket(String sender, String destination, validActions validAction, Object information){
        this.sender = sender;
        this.destination = destination;
        this.validActions = validAction;
        this.information = information;
    }

    public String getSender() {
        return sender;
    }

    public String getDestination() {
        return destination;
    }

    public genek530.commons.redis.validActions getValidActions() {
        return validActions;
    }

    public Object getInformation() {
        return information;
    }
}
