package com.gmail.genek530.tbot.velocity;

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
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;

@Plugin(id = "TBOT", name = "TBOT", version = "0.0.1-ALPHA",
        url = "https://techblock.pl", description = "Brak opisu :)", authors = {"genek530"}, dependencies = {@Dependency(id = "luckperms", optional = true)})
public class Main {
    public static ProxyServer server;
    public static Logger logger;

    //server plugin folder
    public static Path dataDir;

    public static configWazparser conf;

    public static Main plugin;


    @Inject
    public Main(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        server = server;
        main.logger = logger;
        main.dataDir = dataDirectory;
        plugin = this;
    }


    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) throws Exception {

        com.gmail.genek530.tbot.clientinterface.Main.init(dataDir.toFile(), logger,this, new velocityCallback());

        CommandManager commandManager = main.server.getCommandManager();
        // Here you can add meta for the command, as aliases and the plugin to which it belongs (RECOMMENDED)
        CommandMeta commandMeta = commandManager.metaBuilder("discordsync")
                // This will create a new alias for the command "/test"
                // with the same arguments and functionality
                .plugin(this)
                .build();
        commandManager.register(commandMeta, new syncCommand());
        //server.getEventManager().register(this, new events());
        //initializeBulk();
    }

    @Subscribe
    public void onProxyShutdownEvent(ProxyShutdownEvent event){
        //todo
    }

}
