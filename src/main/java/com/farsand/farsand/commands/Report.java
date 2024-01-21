package com.farsand.farsand.commands;

import com.farsand.farsand.FSCommand;
import org.bukkit.command.CommandSender;

public class Report extends FSCommand {
    @Override
    public boolean Command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        sender.sendMessage(args + "?");
        return true;
    }
}
