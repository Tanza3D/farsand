package com.farsand.farsand;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;

import java.util.List;
import java.util.Vector;

public class FSPlugin extends JavaPlugin {
    public String ver = "b1.3";
    
    @Override
    public void onDisable() {
    	System.out.print("FS PL Disabled");
    }

    @Override
    public void onEnable() {
        Global.World = this.getServer().getWorlds().get(0);
    	PluginManager pm = getServer().getPluginManager();

    	FSPlayerListener playerListener = new FSPlayerListener(this);
        pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);

        FSBlockListener blockListener = new FSBlockListener();
        pm.registerEvent(Type.BLOCK_PLACED, blockListener, Priority.Highest, this);

        FSBoatRespawner boatChecker = new FSBoatRespawner(this);
        boatChecker.BoatLoop();

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