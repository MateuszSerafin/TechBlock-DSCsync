package com.gmail.genek530;

import com.gmail.genek530.modules.verificationdscmc.VerifyPackets;
import com.gmail.genek530.tbot.commons.PyritePacket;
import com.gmail.genek530.tbot.commons.ValidPackets;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

public class PacketHandler implements PacketContainer {
    @PacketListener
    public void onPyritePacket(PyritePacket packet) {
        if(!packet.getDestination().equalsIgnoreCase("coordinator")){
            return;
        }
        switch(packet.getValidPacket()){
            case ValidPackets.checkPassword:
                VerifyPackets.checkPassword(packet.getSender(), (String) packet.getInformation());
        }
    }
}