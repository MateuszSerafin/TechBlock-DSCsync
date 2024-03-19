package genek530.velocity;


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
import genek530.proxyCommons.Data;
import genek530.proxyCommons.configWazparser;
import genek530.velocity.commands.syncCommand;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;

@Plugin(id = "tbdiscordbot", name = "BOT", version = "0.0.1-ALPHA",
        url = "http://techblock.pl", description = "Brak opisu :)", authors = {"FfFn6XCqhpxY"}, dependencies = {@Dependency(id = "luckperms")})
public class main {
    public static ProxyServer server;
    public static Logger logger;

    //server plugin folder
    public static Path dataDir;

    public static configWazparser conf;

    public static main plugin;


    @Inject
    public main(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        main.server = server;
        main.logger = logger;
        main.dataDir = dataDirectory;
        plugin = this;
    }


    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) throws Exception {

        genek530.proxyCommons.main.init(dataDir.toFile(), logger,this, new velocityCallback());

        CommandManager commandManager = main.server.getCommandManager();
        // Here you can add meta for the command, as aliases and the plugin to which it belongs (RECOMMENDED)
        CommandMeta commandMeta = commandManager.metaBuilder("discordsync")
                // This will create a new alias for the command "/test"
                // with the same arguments and functionality
                .plugin(this)
                .build();
        commandManager.register(commandMeta, new syncCommand());
        server.getEventManager().register(this, new events());

        //initializeBulk();
    }

    @Subscribe
    public void onProxyShutdownEvent(ProxyShutdownEvent event){
        //todo
    }

    private void initializeBulk(){
        try{
            File dataDir = main.dataDir.toFile();
            if(!dataDir.exists()){
                dataDir.mkdir();
            }
            File config = new File(dataDir.toPath() + "/config.yml");
            if(!config.exists()){
                Files.copy(Objects.requireNonNull(main.class.getClassLoader().getResourceAsStream("config.yml")), config.toPath());
                throw new Exception("He he najpierw ustaw config od  TBDISCORDBOTa");
            }

            //https://www.spigotmc.org/threads/cannot-use-shaded-snakeyaml-to-construct-class-object.136707/
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

            Yaml yaml = new Yaml(new Constructor(configWazparser.class));
            conf = yaml.load(new UnicodeReader(new FileInputStream(config)));

            //todo server.getEventManager().register(this, new joinEvent());
            logger.info("TBDISCORDSYNC wystartowal");
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
