package com.gmail.genek530.discord;

import com.gmail.genek530.configs.BotOption;
import com.gmail.genek530.discord.configs.BotOptions;
import com.gmail.genek530.Main;
import com.gmail.genek530.modules.verificationdscmc.discord.slashcommand.Unsync;
import com.gmail.genek530.discord.userverification.discordside.Sync;
import com.gmail.genek530.discord.userverification.discordside.Unsync;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MainDiscordBot {
    private static JDA jda;

    public static void initalize() throws Exception {
        BotOptions mainBot = Main.getMainConfig().getMainBot();

        JDABuilder builder = JDABuilder.createLight(mainBot.getToken(), GatewayIntent.GUILD_MEMBERS).addEventListeners(new MainDiscordBotSlashCommands());
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setEnabledIntents(GatewayIntent.GUILD_MEMBERS);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);


        jda = builder.build();

        HashMap<String,String> syncCommand = Main.getVerifyModuleConfig().getCommands().get("sync");

        jda.upsertCommand(syncCommand.get("commandName"), syncCommand.get("commandDesc")).addOption(OptionType.STRING, syncCommand.get("argumentOne"), syncCommand.get("argumentOneDesc"), true).queue();

        HashMap<String,String> unsyncCommand =  Main.getVerifyModuleConfig().getCommands().get("unsync");

        jda.upsertCommand(unsyncCommand.get("commandName"), unsyncCommand.get("commandDesc")).queue();


        jda.upsertCommand("test", "test").queue();


    }

    public static JDA getJda() {
        return jda;
    }
}
class MainDiscordBotSlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "test":
                Main.getProxyServers().get("testproxy").testtest();
                break;
            case "sync":
                Sync.execute(event);
                break;
            case "unsync":
                Unsync.execute(event);
                break;
        }
    }
}
class DescriptionTicker implements Runnable {

    private int ticker = Main.getMainConfig().getMainBot().getChangeFrequency() * 1000 * 60;

    @Override
    public void run() {
        while(true){

            MainDiscordBot.getJda().getPresence().setActivity();


            //listening, playing, streaming, watching
            switch(mainBot.getActivityName()){
                case "listening":
                    builder.setActivity(Activity.listening(mainBot.getDesc));
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


            try {
                Thread.sleep(ticker);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}