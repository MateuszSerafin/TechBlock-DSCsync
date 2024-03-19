package genek530.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import genek530.proxyCommons.minecraft.events.onChatEvent;


public class events {
    @Subscribe
    public void onPostLoginEvent(PostLoginEvent event){
        //genek530.proxyCommons.minecraft.events.joinEvent.onPostLoginEvent(event.getPlayer().getUniqueId());
    }

    @Subscribe
    public void onPlayerChatEvent(PlayerChatEvent event){
        onChatEvent.chat(event.getPlayer().getUsername(), event.getPlayer().getUniqueId(), event.getMessage(), event.getPlayer().getCurrentServer().get().getServerInfo().getName());
    }

    @Subscribe
    public void onServerConnectedEvent(ServerConnectedEvent event){
        if(event.getPreviousServer().isPresent()){
            genek530.proxyCommons.minecraft.events.onServerjoinchange.handle(event.getPlayer().getUniqueId(), event.getPlayer().getUsername(), event.getServer().getServerInfo().getName(), event.getPreviousServer().get().getServerInfo().getName());
            return;
        }
        genek530.proxyCommons.minecraft.events.onServerjoinchange.handle(event.getPlayer().getUniqueId(), event.getPlayer().getUsername(), event.getServer().getServerInfo().getName());

    }
    @Subscribe
    public void onDisconnectEvent(DisconnectEvent event){
        genek530.proxyCommons.minecraft.events.onPlayerLeave.handle(event.getPlayer().getUsername(),event.getPlayer().getUniqueId(), event.getPlayer().getCurrentServer().get().getServerInfo().getName());
    }

}
