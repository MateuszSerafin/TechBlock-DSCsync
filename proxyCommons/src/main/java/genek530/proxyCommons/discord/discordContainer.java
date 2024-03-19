package genek530.proxyCommons.discord;

import genek530.proxyCommons.discord.event.onMessage;
import genek530.proxyCommons.main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class discordContainer {


    //todo nie wiem jak juz to pisalem to caly pod wymaga refactora XDD w sensie zle nie jest layoutowo ale jest gówno w plikach XD



    private static JDA jda;

    public static void setJda(JDA jda) {
        discordContainer.jda = jda;
    }

    public static JDA getJda() {
        return jda;
    }

    public static void initalize() throws Exception {

        JDABuilder builder = JDABuilder.createLight(main.conf.getToken(), GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES).addEventListeners(new onMessage());

        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        //incase
        builder.setEnabledIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")


        jda = builder.build();

        boolean flag = true;
        jda.awaitReady();

        main.serverCallback.createasyncrunnablereapeating(new Runnable() {
            private String build(String input){
                Map<String, String> values = new HashMap<>();
                values.put("players", String.valueOf(main.serverCallback.getPlayersOnline()));
                StringSubstitutor sub = new StringSubstitutor(values);
                //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
                return sub.replace(input);
            }
            @Override
            public void run() {
                for (String s : main.conf.getActivity()) {
                    jda.getPresence().setActivity(Activity.listening(build(s)));
                    try {
                        Thread.sleep(30*1000);
                    } catch (InterruptedException e) {
                        //to bedzie sie callowac jak proxy bedzie umierac
                        return;
                    }
                }
            }
        }, 30);

        for(Guild guild : jda.getGuilds()){
            if(guild.getIdLong() == main.conf.getGuild()){
                flag = false;
            }
        }
        if(flag) throw new Exception("Bot nie jest w guildzie z configa error");
        messageHandler.init();
        }
    }