package com.farsand.farsand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;

public class Plugin extends JavaPlugin {
    public String ver = "b1.3";
    
    @Override
    public void onDisable() {
    	System.out.print("FS PL Disabled");
    }

    @Override
    public void onEnable() {
    	PluginManager pm = getServer().getPluginManager();

    	PlayerListener playerListener = new PlayerListener(this);
		pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
		
    	System.out.print("FS PL Enabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("about-farsand")) {
        	sender.sendMessage("FARSAND is a Minecraft Beta server, upgrading one version at a time. Currently on " + ver);
        }
        return false;
    }
}