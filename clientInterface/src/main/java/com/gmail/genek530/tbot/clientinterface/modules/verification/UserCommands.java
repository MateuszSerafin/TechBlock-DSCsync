package com.gmail.genek530.tbot.clientinterface.modules.verification;

import com.gmail.genek530.tbot.clientinterface.Main;
import com.gmail.genek530.tbot.clientinterface.Packets;
import com.gmail.genek530.tbot.commons.ValidPackets;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class UserCommands {

    public static void syncCommand(String mcnick, UUID mcUUID, String[] args){
        if (args.length < 1) {
            Main.getClientImplementation().sendMessageToSpecificPlayer(mcUUID, Main.getTbotConfiguration().getMessages().get("verifyNoPassword"));
            return;
        }
        Main.getClientImplementation().sendMessageToSpecificPlayer(mcUUID, Main.getTbotConfiguration().getMessages().get("verifyWait"));
        ArrayList<String> data = new ArrayList<>();
        data.add(mcnick);
        data.add(mcUUID.toString());
        data.add(args[0]);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        Packets.sendToCoordinator(ValidPackets.checkPassword, json);
    }

}
