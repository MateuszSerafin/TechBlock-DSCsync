package genek530.standAlone.discord.utils;

import genek530.standAlone.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.text.StringSubstitutor;

import java.awt.*;
import java.util.Map;

public class baseEmbed {

    public static MessageEmbed build(String crime, Map<String, String> replacable){
        EmbedBuilder eb = new EmbedBuilder();
        StringSubstitutor sub = new StringSubstitutor(replacable);


        Map<String, String> opcje = Main.conf.getWiadomosci().get(crime);

        if(opcje == null) try {
            throw new Exception("przypał exception embedBuilder");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String title = opcje.get("title");
        if(title != null) eb.setTitle(sub.replace(title));

        String description = opcje.get("description");
        if(description != null) {
            eb.setDescription(sub.replace(description));
        }

        String thumbnail = opcje.get("thumbnail");
        if(thumbnail != null) eb.setThumbnail(sub.replace(thumbnail));

        String image = opcje.get("image");
        if(image != null) eb.setImage(sub.replace(image));

        String color = opcje.get("color");
        if(color != null){
            eb.setColor(Color.decode(color));
        }
        return eb.build();
    }


}
