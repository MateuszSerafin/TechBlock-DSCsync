package com.gmail.genek530.modules.verificationdscmc.discord.slashcommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandHandler extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "sync":
                Sync.execute(event);
                break;
            case "unsync":
                Unsync.execute(event);
                break;
        }
    }
}
