package genek530.standAlone.discord;

import genek530.standAlone.Main;
import genek530.standAlone.discord.commands.commandHandler;
import genek530.standAlone.discord.events.guildAddRole;
import genek530.standAlone.discord.events.guildRemoveRole;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class discordContainer {

    private static JDA jda;

    public static void setJda(JDA jda) {
        discordContainer.jda = jda;
    }

    public static JDA getJda() {
        return jda;
    }

    public static void initalize() throws Exception {

        JDABuilder builder = JDABuilder.createLight(Main.conf.getToken(), GatewayIntent.GUILD_MEMBERS).addEventListeners(new commandHandler(), new guildAddRole(), new guildRemoveRole());

        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        //incase
        builder.setEnabledIntents(GatewayIntent.GUILD_MEMBERS);
        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.listening(Main.conf.getActivity()));

        jda = builder.build();


        boolean flag = true;
        jda.awaitReady();
        for(Guild guild : jda.getGuilds()){
            if(guild.getIdLong() == Main.conf.getGuild()){
                flag = false;
            }
        }
        if(flag) throw new Exception("Bot nie jest w guildzie z configa error");

        jda.upsertCommand("sync", "Synchronizuj swoje konto z MC").addOption(OptionType.STRING, "nick", "Twoj nick w mc", true).queue();
        jda.upsertCommand("unsync", "Zdesynchronizuj twoje konto dsc z mc").addOption(OptionType.STRING, "nick", "Twoj nick w mc", false).queue();

    }
}
