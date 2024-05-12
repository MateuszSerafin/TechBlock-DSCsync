package com.gmail.genek530.discord.userverification.mcside;

import com.gmail.genek530.Main;
import com.gmail.genek530.minecraft.PacketRequests;
import com.gmail.genek530.discord.common.MyEmbedBuilder;
import com.gmail.genek530.discord.MainDiscordBot;
import com.gmail.genek530.discord.common.RequireAuthUser;
import com.gmail.genek530.discord.userverification.data.RequireAuthUserDB;
import com.gmail.genek530.tbot.commons.ValidPackets;
import com.gmail.genek530.discord.userverification.data.VerifiedUserDB;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReceivedVerifyPackets {

    public static void checkPassword(String destination, String data){
        Gson gson = new Gson();

        ArrayList<String> deserialized = gson.fromJson(data, ArrayList.class);

        String mcNick = deserialized.get(0);
        UUID mcUUID = UUID.fromString(deserialized.get(1));
        String password = deserialized.get(2);

        RequireAuthUser authUser = RequireAuthUserDB.getRequireAuthUser(mcNick);

        //failed, mcnick, mcuuid, discordname, discordid
        if(authUser == null){
            PacketRequests.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("fail", mcNick, mcUUID.toString(),"empty","empty")));
            return;
        }

        if(!authUser.getMcNick().equals(mcNick)){
            PacketRequests.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("fail", mcNick, mcUUID.toString(),"empty","empty")));
            return;
        }

        if(!authUser.getPassword().equals(password)){
            PacketRequests.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("fail", mcNick, mcUUID.toString(),"empty","empty")));
            return;
        }

        User dscUser =  MainDiscordBot.getJda().retrieveUserById(authUser.getDiscordID()).complete();

        PacketRequests.sendToSpecificClient(destination, ValidPackets.checkResponse, gson.toJson(List.of("success", mcNick, mcUUID.toString(), dscUser.getName(), authUser.getDiscordID())));

        dscUser.openPrivateChannel().complete().sendMessageEmbeds(MyEmbedBuilder.build(Main.getVerifyModuleConfig().getMessages(), "syncSuccess", Map.of("playernick", mcNick))).queue();

        RequireAuthUserDB.removeAuthUser(authUser);
        VerifiedUserDB.SynchronizeUser(authUser, mcUUID);

    }




}
