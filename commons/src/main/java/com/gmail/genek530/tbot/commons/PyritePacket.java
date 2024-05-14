package com.gmail.genek530.tbot.commons;

import io.github.thatkawaiisam.pyrite.packet.Packet;

public class PyritePacket extends Packet {

    private String sender;
    private String destinationProxy;
    private String responsibleProxy;


    private String validPacket;
    private Object information;


    //coordinator -> proxy1, backend1
    //coordinator -> proxy2, backend1
    //coordinator -> proxy1
    //backend -> coordinator
    //proxy -> coordinator
    public PyritePacket(String sender, String destinationProxy, String validPacket, Object information){
        this.sender = sender;
        this.destinationProxy = destinationProxy;
        this.responsibleProxy = "N/A";

        this.validPacket = validPacket;
        this.information = information;
    }

    public PyritePacket(String sender, String destinationProxy, String responsibleProxy, String validPacket, Object information){
        this.sender = sender;
        this.destinationProxy = destinationProxy;
        this.responsibleProxy = responsibleProxy;

        this.validPacket = validPacket;
        this.information = information;
    }

    public String getSender() {
        return sender;
    }

    public String getDestination() {
        return destinationProxy;
    }

    public String getResponsibleProxy(){
        return this.responsibleProxy;
    }

    public String getValidPacket() {
        return validPacket;
    }

    public Object getInformation() {
        return information;
    }
}
