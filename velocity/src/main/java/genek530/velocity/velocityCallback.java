package genek530.velocity;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import genek530.proxyCommons.serverCallbacks;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class velocityCallback implements serverCallbacks {
    @Override
    public void sendMessagetoServer(String messadz, String server) {
        Optional<RegisteredServer> opt =  main.server.getServer(server);
        if(opt.isPresent()){
            opt.get().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(messadz));
            return;
        }
        //todo debug
    }

    @Override
    public void sendMessagewholeproxy(String messadz) {
        main.server.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(messadz));
    }


    @Override
    public void createasyncrunnablereapeating(Runnable runnable, int intervalinsec) {
        main.server.getScheduler().buildTask(main.plugin, runnable).repeat(intervalinsec, TimeUnit.SECONDS).schedule();
    }

    @Override
    public int getPlayersOnline() {
        return main.server.getPlayerCount();
    }
}
