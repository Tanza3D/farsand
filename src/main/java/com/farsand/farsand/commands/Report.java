package com.farsand.farsand.commands;

import com.farsand.farsand.Config;
import com.farsand.farsand.FSCommand;
import com.farsand.farsand.discord.Webhook;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;

public class Report extends FSCommand {
    @Override
    public boolean Command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        String message = String.join(" ", args);
        if(Config.getValue("discord-webhook", null) != null) {
            Webhook webhook = new Webhook((String) Config.getValue("discord-webhook", null));
            Webhook.EmbedObject embed = new Webhook.EmbedObject();
            webhook.setUsername(player.getDisplayName());
            webhook.setAvatarUrl("https://mc-heads.net/avatar/" + player.getName());
            embed.setDescription(message);
            //embed.addField("Content", message, false);
            embed.addField("Position", Math.round(player.getLocation().getX()) + ", " + Math.round(player.getLocation().getY()) + ", " + Math.round(player.getLocation().getZ()) + " (world "+ player.getLocation().getWorld().getName() +")", true);
            webhook.addEmbed(embed);
            try {
                webhook.execute();
                sender.sendMessage("We have reported your issue: " + message);
            } catch (IOException e) {
                sender.sendMessage("Could not report: " + e.toString());
            }
        } else {
            sender.sendMessage("Could not report");
        }
        return true;
    }
}
