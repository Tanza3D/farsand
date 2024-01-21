package com.farsand.farsand;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class FSBlockListener extends BlockListener {
    public FSBlockListener() {

    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.TNT) {
            event.getBlock().setType(Material.AIR);
            event.getPlayer().sendMessage("Cannot place TNT!");
            ItemStack drop = new ItemStack(event.getBlock().getType());
            drop.setAmount(1);
            drop.setType(Material.TNT);
            Global.World.dropItem(event.getBlock().getLocation(), drop);

            return;
        }
        super.onBlockPlace(event);
    }
}
