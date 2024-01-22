package com.farsand.farsand;

import com.farsand.farsand.commands.Cleanup;
import com.farsand.farsand.commands.Info;
import com.farsand.farsand.commands.Report;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.logging.Logger;


public class FSPlugin extends JavaPlugin {
    @Override
    public void onDisable() {
        System.out.print("FS PL Disabled");
    }

    Hashtable<String, FSCommand> commands = new Hashtable<>();



    @Override
    public void onEnable() {
        Global.Plugin = this;
        Global.World = this.getServer().getWorlds().get(0);
        Global.Server = this.getServer();
        PluginManager pm = getServer().getPluginManager();

        Config.loadConfig();

        FSPlayerListener playerListener = new FSPlayerListener(this);
        pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);

        FSBlockListener blockListener = new FSBlockListener();
        pm.registerEvent(Type.BLOCK_PLACED, blockListener, Priority.Highest, this);

        FSBoatRespawner boatChecker = new FSBoatRespawner(this);
        boatChecker.BoatLoop();

        commands.put("cleanup", new Cleanup());
        commands.put("report", new Report());
        commands.put("abt", new Info());

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
        if (commands.containsKey(name)) {
            return commands.get(name).Command(sender, cmd, label, args);
        } else {
            sender.sendMessage("Could not find command " + name + "?");
            sender.sendMessage(commands + "?");
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return doCommand(sender, cmd, label, args);
    }
}