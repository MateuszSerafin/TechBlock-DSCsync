package com.gmail.genek530.tbot.commons;

import io.github.thatkawaiisam.pyrite.packet.Packet;

public class PyritePacket extends Packet {

    private String sender;
    private String destination;

    private String validPacket;
    private Object information;

    public PyritePacket(String sender, String destination, String validPacket, Object information){
        this.sender = sender;
        this.destination = destination;
        this.validPacket = validPacket;
        this.information = information;
    }

    public String getSender() {
        return sender;
    }

    public String getDestination() {
        return destination;
    }

    public String getValidPacket() {
        return validPacket;
    }

    public Object getInformation() {
        return information;
    }
}
