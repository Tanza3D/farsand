package com.farsand.farsand;

import java.util.logging.Logger;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;

// from memory, this class was pulled from an old source tree of Essentials. maybe.
public class FSPlayerListener extends PlayerListener
{
	private static final Logger logger = Logger.getLogger("Minecraft");
	private final Server server;
	private final FSPlugin parent;

	public FSPlayerListener(FSPlugin parent)
	{
		this.parent = parent;
		this.server = parent.getServer();
	}
	
	@Override
	public void onPlayerJoin(PlayerEvent event)
	{
		Player user = event.getPlayer();
		user.sendMessage("Welcome to FARSAND " + parent.ver + ", run /about for more info.");
	}
}
