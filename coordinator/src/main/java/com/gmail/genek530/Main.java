package com.gmail.genek530;

import com.gmail.genek530.discord.MainDiscordBot;
import com.gmail.genek530.minecraft.datatypes.MainConfig;
import com.gmail.genek530.discord.configs.VerifyModuleConfig;
import com.gmail.genek530.minecraft.PacketRequests;
import com.gmail.genek530.minecraft.callbacks.ProxyServer;
import com.gmail.genek530.discord.userverification.data.VerifiedUserDB;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("TBOT");

    private static final File dataDir = new File(".");

    private static MainConfig mainConfig;

    private static VerifyModuleConfig verifyModuleConfig;

    private static HashMap<String, ProxyServer> proxyServers = new HashMap<>();


    public static void main(String[] args) throws Exception {
        boolean requireConfiguration = false;

        File moduleDir = new File(dataDir + "/modules/");
        if(!moduleDir.exists()) moduleDir.mkdir();

        File mainConfigFile = new File(dataDir + "/minecraftside.yaml");
        if(!mainConfigFile.exists()){
            Files.copy(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("minecraftside.yaml")), mainConfigFile.toPath());
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


        Yaml verification = new Yaml(new Constructor(VerifyModuleConfig.class));
        verifyModuleConfig = verification.load(new UnicodeReader(new FileInputStream(Main.getDataDir() + "/modules/verification.yaml")));

        MainDiscordBot.initalize();
        VerifiedUserDB.init();

        for (String server : mainConfig.getProxyServers()) {
            proxyServers.put(server, new ProxyServer(server));
        }

        HashMap<String,String> redis = mainConfig.getRedis();
        PacketRequests.init(redis.get("ip"), Integer.parseInt(redis.get("port")), redis.get("password"));

        String line =  new Scanner(System.in).nextLine();
    }

    public static HashMap<String, ProxyServer> getProxyServers() {
        return proxyServers;
    }

    public static MainConfig getMainConfig() {
        return mainConfig;
    }

    public static VerifyModuleConfig getVerifyModuleConfig() {
        return verifyModuleConfig;
    }

    public static File getDataDir(){
        return dataDir;
    }

    public static Logger getLogger(){
        return logger;
    }
}
