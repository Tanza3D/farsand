package com.farsand.farsand.commands;

import com.farsand.farsand.FSCommand;
import com.farsand.farsand.Global;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import java.util.List;

public class Cleanup extends FSCommand {
    @Override
    public boolean Command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (!sender.isOp()) return false;
        int dropped = 0;
        int arrows = 0;
        List<Entity> entities = Global.World.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Item) {
                entity.remove();
                dropped++;
            }
            if (entity instanceof Arrow) {
                entity.remove();
                arrows++;
            }
        }
        sender.sendMessage("Cleaned up " + dropped + " dropped items.");
        sender.sendMessage("Cleaned up " + arrows + " arrows.");
        return true;
    }
}
