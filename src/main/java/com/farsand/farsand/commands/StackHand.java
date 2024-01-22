package com.farsand.farsand.commands;

import com.farsand.farsand.FSCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StackHand extends FSCommand {
    @Override
    public boolean Protected() {
        return true;
    }

    @Override
    public boolean Command(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.getItemInHand().setAmount(64);
        player.sendMessage("Set " + player.getItemInHand().getType() + " to 64");
        return true;
    }
}
