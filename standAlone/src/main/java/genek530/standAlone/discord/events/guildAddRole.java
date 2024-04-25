package genek530.standAlone.discord.events;

import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendPermUpdate;
import genek530.commons.redis.validActions;
import genek530.standAlone.Data;
import genek530.standAlone.Main;
import genek530.commons.internal.SynchronizedUser;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class guildAddRole extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        Main.logger.info("addrole");

        long userID = event.getUser().getIdLong();

        SynchronizedUser shareduser = Data.getsharedUser(userID);
        if(shareduser == null) return;

        List<Role> lista = event.getRoles();

        for(Role role : lista){
            if(Main.conf.getPerms().containsKey(role.getIdLong())){
                if(Data.addSkip.containsKey(userID)){
                    long rolealemoja = Data.addSkip.get(userID);
                    if(rolealemoja == role.getIdLong()){
                        Data.addSkip.remove(userID);
                        continue;
                    }
                }
                String permission = Main.conf.getPerms().get(role.getIdLong());
                Main.pyrite.sendPacket(new redisPacket("standAlone", "proxy", validActions.adddsctoMC, new sendPermUpdate(shareduser.getMcUUID(), permission)), "TBOT");
            }
        }
    }
}
