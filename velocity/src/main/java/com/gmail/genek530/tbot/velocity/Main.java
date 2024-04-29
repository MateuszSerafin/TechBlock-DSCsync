package com.gmail.genek530.tbot.velocity;

import com.gmail.genek530.tbot.velocity.commands.VerifyCommand;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Logger;

@Plugin(id = "tbot", name = "TBOT", version = "0.0.1-ALPHA",
        url = "https://techblock.pl", description = "Brak opisu :)", authors = {"genek530"}, dependencies = {@Dependency(id = "luckperms", optional = true)})
public class Main {
    private static ProxyServer server;
    private static Logger logger;

    //server plugin folder
    private static Path dataDir;

    private static VelocityImplementation velocityImplementation = new VelocityImplementation();

    public static Main plugin;


    @Inject
    public Main(ProxyServer serverVar, Logger loggerVar, @DataDirectory Path dataDirectoryVar) {
        server = serverVar;
        logger = loggerVar;
        dataDir = dataDirectoryVar;
        plugin = this;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) throws Exception {
        com.gmail.genek530.tbot.clientinterface.Main.init(dataDir.toFile(), logger, velocityImplementation);


        HashMap<String,String> commandAliases = com.gmail.genek530.tbot.clientinterface.Main.getTbotConfiguration().getCommandAlias();
        CommandManager commandManager = server.getCommandManager();

        CommandMeta commandMeta = commandManager.metaBuilder(commandAliases.get("verify")).plugin(this).build();
        commandManager.register(commandMeta, new VerifyCommand());
        //server.getEventManager().register(this, new events());
    }

    @Subscribe
    public void onProxyShutdownEvent(ProxyShutdownEvent event){
        //todo
    }

    public static ProxyServer getServer(){
        return server;
    }

}
