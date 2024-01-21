package com.farsand.farsand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class FSPlugin extends JavaPlugin {
    public String ver = "b1.3";

    @Override
    public void onDisable() {
        System.out.print("FS PL Disabled");
    }

    @Override
    public void onEnable() {
        Global.Plugin = this;
        Global.World = this.getServer().getWorlds().get(0);
        Global.Server = this.getServer();
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
        if (cmd.getName().equalsIgnoreCase("about")) {
            sender.sendMessage("{! REWRITE THIS :3} FARSAND is a Minecraft Beta server, upgrading one version at a time. Currently on " + ver + ". See /rules");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("cleanup")) {
            if (!sender.isOp()) return false;
            int dropped = 0;
            List<Entity> entities = Global.World.getEntities();
            for (Entity entity : entities) {
                if (!(entity instanceof Item)) continue;
                entity.remove();
                dropped++;
            }
            sender.sendMessage("Cleaned up " + dropped + " dropped items.");
            return true;
        }
        return false;
    }
}