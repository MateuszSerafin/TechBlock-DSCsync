package com.gmail.genek530.tbot.clientinterface;

import com.gmail.genek530.tbot.commons.PyritePacket;
import com.gmail.genek530.tbot.commons.ValidPackets;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

public class PacketHandler implements PacketContainer {
    @PacketListener
    public void onTestPacket(PyritePacket packet) {
        if(!packet.getDestination().equalsIgnoreCase(Packets.getWhoAMI())){
            return;
        }
        switch(packet.getValidPacket()){
            case ValidPackets.getAllPlayersOnProxy:









            case ValidPackets.checkResponse:
                PacketResponses.handleVerify((String) packet.getInformation());
        }
    }
}