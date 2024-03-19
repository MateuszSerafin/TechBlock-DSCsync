package genek530.proxyCommons.discord.event;

import genek530.commons.internal.sharedUser;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.discord.messageHandler;
import genek530.proxyCommons.discord.util.messageBuilder;
import genek530.proxyCommons.main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class onMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //nie trzeba sprawdzac czy isczatsynchro
        if(event.getAuthor().isBot()) return;
        if(!event.isFromGuild()) return;
        if(event.getGuild().getIdLong() != main.conf.getGuild()) return;
        if(!main.conf.getMsgchannelid().containsValue(event.getChannel().getIdLong())) return;

        String server = messageHandler.getServerFromID(event.getChannel().getIdLong());
        sharedUser shrdUsr = Data.getsharedUser(event.getAuthor().getIdLong());

        if(server.equals("default")){
            if(shrdUsr == null){
                messageHandler.sendDiskordTOmajkraft(messageBuilder.dsctoMCunsyncunknownserver(event.getAuthor().getName(), event.getMessage().getContentRaw()), server);
                return;
            }
            messageHandler.sendDiskordTOmajkraft(messageBuilder.dsctoMCsyncedunknownserver(event.getAuthor().getName(),shrdUsr.getNick(), event.getMessage().getContentRaw()), server);
            return;
        }

        if(shrdUsr == null){
            messageHandler.sendDiskordTOmajkraft(messageBuilder.dsctoMCunsync(event.getAuthor().getName(), event.getMessage().getContentRaw(), server), server);
            return;
        }
        messageHandler.sendDiskordTOmajkraft(messageBuilder.dsctoMCsynced(event.getAuthor().getName(),shrdUsr.getNick(), event.getMessage().getContentRaw(), server), server);

    }
}
