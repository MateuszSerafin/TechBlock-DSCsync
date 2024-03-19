package genek530.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import genek530.proxyCommons.minecraft.commands.synccommand;
import genek530.velocity.main;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class syncCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        Player source = (Player) invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        if (args.length < 1) {
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("§aNie podales hasła do komendy &c:("));
            return;
        }
        String whattodo = synccommand.execute(source.getUsername(),source.getUniqueId(),args[0]);
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(genek530.proxyCommons.main.conf.getWiadomosci().get(whattodo)));
    }
}
