package genek530.standAlone.hujowecommonsy;

import genek530.commons.internal.sharedUser;
import genek530.standAlone.Data;
import genek530.standAlone.Main;
import genek530.standAlone.discord.discordContainer;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public class rolesyncer {

    /*
    public static void syncall(sharedUser synchronizedUser){
        JDA jda = discordContainer.getJda();
        Guild guild = jda.getGuildById(Main.conf.getGuild());

        //todo jak ktos wywali bota podczas dzailania to bedzie przyps
        assert guild != null;

        //todo to trzeba sprawdzic czy dziala tak jak mysle jak nie to przypal
        Member memberUser = null;

        try {
            memberUser = guild.retrieveMemberById(synchronizedUser.getDiscordID(), true).complete();
        } catch (Exception e){
            //teoretycznie jezeli ErrorHandler z tej jebanej jda da errora to tutaj usera nie ma w guildie co nie a nie np. bedzie nullem objekt wyzej co nie no to ja tak mysle (nie mam czasu sprawdzic)



            Data.desyncUser(synchronizedUser);
            Main.logger.info("Desynchronizacja " + synchronizedUser.getNick() + " Powód: wyjscie z dsc");
            return;
        }


        List<Role> roleList = memberUser.getRoles();

        Main.logger.info(String.valueOf(guild.loadMembers().get().size()));


        LuckPerms lpapi = minecraftContainer.api;
        try{
            User mcUser = lpapi.getUserManager().loadUser(synchronizedUser.mcUUID).get();
            @NonNull @Unmodifiable Collection<Node> mcUserPermissions = mcUser.getNodes();


            boolean save = false;

            for (Node node : mcUserPermissions) {
                Main.logger.info(node.getKey() + "KUUUURWA");
            }

            //nie wiem co to jest Xd
            for(Map.Entry<Long, String> entry : Main.conf.perms.entrySet()){
                boolean mapermisije = permissionCHECK.permissioncheck(mcUserPermissions, entry.getValue());

                boolean mapermisjeandsc = false;

                for (Role role : roleList) {
                    if(entry.getKey().equals(role.getIdLong())){
                        mapermisjeandsc = true;
                    }
                }

                if(mapermisije && !mapermisjeandsc){
                    guild.addRoleToMember(memberUser, guild.getRoleById(entry.getKey())).queue();
                }
                if(!mapermisije && mapermisjeandsc){
                    mcUser.data().add(Node.builder(entry.getValue()).build());
                    save = true;
                }
            }

            if(save){
                lpapi.getUserManager().saveUser(mcUser);
            }
        } catch (Exception e){
            //realnie jak tutaj wywali problem z lp api to jest sraka perhaps crash calego pluginu powinien lecicec
            e.printStackTrace();
        }
    }



    public static void addPermissiontouser(String whatpermission, UUID mcuuid){
        LuckPerms lpapi = minecraftContainer.api;
        try {
            User mcUser = lpapi.getUserManager().loadUser(mcuuid).get();
            mcUser.data().add(Node.builder(whatpermission).build());
            lpapi.getUserManager().saveUser(mcUser);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void removePermissiontouser(String whatpermission, UUID mcuuid){
        LuckPerms lpapi = minecraftContainer.api;
        try {
            User mcUser = lpapi.getUserManager().loadUser(mcuuid).get();
            mcUser.data().remove(Node.builder(whatpermission).build());
            lpapi.getUserManager().saveUser(mcUser);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void removePermissionDSC(long whatrole, long discordID){
        JDA jda = discordContainer.getJda();

        Guild guild = jda.getGuildById(Main.conf.getGuild());
        guild.removeRoleFromMember(guild.getMemberById(discordID), guild.getRoleById(whatrole)).queue();
    }
    public static void addPermissionDSC(long whatrole, long discordID){
        JDA jda = discordContainer.getJda();

        Guild guild = jda.getGuildById(Main.conf.getGuild());
        guild.addRoleToMember(guild.getMemberById(discordID), guild.getRoleById(whatrole)).queue();
    }

    private static void unsyncmc(UUID mcuuid){
        LuckPerms lpapi = minecraftContainer.api;
        try{
            User mcUser = lpapi.getUserManager().loadUser(mcuuid).get();
            @NonNull @Unmodifiable Collection<Node> mcUserPermissions = mcUser.getNodes();
            boolean save = false;

            for (Node node : mcUserPermissions) {
                if(Main.conf.perms.containsValue(node.getKey())){
                    mcUser.data().remove(node);
                    save = true;
                }
            }
            if(save){
                lpapi.getUserManager().saveUser(mcUser);
            }
    } catch (Exception e){

        }
    }

    private static void unsyncdsc(long dscID){
        JDA jda  = discordContainer.getJda();

        Guild guild = jda.getGuildById(Main.conf.getGuild());
        //jezeli nie ma usera to wywalamy mu tylko permissije z mc
        Member memberUser = null;
        try {
            memberUser = guild.retrieveMemberById(dscID, true).complete();
        } catch (Exception e){
            //teoretycznie jezeli ErrorHandler z tej jebanej jda da errora to tutaj usera nie ma w guildie co nie a nie np. bedzie nullem objekt wyzej co nie no to ja tak mysle (nie mam czasu sprawdzic)
        }

        for(Role role : memberUser.getRoles()){
            if(Main.conf.perms.containsKey(role.getIdLong())){
                guild.removeRoleFromMember(memberUser, role).queue();
            }
        }

    }

    //wywalamy wszystkiepermissije
    public static void unsync(sharedUser sharedUser){
        unsyncmc(sharedUser.mcUUID);
        unsyncdsc(sharedUser.discordID);
    }


     */

}
