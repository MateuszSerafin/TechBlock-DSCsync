package com.gmail.genek530.tbot.clientinterface;

import io.github.thatkawaiisam.pyrite.Pyrite;
import io.github.thatkawaiisam.pyrite.PyriteCredentials;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public class Main {

    private static ClientImplementation clientImplementation;

    private static Logger logger;

    private static TBOTConfiguration tbotConfiguration;

    private static boolean isVerifyModuleEnabled = false;

    //Data dir should be /plugins/TBOT/
    public static void init(File dataDir, Logger logger, ClientImplementation clientImplementationVar) throws Exception {
        boolean requireConfiguration = false;

        if(!dataDir.exists()) dataDir.mkdir();
        File mainConfigFile = new File(dataDir + "/TBOT.yaml");
        if(!mainConfigFile.exists()){
            Files.copy(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("TBOT.yaml")), mainConfigFile.toPath());
            requireConfiguration = true;
        }
        if(requireConfiguration){
            logger.info("TBOT requires additional configuration");
            return;
        }
        clientImplementation = clientImplementationVar;

        Yaml yaml = new Yaml(new Constructor(TBOTConfiguration.class));
        tbotConfiguration = yaml.load(new UnicodeReader(new FileInputStream(mainConfigFile)));

        HashMap<String,String> redis = tbotConfiguration.getRedis();
        Packets.init(redis.get("ip"), Integer.parseInt(redis.get("port")), redis.get("password"), redis.get("whoAmI"));
    }

    public static ClientImplementation getClientImplementation() {
        return clientImplementation;
    }

    public static TBOTConfiguration getTbotConfiguration() {
        return tbotConfiguration;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static boolean isIsVerifyModuleEnabled() {
        return isVerifyModuleEnabled;
    }
}
