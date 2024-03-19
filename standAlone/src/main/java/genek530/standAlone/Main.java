package genek530.standAlone;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import genek530.commons.redis.redisPacket;
import genek530.commons.redis.validActions;
import genek530.standAlone.discord.discordContainer;
import genek530.standAlone.hujowecommonsy.configWazparser;
import genek530.standAlone.redis.packetHandler;
import io.github.thatkawaiisam.pyrite.Pyrite;
import io.github.thatkawaiisam.pyrite.PyriteCredentials;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static Logger logger = Logger.getLogger("TBOT");

    //server plugin folder
    public static Path dataDir = new File(".").toPath();

    public static configWazparser conf;

    public static Pyrite pyrite;

    public static Gson gson = new GsonBuilder().setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();
    public static void main(String[] args) {
        initializeBulk();
    }

    private static void initializeBulk(){
        try{
            File dataDir = Main.dataDir.toFile();
            if(!dataDir.exists()){
                dataDir.mkdir();
            }
            File config = new File(dataDir.toPath() + "/config.yml");
            if(!config.exists()){
                Files.copy(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("config.yml")), config.toPath());
                throw new Exception("He he najpierw ustaw config od  TBDISCORDBOTa");
            }

            /*
            //https://www.spigotmc.org/threads/cannot-use-shaded-snakeyaml-to-construct-class-object.136707/
            Thread.currentThread().setContextClassLoader(Main.class.getClassLoader());

             */

            Yaml yaml = new Yaml(new Constructor(configWazparser.class));
            conf = yaml.load(new UnicodeReader(new FileInputStream(config)));

            Data.initialize();
            discordContainer.initalize();

            String ip = conf.getRedis().get("ip");
            String haslo = conf.getRedis().get("haslo");
            String port = conf.getRedis().get("port");

            packetHandler packetHandler = new packetHandler();

            try {
                pyrite = new Pyrite(new PyriteCredentials(ip,haslo,Integer.parseInt(port)));
                pyrite.registerContainer(packetHandler);
            } catch (Exception e){
                discordContainer.getJda().shutdownNow();
                throw new Exception("Bruh moment pyrite sie wyjebał exception");
            }


            pyrite.sendPacket(new redisPacket("standAlone", "broadcast", validActions.restartedStandalone, "null"), "TBOT");

            String line =  new Scanner(System.in).nextLine();
            discordContainer.getJda().shutdownNow();
            Data.shutdown();
            pyrite.unregisterContainer(packetHandler);
            pyrite.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
