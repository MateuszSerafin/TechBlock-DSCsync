package com.gmail.genek530.modules.verificationdscmc.discord;

import com.gmail.genek530.modules.verificationdscmc.VerifyModule;
import com.gmail.genek530.modules.verificationdscmc.VerifyModuleConfig;
import com.gmail.genek530.modules.verificationdscmc.discord.slashcommand.SlashCommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.HashMap;

public class BotContainer {
    private static JDA jda;



    public static void initalize() throws Exception {
        VerifyModuleConfig config = VerifyModule.getVerifyModuleConfig();


        JDABuilder builder = JDABuilder.createLight(config.getBotToken(), GatewayIntent.GUILD_MEMBERS).addEventListeners(new SlashCommandHandler());

        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setEnabledIntents(GatewayIntent.GUILD_MEMBERS);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);

        //listening, playing, streaming, watching
        switch(VerifyModule.getVerifyModuleConfig().getActivityName()){
            case "listening":
                builder.setActivity(Activity.listening(config.getActivityDescription()));
                break;
            case "playing":
                builder.setActivity(Activity.playing(config.getActivityDescription()));
                break;
            case "streaming":
                builder.setActivity(Activity.streaming(config.getActivityDescription(), null));
                break;
            case "watching":
                builder.setActivity(Activity.watching(config.getActivityDescription()));
                break;
            default:
                break;
        }

        jda = builder.build();

        HashMap<String,String> syncCommand = config.getCommands().get("sync");

        jda.upsertCommand(syncCommand.get("commandName"), syncCommand.get("commandDesc")).addOption(OptionType.STRING, syncCommand.get("argumentOne"), syncCommand.get("argumentOneDesc"), true).queue();

        HashMap<String,String> unsyncCommand = config.getCommands().get("unsync");

        jda.upsertCommand(unsyncCommand.get("commandName"), unsyncCommand.get("commandDesc")).queue();

    }

    public static JDA getJda() {
        return jda;
    }
}
