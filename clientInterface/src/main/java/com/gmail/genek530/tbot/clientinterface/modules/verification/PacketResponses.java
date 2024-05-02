package com.gmail.genek530.tbot.clientinterface.modules.verification;

import com.gmail.genek530.tbot.clientinterface.Main;
import com.gmail.genek530.tbot.clientinterface.TBOTConfiguration;
import com.google.gson.Gson;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketResponses {

    public static void handleVerify(String data){
        //failed, mcnick, mcuuid, discordname, discordid
        List<String> deSerialized = new Gson().fromJson(data, List.class);
        StringSubstitutor substitutor = new StringSubstitutor(Map.of("mcnick", deSerialized.get(1), "mcuuid", deSerialized.get(2), "discordname", deSerialized.get(3), "discordid", deSerialized.get(4)));
        UUID mcUUID = UUID.fromString(deSerialized.get(2));


        if(deSerialized.get(0).equalsIgnoreCase("fail")){
            Main.getClientImplementation().sendMessageToSpecificPlayer(mcUUID,substitutor.replace(Main.getTbotConfiguration().getMessages().get("verifyFailure")));
            return;
        }

        if(deSerialized.get(0).equalsIgnoreCase("success")){
            Main.getClientImplementation().sendMessageToSpecificPlayer(mcUUID,substitutor.replace(Main.getTbotConfiguration().getMessages().get("verifySuccess")));
            return;
        }

        try {
            throw new Exception(String.format("Problem handling packet... data is %s", data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
