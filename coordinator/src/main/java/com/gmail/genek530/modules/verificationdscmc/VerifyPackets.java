package com.gmail.genek530.modules.verificationdscmc;

import com.gmail.genek530.Packets;
import com.gmail.genek530.common.MyEmbedBuilder;
import com.gmail.genek530.modules.verificationdscmc.common.RequireAuthUser;
import com.gmail.genek530.modules.verificationdscmc.discord.BotContainer;
import com.gmail.genek530.tbot.commons.PyritePacket;
import com.gmail.genek530.tbot.commons.ValidPackets;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VerifyPackets {

    public static void checkPassword(String destination, String data){
        Gson gson = new Gson();

        ArrayList<String> deserialized = gson.fromJson(data, ArrayList.class);

        String mcNick = deserialized.get(0);
        UUID mcUUID = UUID.fromString(deserialized.get(1));
        String password = deserialized.get(2);

        RequireAuthUser authUser = RequireAuthUserDB.getRequireAuthUser(mcNick);

        //failed, mcnick, mcuuid, discordname, discordid
        if(authUser == null){
            Packets.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("fail", mcNick, mcUUID.toString(),"empty","empty")));
            return;
        }

        if(!authUser.getMcNick().equals(mcNick)){
            Packets.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("fail", mcNick, mcUUID.toString(),"empty","empty")));
            return;
        }

        if(!authUser.getPassword().equals(password)){
            Packets.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("fail", mcNick, mcUUID.toString(),"empty","empty")));
            return;
        }

        User dscUser =  BotContainer.getJda().retrieveUserById(authUser.getDiscordID()).complete();

        Packets.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("success", mcNick, mcUUID.toString(), dscUser.getName(), authUser.getDiscordID())));

        dscUser.openPrivateChannel().complete().sendMessageEmbeds(MyEmbedBuilder.build(VerifyModule.getVerifyModuleConfig().getMessages(), "syncSuccess", Map.of("playernick", mcNick))).queue();

        RequireAuthUserDB.removeAuthUser(authUser);
        VerifiedUserDB.SynchronizeUser(authUser, mcUUID);

    }




}
