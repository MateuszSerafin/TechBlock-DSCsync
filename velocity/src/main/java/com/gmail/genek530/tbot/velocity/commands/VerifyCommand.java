package com.gmail.genek530.tbot.velocity.commands;

import com.gmail.genek530.tbot.clientinterface.Main;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class VerifyCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        Player source = (Player) invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();
        if (args.length < 1) {
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Main.getTbotConfiguration().getMessages().get("verifyNotPassword")));
            return;
        }
        com.gmail.genek530.tbot.clientinterface.modules.verification.UserCommands.syncCommand(source.getUsername(), source.getUniqueId(), args[0]);
        return;
    }
}
