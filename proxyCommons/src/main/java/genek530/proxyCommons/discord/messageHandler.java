package genek530.proxyCommons.discord;

import genek530.proxyCommons.main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class messageHandler {

    private static Map<String, MessageChannel> serverNametoMesasgeChannel = new HashMap<>();
    private static Map<Long, String> discordIDtoserver = new HashMap<>();

    public static void init() throws Exception {
        Guild guild = discordContainer.getJda().getGuildById(main.conf.getGuild());
        if(guild == null){
            throw new Exception("nie ma takiego guild'a");
        }

        for (Map.Entry<String, Long> stringLongEntry : main.conf.getMsgchannelid().entrySet()) {
            MessageChannel msgchannel = guild.getTextChannelById(stringLongEntry.getValue());
            if (msgchannel == null) {
                //todo nie wiem co to ma robic nie ma jeszcze disable ogolnego ani nic XD
                discordContainer.getJda().shutdown();
                throw new Exception("Czanel jest nullem error ");
            }
            discordIDtoserver.put(stringLongEntry.getValue(), stringLongEntry.getKey());
            serverNametoMesasgeChannel.put(stringLongEntry.getKey(), msgchannel);
        }
    }

    public static void sendMessagetoDiscord(String whattosend, String whatserver){
        MessageChannel messageChannel = serverNametoMesasgeChannel.get(whatserver);
        if(messageChannel != null){
            messageChannel.sendMessage(whattosend).allowedMentions(new HashSet<>()).queue();
            return;
        }
        messageChannel = serverNametoMesasgeChannel.get("default");
        if(messageChannel != null){
            messageChannel.sendMessage(whattosend).allowedMentions(new HashSet<>()).queue();
        }
    }


    public static String getServerFromID(long serverid){
        return discordIDtoserver.get(serverid);
    }


    public static void sendDiskordTOmajkraft(String mesadz, String server){
        if(server != null){
            if(server.equals("default")){
                main.serverCallback.sendMessagewholeproxy(mesadz);
                return;
            }
            main.serverCallback.sendMessagetoServer(mesadz, server);
            return;
        }
        //todo debug

    }


}
