package com.farsand.farsand;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

// NO WORKY! DONT USE
public class FSBlockFixer {
    public static class ForceBlock {
        Material material;
        Location location;
        public ForceBlock(Material material, Location location) {
            location = location;
            material = material;
        }
    }
    public List<ForceBlock> locations = new ArrayList<>();

    public FSBlockFixer() {
        int sec = 10;
        Global.Server.getScheduler().scheduleSyncRepeatingTask((Plugin) Global.Plugin, new Runnable() {
            @Override
            public void run() {
                for(ForceBlock location : locations) {
                    Global.Server.broadcastMessage(Global.World.getBlockAt(location.location).getType() + " at " + Global.World.getBlockAt(location.location).getLocation());
                    Global.World.getBlockAt(location.location).setType(Material.AIR);
                    Global.World.getBlockAt(location.location).setType(location.material);
                }
            }
        }, 20L * sec, 20L * sec);
    }
}
