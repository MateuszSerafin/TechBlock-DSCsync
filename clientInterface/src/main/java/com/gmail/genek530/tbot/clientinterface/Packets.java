package com.gmail.genek530.tbot.clientinterface;

import com.gmail.genek530.tbot.commons.PyritePacket;
import com.gmail.genek530.tbot.commons.ValidPackets;
import io.github.thatkawaiisam.pyrite.Pyrite;
import io.github.thatkawaiisam.pyrite.PyriteCredentials;
import io.github.thatkawaiisam.pyrite.packet.PacketContainer;
import io.github.thatkawaiisam.pyrite.packet.PacketListener;

public class Packets {
    private static Pyrite pyrite;

    private static String whoAMI;

    public static void init(String ip, Integer port, String password, String whoAmIVar){
        PackerHandler standAloneHandler = new PackerHandler();
        pyrite = new Pyrite(new PyriteCredentials(ip, password, port));
        pyrite.registerContainer(standAloneHandler);
        whoAMI = whoAmIVar;
    }


    public static void sendToCoordinator(ValidPackets action, String Data){
        pyrite.sendPacket(new PyritePacket(whoAMI, "coordinator", action, Data), "TBOT");
    }

    protected static String getWhoAMI(){
        return whoAMI;
    }

}
class PackerHandler implements PacketContainer {
    @PacketListener
    public void onTestPacket(PyritePacket packet) {
        if(!packet.getDestination().equalsIgnoreCase(Packets.getWhoAMI())){
            return;
        }




    }
}
