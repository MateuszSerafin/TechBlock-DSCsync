package com.gmail.genek530.modules.verificationdscmc;

import com.gmail.genek530.Main;
import com.gmail.genek530.MainConfig;
import com.gmail.genek530.modules.verificationdscmc.discord.BotContainer;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.FileInputStream;

public class VerifyModule {

    private static boolean isEnabled = false;

    private static VerifyModuleConfig verifyModuleConfig;


    public static void init() throws Exception {
        isEnabled = true;

        Yaml yaml = new Yaml(new Constructor(VerifyModuleConfig.class));
        verifyModuleConfig = yaml.load(new UnicodeReader(new FileInputStream(Main.getDataDir() + "/modules/verification.yaml")));

        BotContainer.initalize();
        VerifiedUserDB.init();
    }


    public static boolean isIsEnabled() {
        return isEnabled;
    }

    public static VerifyModuleConfig getVerifyModuleConfig() {
        return verifyModuleConfig;
    }



}
