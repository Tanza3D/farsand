package com.farsand.farsand;

import org.bukkit.Server;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class FSBoatRespawner {
    private final FSPlugin parent;
    private final Server server;

    FSBoatRespawner(FSPlugin parent) {
        this.parent = parent;
        this.server = parent.getServer();
    }

    Box SpawnArea = new Box(new Vector3(33, 128, 25), new Vector3(-87, 0, 122));
    Box DockArea = new Box(new Vector3(-20, 128, 88), new Vector3(21, 0, 57));


    public void BoatLoop() {
        int sec = 60;
        this.server.getScheduler().scheduleSyncRepeatingTask((Plugin) this.parent, new Runnable() {
            int players = 0;
            int inSpawn = 0;
            int inDock = 0;

            @Override
            public void run() {
                players = 0;
                inSpawn = 0;
                inDock = 0;

                List<Entity> entities = Global.World.getEntities();
                for (Entity entity : entities) {
                    if(entity instanceof Player) {
                        if(SpawnArea.isInside(new Vector3(entity.getLocation()))) players++;
                    }
                    if (!(entity instanceof Boat)) continue;
                    if (!SpawnArea.isInside(new Vector3(entity.getLocation()))) continue;
                    if (players == 0) {
                        entity.remove();
                        continue;
                    }
                    inSpawn++;
                    if (DockArea.isInside(new Vector3(entity.getLocation()))) inDock++;
                }

                //getServer().broadcastMessage("There are " + inSpawn + " boats in spawn, and " + inDock + " boats at the dock.");
                // checked boats.
                if (inDock == 0 && inSpawn < 2) {
                    // TODO: respawn boats
                    Global.Commands.get("spawnboats").Command(null, null, null, null);
                }
            }
        }, 20L * sec, 20L * sec);
    }
}
