package genek530.proxyCommons.minecraft.commands;


import genek530.commons.internal.authUser;
import genek530.commons.internal.sharedUser;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendMultiplePermsUpdate;
import genek530.commons.redis.validActions;
import genek530.proxyCommons.Data;
import genek530.proxyCommons.main;
import genek530.proxyCommons.utils.lputil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class synccommand {
    public static String execute(String playerName, UUID playerUUID, String haslo) {
        /*
        Player source = (Player) invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        if(args.length < 1){
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Main.conf.getWiadomosci().get("mc-sync-brakhasla")));
            return;
        }

         */
        authUser checked = Data.getauthUser(playerName);
        if(checked == null){
            return "mc-sync-zlewpisanynicklubhaslo";
        }
        if(checked.matchOtherpass(haslo)){
            sharedUser hehejuzwiemyktotojest = new sharedUser(checked.getDiscordID(), playerUUID, playerName);
            Data.addUserAfterSync(hehejuzwiemyktotojest);
            main.pyrite.sendPacket(new redisPacket(main.bootUUID, "standAlone", validActions.ackAuthorization, main.gson.toJson(new sendMultiplePermsUpdate(hehejuzwiemyktotojest, lputil.getPerms(hehejuzwiemyktotojest)))), "TBOT");
            //todo rolesyncer.syncall(hehejuzwiemyktotojest);
            return "mc-sync-success";
        }
        return "mc-sync-zlykod";
    }
}
