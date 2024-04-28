package com.gmail.genek530.modules.verificationdscmc.discord.slashcommand;

import com.gmail.genek530.common.MyEmbedBuilder;
import com.gmail.genek530.modules.verificationdscmc.RequireAuthUserDB;
import com.gmail.genek530.modules.verificationdscmc.VerifiedUserDB;
import com.gmail.genek530.modules.verificationdscmc.VerifyModule;
import com.gmail.genek530.modules.verificationdscmc.common.RandomStringGen;
import com.gmail.genek530.modules.verificationdscmc.common.RequireAuthUser;
import com.gmail.genek530.modules.verificationdscmc.common.VerifiedUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Sync {
    protected static void execute(@NotNull SlashCommandInteractionEvent event){
        long userID = event.getUser().getIdLong();
        String getMCname = event.getOption(VerifyModule.getVerifyModuleConfig().getCommands().get("sync").get("argumentOne")).getAsString();
        VerifiedUser verifiedUser = VerifiedUserDB.getSynchronizedUser(userID);

        if(verifiedUser != null){
            event.replyEmbeds(MyEmbedBuilder.build(VerifyModule.getVerifyModuleConfig().getMessages(), "syncAlreadySynced", Map.of("playernick", verifiedUser.getNick(),"playeruuid", verifiedUser.getMcUUID().toString()))).setEphemeral(true).queue();
            return;
        }

        RequireAuthUser toAuth = RequireAuthUserDB.getRequireAuthUser(userID);
        if(toAuth != null){
            toAuth.setPassword(new RandomStringGen().nextString());
            toAuth.setMcNick(getMCname);
            event.replyEmbeds(MyEmbedBuilder.build(VerifyModule.getVerifyModuleConfig().getMessages(), "syncNewKey", Map.of("playernick", toAuth.getMcNick(),"key", toAuth.getPassword()))).setEphemeral(true).queue();
            return;
        }

        //user nie jest ani tu ani tu
        RequireAuthUser authUser = new RequireAuthUser(userID, getMCname, new RandomStringGen().nextString());
        RequireAuthUserDB.addUserToAuth(authUser);
        event.replyEmbeds(MyEmbedBuilder.build(VerifyModule.getVerifyModuleConfig().getMessages(), "syncLastAction", Map.of("playernick", authUser.getMcNick(),"key", authUser.getPassword()))).setEphemeral(true).queue();
    }
}
