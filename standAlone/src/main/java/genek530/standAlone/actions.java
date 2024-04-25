package genek530.standAlone;

import genek530.commons.internal.RequiresSynchUser;
import genek530.commons.internal.SynchronizedUser;
import genek530.commons.internal.updateData;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.sendMultiplePermsUpdate;
import genek530.commons.redis.sendPermUpdate;
import genek530.commons.redis.validActions;
import genek530.standAlone.discord.discordContainer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class actions {

    //kiedy authuser jest potwierdzony na jakims proxy to jest callowane
    public static void userJustSynchronized(Object sendMultiplePermsipromise){
        assert sendMultiplePermsipromise instanceof String;
        sendMultiplePermsUpdate instance = Main.gson.fromJson(sendMultiplePermsipromise.toString(), sendMultiplePermsUpdate.class);

        Data.userJustSynchronized(instance.getSharedUser(true));
        Main.pyrite.sendPacket(new redisPacket("standAlone", "broadcast", validActions.createSyncedUser, Main.gson.toJson(instance)), "TBOT");
        actions.syncUser(sendMultiplePermsipromise);
    }


    //kiedy zmieniane jest haslo,lub jest initial tworzenie usera
    public static void authentication(RequiresSynchUser RequiresSynchUser){
        Data.removetempauth(RequiresSynchUser);
        Data.addauthUser(RequiresSynchUser);
        Main.pyrite.sendPacket(new redisPacket("standAlone", "broadcast", validActions.updateAuthenticatedUsers, Main.gson.toJson(RequiresSynchUser)), "TBOT");
    }



    public static void desyncUser(SynchronizedUser SynchronizedUser){
        Guild guild = discordContainer.getJda().getGuildById(Main.conf.getGuild());
        Member member = guild.getMemberById(SynchronizedUser.getDiscordID());
        if(member == null){
            Main.logger.info("Member null");
            return;
        }
        List<Role> roleList = member.getRoles();

        for(Map.Entry<Long, String> entry : Main.conf.perms.entrySet()){
            for (Role role : roleList) {
                if(role.getIdLong() == entry.getKey()){
                    guild.removeRoleFromMember(member, guild.getRoleById(entry.getKey())).queue();
                }
            }
        }

        Data.desyncUser(SynchronizedUser);
    }
    public static void syncUser(Object sendMultiplePermsUpdateipromise){
        assert  sendMultiplePermsUpdateipromise instanceof String;

        sendMultiplePermsUpdate multiplePerms = Main.gson.fromJson(sendMultiplePermsUpdateipromise.toString(), sendMultiplePermsUpdate.class);

        SynchronizedUser SynchronizedUser = Data.getsharedUser(multiplePerms.getMcuuid());
        if(SynchronizedUser == null) {
            Main.logger.info(String.format("CANNOT SYNC USER shared User doesnt exist "));
            return;
        }

        Guild guild = discordContainer.getJda().getGuildById(Main.conf.getGuild());
        Member member = guild.getMemberById(SynchronizedUser.getDiscordID());
        if(member == null){
            Main.logger.info("Member null");
            return;
        }

        List<Role> roleList = member.getRoles();

        //nie wiem co to jest Xd
        for(Map.Entry<Long, String> entry : Main.conf.perms.entrySet()){
            boolean mapermisije = multiplePerms.getPermsList().contains(entry.getValue());

            boolean mapermisjeandsc = false;

            for (Role role : roleList) {
                if(entry.getKey().equals(role.getIdLong())){
                    mapermisjeandsc = true;
                }
            }

            if(mapermisije && !mapermisjeandsc){
                guild.addRoleToMember(member, guild.getRoleById(entry.getKey())).queue();
            }
            if(!mapermisije && mapermisjeandsc){
                Main.pyrite.sendPacket(new redisPacket("standAlone", "broadcast", validActions.adddsctoMC, new sendPermUpdate(SynchronizedUser.getMcUUID(), entry.getValue())), "TBOT");
            }
        }

    }


    public static void addMCtodsc(Object sendPermUpdateipromise){
        assert  sendPermUpdateipromise instanceof String;
        sendPermUpdate discordID = Main.gson.fromJson(sendPermUpdateipromise.toString(), sendPermUpdate.class);
        SynchronizedUser SynchronizedUser = Data.getsharedUser(discordID.getMcUUID());
        if(SynchronizedUser == null) return;

        Guild guild = discordContainer.getJda().getGuildById(Main.conf.getGuild());

        Main.logger.info("1");

        Member member = guild.getMemberById(SynchronizedUser.getDiscordID());
        if(member == null) return;
        Role role = guild.getRoleById(subUtils.getRolefromstring(discordID.getPermission()));

        Main.logger.info("2");

        if(role == null) {
            Main.logger.info("ROLE NOT FOUND");
            return;
        }

        Main.logger.info("3");

        Data.addSkip.put(SynchronizedUser.getDiscordID(), role.getIdLong());
        guild.addRoleToMember(member, role).queue();
    }



    public static void remMCtodsc(Object sendPermUpdateipromise){
        assert  sendPermUpdateipromise instanceof String;
        sendPermUpdate discordID = Main.gson.fromJson(sendPermUpdateipromise.toString(), sendPermUpdate.class);
        SynchronizedUser SynchronizedUser = Data.getsharedUser(discordID.getMcUUID());
        if(SynchronizedUser == null) return;

        Guild guild = discordContainer.getJda().getGuildById(Main.conf.getGuild());

        Member member = guild.getMemberById(SynchronizedUser.getDiscordID());
        if(member == null) return;
        Role role = guild.getRoleById(subUtils.getRolefromstring(discordID.getPermission()));
        if(role == null) {
            Main.logger.info("ROLE NOT FOUND");
            return;
        }
        Data.remSkip.put(SynchronizedUser.getDiscordID(), role.getIdLong());
        guild.removeRoleFromMember(member, role).queue();
    }


    //kiedy proxy Startuje potrzebuje zaciagnac info jacy userzy sa synced nie synced.
    public static updateData updateData(){
        //todo permissions
        return new updateData(Data.sharedUserList(true), Data.authUserList(true), new ArrayList<>(Main.conf.perms.values()));
    }


} class subUtils {
    @Nullable
    public static Long getRolefromstring(String whatrole){
        for (Map.Entry<Long, String> entry : Main.conf.getPerms().entrySet()) {
            if(entry.getValue().equals(whatrole)) return entry.getKey();
        }
        return null;
    }
}
