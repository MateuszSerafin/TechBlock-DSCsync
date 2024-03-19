package genek530.proxyCommons.discord.util;

import genek530.commons.internal.sharedUser;
import genek530.proxyCommons.main;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class messageBuilder {
    public static String mctodscUnsync(String mcNick, String message, String server){
        Map<String, String> values = new HashMap<>();
        values.put("nick", mcNick);
        values.put("message", message);
        values.put("server", server);
        StringSubstitutor sub = new StringSubstitutor(values);
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("mctodscniesynced"));
    }

    public static String mctodscsync(String mcNick, long discordID, String message, String server){
        Map<String, String> values = new HashMap<>();
        values.put("nick", mcNick);
        values.put("message", message);
        //crime
        values.put("dscmention", "<@" + String.valueOf(discordID) + ">");
        values.put("server", server);
        StringSubstitutor sub = new StringSubstitutor(values);
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("mctodscsynced"));
    }

    public static String dsctoMCunsyncunknownserver(String dscnick, String message){
        Map<String, String> values = new HashMap<>();
        values.put("dscnick", dscnick);
        values.put("message", message);


        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("dsctomcniesynced"));
    }



    public static String dsctoMCsyncedunknownserver(String dscnick, String mcnick, String message){
        Map<String, String> values = new HashMap<>();
        values.put("dscnick", dscnick);
        values.put("mcnick", mcnick);
        values.put("message", message);
        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("dsctomcsynced"));
    }


    public static String dsctoMCunsync(String dscnick, String message, String server){
        Map<String, String> values = new HashMap<>();
        values.put("dscnick", dscnick);
        values.put("message", message);
        values.put("server", server);


        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("dsctomcniesynced"));
    }
    public static String dsctoMCsynced(String dscnick, String mcnick, String message, String server){
        Map<String, String> values = new HashMap<>();
        values.put("dscnick", dscnick);
        values.put("mcnick", mcnick);
        values.put("message", message);
        values.put("server", server);
        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("dsctomcsynced"));
    }

    public static String joinedServer(String mcNick, String server){
        Map<String, String> values = new HashMap<>();
        values.put("mcnick", mcNick);
        values.put("server", server);
        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("jointomcunsynced"));
    }

    public static String joinedServer(sharedUser sharedusr, String server){
        Map<String, String> values = new HashMap<>();
        values.put("mcnick", sharedusr.getNick());
        values.put("server", server);
        values.put("dscmention", "<@" + String.valueOf(sharedusr.getDiscordID()) + ">");

        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("jointomcsynced"));
    }

    public static String changedServer(String mcNick, String server, String previousserver){
        Map<String, String> values = new HashMap<>();
        values.put("mcnick", mcNick);
        values.put("server", server);
        values.put("previousserver", previousserver);
        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("changedserverunsynced"));

    }
    public static String changedServer(sharedUser sharedusr, String server, String previousserver){
        Map<String, String> values = new HashMap<>();
        values.put("mcnick", sharedusr.getNick());
        values.put("server", server);
        values.put("dscmention", "<@" + String.valueOf(sharedusr.getDiscordID()) + ">");
        values.put("previousserver", previousserver);

        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("changedserversynced"));
    }

    public static String quitserver(String mcnick, String server){
        Map<String, String> values = new HashMap<>();
        values.put("mcnick", mcnick);
        values.put("server", server);
        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("playerquitunsynced"));
    }
    public static String quitserver(sharedUser sharedUser, String server){
        Map<String, String> values = new HashMap<>();
        values.put("mcnick", sharedUser.getNick());
        values.put("dscmention", "<@" + String.valueOf(sharedUser.getDiscordID()) + ">");
        values.put("server", server);

        StringSubstitutor sub = new StringSubstitutor(values);
        //nie returnowac nie stringa bo chce trzymac to jako common. Net.kyori.adventure maybe by dzialal ale idk czy bungee to ogarnia
        return sub.replace(main.conf.getSynchroczatuwiadomosci().get("playerquitsynced"));
    }






}
