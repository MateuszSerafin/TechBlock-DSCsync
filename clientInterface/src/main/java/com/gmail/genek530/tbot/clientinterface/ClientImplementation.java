package com.gmail.genek530.tbot.clientinterface;

import java.util.UUID;

public interface ClientImplementation {

    public void sendMessageToSpecificPlayer(UUID pUUID, String message);

    public void broadcastMessage(String message);

}
