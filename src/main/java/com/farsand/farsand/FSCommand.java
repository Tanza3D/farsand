package com.farsand.farsand;

import org.bukkit.command.CommandSender;

public class FSCommand {
    public boolean Protected() {
        return false;
    }
    public boolean Command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        sender.sendMessage("Command not implemented");
        return false;
    }
}
