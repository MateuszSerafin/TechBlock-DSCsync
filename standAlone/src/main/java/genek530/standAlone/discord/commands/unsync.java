package genek530.standAlone.discord.commands;

import genek530.commons.redis.redisPacket;
import genek530.commons.redis.validActions;
import genek530.standAlone.Data;
import genek530.standAlone.Main;
import genek530.standAlone.actions;
import genek530.standAlone.discord.utils.baseEmbed;
import genek530.standAlone.hujowecommonsy.rolesyncer;
import genek530.commons.internal.sharedUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.Map;

public class unsync {
    public static void execute(SlashCommandInteractionEvent event){
        sharedUser syncuser = Data.getsharedUser(event.getUser().getIdLong());
        if(syncuser == null){
            event.replyEmbeds(baseEmbed.build("dsc-unsync-niejestsync", new HashMap<>())).setEphemeral(true).queue();
            return;
        }
        //todo rolesyncer.unsync(syncuser);
        actions.desyncUser(syncuser);
        Main.pyrite.sendPacket(new redisPacket("standAlone", "broadcast", validActions.desynchronizeuser, Main.gson.toJson(syncuser.getMcUUID())), "TBOT");

        event.replyEmbeds(baseEmbed.build("dsc-unsync-niejestsync", Map.of("playernick", syncuser.getNick(), "playeruuid", syncuser.getMcUUID().toString()))).setEphemeral(true).queue();
    }
}
