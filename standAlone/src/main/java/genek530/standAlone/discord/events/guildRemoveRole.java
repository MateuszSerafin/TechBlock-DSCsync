package genek530.standAlone.discord.events;

import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendPermUpdate;
import genek530.commons.redis.validActions;
import genek530.standAlone.Data;
import genek530.standAlone.Main;
import genek530.commons.internal.SynchronizedUser;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class guildRemoveRole extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        Main.logger.info("remrole");
        long userID = event.getUser().getIdLong();
        SynchronizedUser shareduser = Data.getsharedUser(userID);
        if(shareduser == null) return;
        List<Role> lista = event.getRoles();

        for(Role role : lista){
            if(Main.conf.getPerms().containsKey(role.getIdLong())){
                if(Data.remSkip.containsKey(userID)){
                    long rolealemoja = Data.remSkip.get(userID);
                    if(rolealemoja == role.getIdLong()){
                        Data.remSkip.remove(userID);
                        continue;
                    }
                }
                String permission = Main.conf.getPerms().get(role.getIdLong());
                Main.pyrite.sendPacket(new redisPacket("standAlone", "proxy", validActions.remdsctoMC, new sendPermUpdate(shareduser.getMcUUID(), permission)), "TBOT");
            }
        }
    }
}
