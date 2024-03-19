package genek530.proxyCommons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.validActions;
import genek530.proxyCommons.discord.discordContainer;
import genek530.proxyCommons.minecraft.events.lpPermissionAdd;
import genek530.proxyCommons.minecraft.events.lpPermissionRemove;
import genek530.proxyCommons.minecraft.events.lpUpdateevent;
import genek530.proxyCommons.pyrite.packetHandler;
import io.github.thatkawaiisam.pyrite.Pyrite;
import io.github.thatkawaiisam.pyrite.PyriteCredentials;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class main {
    //chciałem aby dalej pakiety mialy Stringa jako sender. Bo w tedy standalone jest jakos po logach do ogarnicia a tak to wyjeban
    public static String bootUUID = UUID.randomUUID().toString();

    public static serverCallbacks serverCallback;

    public static Pyrite pyrite;
    public static configWazparser conf;
    public static Logger logger;

    public static LuckPerms lapi;

    public static Gson gson = new GsonBuilder().setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();

    //initbruv
    public static void init(File dataDir, Logger logger, Object Plugin, serverCallbacks serverCallback) throws Exception {
        main.serverCallback = serverCallback;
        main.logger = logger;


        if(!dataDir.exists()){
            dataDir.mkdir();
        }
        File config = new File(dataDir.toPath() + "/config.yml");
        if(!config.exists()){
            Files.copy(Objects.requireNonNull(main.class.getClassLoader().getResourceAsStream("config.yml")), config.toPath());
            throw new Exception("He he najpierw ustaw config od  TBDISCORDBOTa");
        }

        //https://www.spigotmc.org/threads/cannot-use-shaded-snakeyaml-to-construct-class-object.136707/
        Thread.currentThread().setContextClassLoader(main.class.getClassLoader());


        Yaml yaml = new Yaml(new Constructor(configWazparser.class));
        conf = yaml.load(new UnicodeReader(new FileInputStream(config)));

        String ip = conf.getRedis().get("ip");
        String haslo = conf.getRedis().get("haslo");
        String port = conf.getRedis().get("port");
        try {
            pyrite = new Pyrite(new PyriteCredentials(ip,haslo,Integer.parseInt(port)));
            pyrite.registerContainer(new packetHandler());
        } catch (Exception e){
            //discordContainer.getJda().shutdownNow();
            e.printStackTrace();
            throw new Exception("Bruh moment pyrite sie wyjebał exception");
        }

        lapi = LuckPermsProvider.get();
        if(conf.isUpdatujpermisije()){
            lapi.getEventBus().subscribe(Plugin, NodeAddEvent.class, lpPermissionAdd::onNodeAddEvent);
            lapi.getEventBus().subscribe(Plugin, NodeRemoveEvent.class, lpPermissionRemove::onNodeRemoveEvent);
            lapi.getEventBus().subscribe(Plugin, UserDataRecalculateEvent.class, lpUpdateevent::handle);
        }

        pyrite.sendPacket(new redisPacket(bootUUID, "standAlone", validActions.proxyStartup, null), "TBOT");

        if(conf.isCzatsynchro()){
            discordContainer.initalize();
        }

        logger.info("TBDISCORDSYNC wystartowal");
    }





}
