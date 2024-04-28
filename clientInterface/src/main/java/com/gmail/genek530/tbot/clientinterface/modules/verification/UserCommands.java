package com.gmail.genek530.tbot.clientinterface.modules.verification;

import com.gmail.genek530.tbot.clientinterface.Main;

import java.util.UUID;

public class UserCommands {

    public static void syncCommand(String mcnick, UUID mcUUID, String password){
        Main.getClientImplementation().sendMessageToSpecificPlayer(mcUUID, Main.getTbotConfiguration().getMessages().get("verifyWait"));
    }

}
