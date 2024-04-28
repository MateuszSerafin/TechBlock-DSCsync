package com.gmail.genek530.modules.verificationdscmc.discord.slashcommand;

import com.gmail.genek530.common.MyEmbedBuilder;
import com.gmail.genek530.modules.verificationdscmc.RequireAuthUserDB;
import com.gmail.genek530.modules.verificationdscmc.VerifiedUserDB;
import com.gmail.genek530.modules.verificationdscmc.VerifyModule;
import com.gmail.genek530.modules.verificationdscmc.common.VerifiedUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.HashMap;

public class Unsync {
    public static void execute(SlashCommandInteractionEvent event){
        VerifiedUser verifiedUser = VerifiedUserDB.getSynchronizedUser(event.getUser().getIdLong());
        RequireAuthUserDB.removeAuthUser(event.getUser().getIdLong());
        event.replyEmbeds(MyEmbedBuilder.build(VerifyModule.getVerifyModuleConfig().getMessages(), "unsyncSuccess", new HashMap<>())).setEphemeral(true).queue();
        if(verifiedUser == null){
            return;
        }
        VerifiedUserDB.deSynchronizeUser(verifiedUser);
    }
}
