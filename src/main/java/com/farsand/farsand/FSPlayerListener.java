package com.farsand.farsand;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.logging.Logger;

// from memory, this class was pulled from an old source tree of Essentials. maybe.
public class FSPlayerListener extends PlayerListener {
    private static final Logger logger = Logger.getLogger("Minecraft");
    private final Server server;
    private final FSPlugin parent;

    public FSPlayerListener(FSPlugin parent) {
        this.parent = parent;
        this.server = parent.getServer();
    }

    @Override
    public void onPlayerJoin(PlayerEvent event) {
        Player user = event.getPlayer();
        //user.sendMessage("Welcome to FARSAND " + parent.ver + ", run /about for more info.");
    }
}