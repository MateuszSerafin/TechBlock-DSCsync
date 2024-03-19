package genek530.standAlone.discord.commands;

import genek530.standAlone.Data;
import genek530.standAlone.Main;
import genek530.commons.internal.authUser;
import genek530.commons.internal.sharedUser;
import genek530.standAlone.actions;
import genek530.standAlone.discord.utils.baseEmbed;
import genek530.standAlone.hujowecommonsy.RandomString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Map;

public class sync {
    public static void execute(SlashCommandInteractionEvent event){
        long userID = event.getUser().getIdLong();
        String getMCname = event.getOption("nick").getAsString();
        sharedUser synceduser = Data.getsharedUser(userID);

        Main.logger.info(Data.idlookup.toString());

        if(synceduser != null){
            event.replyEmbeds(baseEmbed.build("dsc-sync-juzsynced", Map.of("playernick", synceduser.getNick(),"playeruuid", synceduser.getMcUUID().toString()))).setEphemeral(true).queue();
            return;
        }

        authUser toauth = Data.getauthUser(event.getUser().getIdLong());
        if(toauth != null){
            toauth.setPassword(new RandomString().nextString());
            toauth.setMcNick(getMCname);
            actions.authentication(toauth);
            event.replyEmbeds(baseEmbed.build("dsc-sync-makluczaleniejestsynced", Map.of("playernick", toauth.getMcNick(),"klucz", toauth.getPassword()))).setEphemeral(true).queue();
            return;
        }


        //user nie jest ani tu ani tu
        toauth = new authUser(event.getUser().getIdLong(), getMCname, new RandomString().nextString());
        actions.authentication(toauth);
        event.replyEmbeds(baseEmbed.build("dsc-sync-ostatnietap", Map.of("playernick", toauth.getMcNick(),"klucz", toauth.getPassword()))).setEphemeral(true).queue();
    }
}
