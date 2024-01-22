package com.farsand.farsand.commands;

import com.farsand.farsand.FSCommand;
import com.farsand.farsand.Global;
import com.farsand.farsand.Vector3;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

import java.util.List;

public class SpawnBoats extends FSCommand {
    Vector3[] BoatSpawns = {
            new Vector3(-4, 65, 76),
            new Vector3(-4, 65, 78),
            new Vector3(2, 65, 76),
            new Vector3(2, 65, 78),
            new Vector3(10, 65, 76),
            new Vector3(10, 65, 78),
    };

    @Override
    public boolean Command(CommandSender sender, Command cmd, String label, String[] args) {
        Global.Server.broadcastMessage("Respawning boats");
        if(sender != null) {
            // this is dumb but works. we spawn minecarts to check
            // for nearby boats at the spawnpoints, remove the boats,
            // then remove the minecarts, we never remove any user-made
            // minecarts in the spawn though :)
            for (Vector3 spawnpoint : BoatSpawns) {
                // running manually, destroy nearby boats :)
                Minecart tmp = Global.World.spawnMinecart(spawnpoint.toLocation(Global.World));
                List<Entity> ents = tmp.getNearbyEntities(10, 10, 10);
                for (Entity entity : ents) {
                    if (entity instanceof Boat) {
                        entity.remove();
                    }
                }
                tmp.remove();
            }
        }
        for (Vector3 spawnpoint : BoatSpawns) {
            spawnpoint.z -= 0.5;
            Global.World.spawnBoat(spawnpoint.toLocation(Global.World));
        }
        return true;
    }
}
