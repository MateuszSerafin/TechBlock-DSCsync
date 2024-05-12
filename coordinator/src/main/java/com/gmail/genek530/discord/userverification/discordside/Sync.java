package com.gmail.genek530.discord.userverification.discordside;

import com.gmail.genek530.discord.userverification.data.VerifiedUserDB;
import com.gmail.genek530.Main;
import com.gmail.genek530.discord.common.MyEmbedBuilder;
import com.gmail.genek530.discord.userverification.data.RequireAuthUserDB;
import com.gmail.genek530.discord.common.RandomStringGen;
import com.gmail.genek530.discord.common.RequireAuthUser;
import com.gmail.genek530.discord.common.VerifiedUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Sync {
    public static void execute(@NotNull SlashCommandInteractionEvent event){
        long userID = event.getUser().getIdLong();
        String getMCname = event.getOption(Main.getVerifyModuleConfig().getCommands().get("sync").get("argumentOne")).getAsString();
        VerifiedUser verifiedUser = VerifiedUserDB.getSynchronizedUser(userID);

        if(verifiedUser != null){
            event.replyEmbeds(MyEmbedBuilder.build(Main.getVerifyModuleConfig().getMessages(), "syncAlreadySynced", Map.of("playernick", verifiedUser.getNick(),"playeruuid", verifiedUser.getMcUUID().toString()))).setEphemeral(true).queue();
            return;
        }

        RequireAuthUser toAuth = RequireAuthUserDB.getRequireAuthUser(userID);
        if(toAuth != null){
            toAuth.setPassword(new RandomStringGen().nextString());
            toAuth.setMcNick(getMCname);
            event.replyEmbeds(MyEmbedBuilder.build(Main.getVerifyModuleConfig().getMessages(), "syncNewKey", Map.of("playernick", toAuth.getMcNick(),"key", toAuth.getPassword()))).setEphemeral(true).queue();
            return;
        }

        //user nie jest ani tu ani tu
        RequireAuthUser authUser = new RequireAuthUser(userID, getMCname, new RandomStringGen().nextString());
        RequireAuthUserDB.addUserToAuth(authUser);
        event.replyEmbeds(MyEmbedBuilder.build(Main.getVerifyModuleConfig().getMessages(), "syncLastAction", Map.of("playernick", authUser.getMcNick(),"key", authUser.getPassword()))).setEphemeral(true).queue();
    }
}
