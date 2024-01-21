package com.farsand.farsand.commands;

import com.farsand.farsand.FSCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Info extends FSCommand {
    @Override
    public boolean Command(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("{! REWRITE THIS :3} FARSAND is a Minecraft Beta server, upgrading one version at a time. Currently on " + "VERSION" + ". See /rules");
        return true;
    }
}
