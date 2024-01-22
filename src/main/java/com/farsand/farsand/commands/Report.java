package com.farsand.farsand.commands;

import com.farsand.farsand.Config;
import com.farsand.farsand.FSCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class Report extends FSCommand {
    @Override
    public boolean Command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String message = String.join(" ", args);
        if(Config.getValue("discord-webhook", null) != null) {
            sender.sendMessage("We have reported your issue: " + message);
        } else {
            sender.sendMessage("Could not report");
        }
        return true;
    }
}
