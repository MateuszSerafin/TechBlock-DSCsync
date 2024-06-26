package com.gmail.genek530.tbot.clientinterface;

import com.gmail.genek530.tbot.commons.PyritePacket;
import io.github.thatkawaiisam.pyrite.Pyrite;
import io.github.thatkawaiisam.pyrite.PyriteCredentials;

public class Packets {
    private static Pyrite pyrite;

    private static String whoAMI;

    public static void init(String ip, Integer port, String password, String whoAmIVar){
        PacketHandler standAloneHandler = new PacketHandler();
        pyrite = new Pyrite(new PyriteCredentials(ip, password, port));
        pyrite.registerContainer(standAloneHandler);
        whoAMI = whoAmIVar;
    }


    public static void sendToCoordinator(String action, String Data){
        pyrite.sendPacket(new PyritePacket(whoAMI, "coordinator", action, Data), "TBOT");
    }

    protected static String getWhoAMI(){
        return whoAMI;
    }

}
