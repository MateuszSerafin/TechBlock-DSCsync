package com.gmail.genek530.tbot.clientinterface;

import com.gmail.genek530.tbot.clientinterface.modules.verification.PacketResponses;
import com.gmail.genek530.tbot.commons.PyritePacket;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

class PacketHandler implements PacketContainer {
    @PacketListener
    public void onPyritePacket(PyritePacket packet) {
        if(!packet.getDestination().equalsIgnoreCase(Packets.getWhoAMI())){
            return;
        }
        switch(packet.getValidPacket()){
            case checkResponse:
                PacketResponses.handleVerify((String) packet.getInformation());
        }
    }
}