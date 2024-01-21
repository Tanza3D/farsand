package com.farsand.farsand;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class FSBlockListener extends BlockListener {
    public FSBlockListener() {
    }

    public void dropTnt(BlockPlaceEvent event) {
        event.getPlayer().sendMessage("Cannot place TNT!");
        ItemStack drop = new ItemStack(event.getBlock().getType());
        drop.setAmount(1);
        drop.setType(Material.TNT);
        Global.World.dropItem(event.getBlock().getLocation(), drop);
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        // normal check
        if (event.getBlockPlaced().getType() == Material.TNT) {
            event.getBlockPlaced().setType(Material.AIR);
            dropTnt(event);
            return;
        }

        // snow (and edge case i guess) check
        if (event.getBlockReplacedState().getType() == Material.SNOW) {
            // okay, so when clicking on snow, for whatever mystical
            // reason, it gives us the coordinates of the block *above*
            // the snow, so we have to check the block below those
            // coordinates manually. this took 4 hours for me to figure
            // out. :)
            Location loc = event.getBlock().getLocation();
            loc.setY(loc.getY() - 1);
            Block block = Global.World.getBlockAt(loc);
            if (block.getType() == Material.TNT) {
                block.setType(Material.AIR);
                dropTnt(event);
                return;
            }
        }
        super.onBlockPlace(event);
    }
}
