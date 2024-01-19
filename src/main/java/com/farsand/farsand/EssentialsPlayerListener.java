package com.farsand.farsand;

import java.util.logging.Logger;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;


public class EssentialsPlayerListener extends PlayerListener
{
	private static final Logger logger = Logger.getLogger("Minecraft");
	private final Server server;
	private final Plugin parent;

	public EssentialsPlayerListener(Plugin parent)
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
