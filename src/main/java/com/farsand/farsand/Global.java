package com.farsand.farsand;

import java.util.Hashtable;

public class Global {
    public static org.bukkit.World World;
    public static org.bukkit.Server Server;
    public static org.bukkit.plugin.Plugin Plugin;
    public static Object Config;
    public static Hashtable<String, FSCommand> Commands = new Hashtable<>();
}
