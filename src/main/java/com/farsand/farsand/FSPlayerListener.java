package com.farsand.farsand;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;

import java.io.File;
import java.util.logging.Logger;

import static com.farsand.farsand.Global.Plugin;

// from memory, this class was pulled from an old source tree of Essentials. maybe.
public class FSPlayerListener extends PlayerListener {
    private static final Logger logger = Logger.getLogger("Minecraft");
    private final Server server;
    private final FSPlugin parent;

    public FSPlayerListener(FSPlugin parent) {
        this.parent = parent;
        this.server = parent.getServer();
    }

    Boolean firstLogin = false;

    @Override
    public void onPlayerLogin(PlayerLoginEvent event) {
        Global.Server.getLogger().info("User joined");
        String jarpath = FSPlugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String mainfolderpath = jarpath.substring(0, jarpath.lastIndexOf("/plugins"));
        String worldpath = mainfolderpath + "/" + Global.World.getName();
        String userdata = worldpath + "/players/" + event.getPlayer().getName() + ".dat";
        File f = new File(userdata);
        firstLogin = !(f.exists());
    }

    @Override
    public void onPlayerJoin(PlayerEvent event) {
        Player user = event.getPlayer();
    }

    @Override
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().startsWith("/about")) {
            event.setCancelled(true);
            Global.Commands.get("about").Command(event.getPlayer(), null, null, null);
            return;
        }

        if((event.getMessage().startsWith("/login") || event.getMessage().startsWith("/register")) && firstLogin) {
            Global.Server.getLogger().warning("Queuing user to teleport to spawn after login");

            Global.Server.getScheduler().scheduleSyncDelayedTask(Global.Plugin, new Runnable() {
                public void run() {
                    event.getPlayer().teleport(new Location(Global.World, -42, 64, 104, 0.1f, 0.5f));
                }
            }, 2L);

        } else {
            Global.Server.getLogger().warning("User has been here before!");
        }
        super.onPlayerCommandPreprocess(event);
    }
}