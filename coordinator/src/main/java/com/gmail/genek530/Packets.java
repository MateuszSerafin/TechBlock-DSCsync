package com.gmail.genek530;

import com.gmail.genek530.tbot.commons.PyritePacket;
import com.gmail.genek530.tbot.commons.ValidPackets;
import io.github.thatkawaiisam.pyrite.Pyrite;
import io.github.thatkawaiisam.pyrite.PyriteCredentials;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

public class Packets {
    private static Pyrite pyrite;

    public static void init(String ip, Integer port, String password){
        PackerHandler standAloneHandler = new PackerHandler();
        pyrite = new Pyrite(new PyriteCredentials(ip, password, port));
        pyrite.registerContainer(standAloneHandler);
    }


    public static void sendToSpecificClient(String destination, ValidPackets action, String Data){
        pyrite.sendPacket(new PyritePacket("coordinator", destination, action, Data), "TBOT");
    }


}
class PackerHandler implements PacketContainer {
    @PacketListener
    public void onTestPacket(PyritePacket packet) {
        if(!packet.getDestination().equalsIgnoreCase("coordinator")){
            return;
        }




    }
}
