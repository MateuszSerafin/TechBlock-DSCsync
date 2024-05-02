package com.gmail.genek530;

import com.gmail.genek530.modules.verificationdscmc.VerifyModule;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("TBOT");

    private static final File dataDir = new File(".");


    public static void main(String[] args) throws Exception {
        boolean requireConfiguration = false;

        File moduleDir = new File(dataDir + "/modules/");
        if(!moduleDir.exists()) moduleDir.mkdir();


        File mainConfigFile = new File(dataDir + "/main.yaml");
        if(!mainConfigFile.exists()){
            Files.copy(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("main.yaml")), mainConfigFile.toPath());
            requireConfiguration = true;
        }

        File verificationModule = new File(moduleDir + "/verification.yaml");
        if(!verificationModule.exists()){
            Files.copy(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("verification.yaml")), verificationModule.toPath());
            requireConfiguration = true;
        }


        if(requireConfiguration){
            //TODO this triggers unecessarly when module is added
            System.out.println("Something has changed you need to configure it.");
            return;
        }

            /*
            //https://www.spigotmc.org/threads/cannot-use-shaded-snakeyaml-to-construct-class-object.136707/
            Thread.currentThread().setContextClassLoader(Main.class.getClassLoader());

             */

        Yaml yaml = new Yaml(new Constructor(MainConfig.class));
        MainConfig mainConfig = yaml.load(new UnicodeReader(new FileInputStream(mainConfigFile)));

        HashMap<String, Boolean> modulesEnabled = mainConfig.getModulesEnabled();

        //crime
        if(modulesEnabled.containsKey("verify_module") && modulesEnabled.containsKey("chat_module") && modulesEnabled.containsKey("permission_module")) {
            Main.getLogger().info("Module configuration checking...");
        } else throw new Exception("Not all of module objects were found in confiugration file. Please delete main.yaml and allow for regeneration");

        if(modulesEnabled.get("verify_module")){
            Main.getLogger().info("Verify module is starting up...");
            VerifyModule.init();
        }


        HashMap<String,String> redis = mainConfig.getRedis();
        Packets.init(redis.get("ip"), Integer.parseInt(redis.get("port")), redis.get("password"));

        String line =  new Scanner(System.in).nextLine();
    }


    public static File getDataDir(){
        return dataDir;
    }

    public static Logger getLogger(){
        return logger;
    }
}
