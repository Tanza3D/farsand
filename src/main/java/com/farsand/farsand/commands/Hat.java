package com.farsand.farsand.commands;

import com.farsand.farsand.FSCommand;
import com.farsand.farsand.FSPlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hat extends FSCommand {
    @Override
    public boolean Protected() {
        return false;
    }

    @Override
    public boolean Command(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        player.getInventory().setItem(39, player.getItemInHand());
        player.setItemInHand(null);
        return true;
    }
}
