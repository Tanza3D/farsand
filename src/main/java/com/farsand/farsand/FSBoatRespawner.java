package com.farsand.farsand;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class FSBoatRespawner {
    private final FSPlugin parent;
    private final Server server;

    FSBoatRespawner(FSPlugin parent) {
        this.parent = parent;
        this.server = parent.getServer();
    }
    Box SpawnArea = new Box(new Vector3(33,128,25), new Vector3(-87,0,122));
    Box DockArea = new Box(new Vector3(-20,128,88), new Vector3(21,0,57));

    Vector3[] BoatSpawns = {
            new Vector3(-4, 65, 76),
            new Vector3(-4, 65, 78),
            new Vector3(2, 65, 76),
            new Vector3(2, 65, 78),
            new Vector3(10, 65, 76),
            new Vector3(10, 65, 78),
    };
    void SpawnBoats() {
        this.server.broadcastMessage("Respawning boats");
        World world = this.server.getWorlds().get(0);
        for(Vector3 spawnpoint : BoatSpawns) {
            Vector3 realSpawnPoint = spawnpoint;
            realSpawnPoint.z += 0.5;
            world.spawnBoat(spawnpoint.toLocation(world));
        }
    }
    public void BoatLoop() {
        int sec = 30;
        this.server.getScheduler().scheduleSyncRepeatingTask((Plugin) this.parent, new Runnable() {
            @Override
            public void run() {
                int inSpawn = 0;
                int inDock = 0;
                for (World world : parent.getServer().getWorlds()) {
                    List<Entity> entities = world.getEntities();
                    for (Entity entity : entities) {
                        if (!(entity instanceof Boat)) continue;
                        if(SpawnArea.isInside(new Vector3(entity.getLocation()))) {
                            inSpawn++;
                            if(DockArea.isInside(new Vector3(entity.getLocation()))) {
                                inDock++;
                            }
                        }
                    }
                }

                //getServer().broadcastMessage("There are " + inSpawn + " boats in spawn, and " + inDock + " boats at the dock.");
                // checked boats.
                if(inDock == 0 && inSpawn < 2) {
                    // TODO: respawn boats
                    SpawnBoats();
                }
            }
        }, 20L * sec, 20L * sec);
    }
}
