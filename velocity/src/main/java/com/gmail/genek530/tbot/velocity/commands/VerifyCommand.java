package com.gmail.genek530.tbot.velocity.commands;

import com.gmail.genek530.tbot.clientinterface.Main;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class VerifyCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        Player source = (Player) invocation.source();
        String[] args = invocation.arguments();
        com.gmail.genek530.tbot.clientinterface.modules.verification.UserCommands.syncCommand(source.getUsername(), source.getUniqueId(), args);
    }
}
