package com.farsand.farsand;

import com.farsand.farsand.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.logging.Logger;


public class FSPlugin extends JavaPlugin {
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

        Config.loadConfig();

        Global.Commands.put("cleanup", new Cleanup());
        Global.Commands.put("report", new Report());
        Global.Commands.put("about", new Info());
        Global.Commands.put("spawnboats", new SpawnBoats());
        Global.Commands.put("stackhand", new StackHand());
        Global.Commands.put("row", new Row());
        Global.Commands.put("hat", new Hat());

        FSPlayerListener playerListener = new FSPlayerListener(this);
        pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Monitor, this);

        FSBlockListener blockListener = new FSBlockListener();
        pm.registerEvent(Type.BLOCK_PLACE, blockListener, Priority.Highest, this);

        FSBoatRespawner boatChecker = new FSBoatRespawner(this);
        boatChecker.BoatLoop();

        System.out.print("FS PL Enabled");
    }


    // Get the Bukkit logger first, before we try to create our own
    private Logger getLoggerSafely() {

        Logger log = null;

        try {
            log = Global.Server.getLogger();
        } catch (Throwable e) {
            // We'll handle it
        }

        if (log == null)
            log = Logger.getLogger("Minecraft");
        return log;
    }
    public boolean doCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String name = cmd.getName();
        Global.Server.getLogger().info(((Player)sender).getName() + ": " + cmd.getName() + " " + String.join(" ", args));
        if (Global.Commands.containsKey(name)) {
            if (!sender.isOp() && Global.Commands.get(name).Protected()) {
                sender.sendMessage(ChatColor.RED + "No Permission");
                return false;
            }
            return Global.Commands.get(name).Command(sender, cmd, label, args);
        } else {
            sender.sendMessage(ChatColor.RED + "Could not find command " + name + "?");
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return doCommand(sender, cmd, label, args);
    }
}