package com.gmail.genek530.discord.userverification.discordside;

import com.gmail.genek530.discord.userverification.data.VerifiedUserDB;
import com.gmail.genek530.Main;
import com.gmail.genek530.discord.common.MyEmbedBuilder;
import com.gmail.genek530.discord.userverification.data.RequireAuthUserDB;
import com.gmail.genek530.discord.common.VerifiedUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.HashMap;

public class Unsync {
    public static void execute(SlashCommandInteractionEvent event){
        VerifiedUser verifiedUser = VerifiedUserDB.getSynchronizedUser(event.getUser().getIdLong());
        RequireAuthUserDB.removeAuthUser(event.getUser().getIdLong());
        event.replyEmbeds(MyEmbedBuilder.build(Main.getVerifyModuleConfig().getMessages(), "unsyncSuccess", new HashMap<>())).setEphemeral(true).queue();
        if(verifiedUser == null){
            return;
        }
        VerifiedUserDB.deSynchronizeUser(verifiedUser);
    }
}
