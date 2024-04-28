package com.gmail.genek530.common;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.text.StringSubstitutor;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MyEmbedBuilder {
    //containsData will contain option for each embed for example ${playeruuid}
    //where containsMessage contains description title and other weird stuff
    //TODO this requires a slight redo beacuse is not consistent but works
    public static MessageEmbed build(Map<String, HashMap<String,String>> containsMessage, String whatMessage, Map<String, String> containsData){
        EmbedBuilder eb = new EmbedBuilder();
        StringSubstitutor sub = new StringSubstitutor(containsData);

        String title = getKeyOrDefault(whatMessage, "title", containsMessage);
        if(title != null) eb.setTitle(sub.replace(title));

        String description = getKeyOrDefault(whatMessage, "description", containsMessage);
        if(description != null) {
            eb.setDescription(sub.replace(description));
        }

        String thumbnail = getKeyOrDefault(whatMessage, "thumbnail", containsMessage);
        if(thumbnail != null) eb.setThumbnail(sub.replace(thumbnail));

        String image = getKeyOrDefault(whatMessage, "image", containsMessage);
        if(image != null) eb.setImage(sub.replace(image));

        String color = getKeyOrDefault(whatMessage, "color", containsMessage);
        if(color != null){
            eb.setColor(Color.decode(color));
        }
        return eb.build();
    }

    //example is whatMessage syncsuccess
    //whatoption description
    //if empty then default or null
    @Nullable
    private static String getKeyOrDefault(String whatMessage, String whatOption, Map<String, HashMap<String,String>> containsMessage){
        if(containsMessage.get(whatMessage).get(whatOption) != null){
            return containsMessage.get(whatMessage).get(whatOption);
        }
        if(containsMessage.get("default").get(whatOption) != null){
            return containsMessage.get("default").get(whatOption);
        }
        return null;
    }

}

