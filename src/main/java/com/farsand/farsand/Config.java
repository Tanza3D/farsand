package com.farsand.farsand;
// Java Imports

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet3Chat;

public class Config {
    // Variables
    private static Map<String, Object> myConfig;
    private static Yaml yaml = new Yaml();
    private static File configFile;
    private static boolean loaded = false;

    static String readFile(File file)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded);
    }
    public static void loadConfig() {
        configFile = new File(Global.Server.getPluginManager().getPlugin("FARSAND").getDataFolder(), "config.yml");
        if (configFile.exists()) {
            try {
                Global.Server.getLogger().info("Config exists, loading");
                myConfig = (Map<String, Object>) yaml.load(readFile(configFile));
                Global.Server.getLogger().info("Config has been loaded");
            } catch (IOException ex) {
                Global.Server.getLogger().severe("Could not load config");
            }
            loaded = true;
        } else {
            Global.Server.getLogger().info("Config does not exist, creating");
            try {
                Global.Server.getPluginManager().getPlugin("FARSAND").getDataFolder().mkdir();
                InputStream jarURL = Config.class.getResourceAsStream("/config.yml");
                copyFile(jarURL, configFile);
                myConfig = (Map<String, Object>) yaml.load(readFile(configFile));
                loaded = true;
                Global.Server.getLogger().info("Config has been loaded");
            } catch (Exception e) {
                Global.Server.getLogger().severe("Could not create config");
            }
        }
    }
    public static Object getValue(String key, Object fallback) {
        if (Config.myConfig != null) {
            return Config.myConfig.getOrDefault(key, fallback);
        } else {
            // Log a warning or handle the situation as appropriate for your application
            Global.Server.getLogger().warning("Config is null while trying to get value for key: " + key);
            return fallback;
        }
    }

    static private void copyFile(InputStream in, File out) throws Exception {
        InputStream fis = in;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    public Config() {
    }
}