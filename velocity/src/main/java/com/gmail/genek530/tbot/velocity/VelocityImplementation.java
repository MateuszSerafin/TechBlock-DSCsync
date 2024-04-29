package com.gmail.genek530.tbot.velocity;

import com.gmail.genek530.tbot.clientinterface.ClientImplementation;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

public class VelocityImplementation implements ClientImplementation {

    @Override
    public void sendMessageToSpecificPlayer(UUID pUUID, String message) {
        Main.getServer().getPlayer(pUUID).ifPresent(p -> p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message)));
    }

    @Override
    public void broadcastMessage(String message) {
        Main.getServer().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }
}
