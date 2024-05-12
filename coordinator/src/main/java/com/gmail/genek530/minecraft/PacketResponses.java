package com.gmail.genek530.minecraft;

import com.gmail.genek530.discord.userverification.mcside.ReceivedVerifyPackets;
import com.gmail.genek530.tbot.commons.PyritePacket;
import com.gmail.genek530.tbot.commons.ValidPackets;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

public class PacketResponses implements PacketContainer {
    @PacketListener
    public void onPyritePacket(PyritePacket packet) {
        if(!packet.getDestination().equalsIgnoreCase("coordinator")){
            return;
        }
        switch(packet.getValidPacket()){
            case ValidPackets.callBackResponse:
                PacketRequests.handOffDataToCallback((String) packet.getInformation());
                return;


            case ValidPackets.checkPassword:
                ReceivedVerifyPackets.checkPassword(packet.getSender(), (String) packet.getInformation());
                return;
        }
    }
}