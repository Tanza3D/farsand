package com.farsand.farsand.commands;

import com.farsand.farsand.FSCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row extends FSCommand {
    @Override
    public boolean Protected() {
        return true;
    }

    public class FSBlock {
        public Material material;
        public int data;
        public int amount = 64;
        public FSBlock(Material material) {
            this.material = material;
            this.data = 0;
        }
        public FSBlock(int amount, Material material) {
            this.amount = amount;
            this.material = material;
            this.data = 0;
        }
        public FSBlock(Material material, int data) {
            this.material = material;
            this.data = data;
        }
        public FSBlock(int amount, Material material, int data) {
            this.amount = amount;
            this.material = material;
            this.data = data;
        }
    }
    public class Preset {
        String name;
        String shortname;
        FSBlock[] materials;
        public Preset(String name, String shortname, FSBlock[] materials) {
            this.name = name;
            this.shortname = shortname;
            this.materials = materials;
        }
    }

    Preset[] presets = {
            new Preset("building", "b", new FSBlock[]{
                    new FSBlock(Material.WOOD),
                    new FSBlock(Material.LOG),
                    new FSBlock(Material.STONE),
                    new FSBlock(Material.COBBLESTONE),
                    new FSBlock(Material.BRICK),
                    new FSBlock(Material.GLASS),
                    new FSBlock(Material.GRAVEL),
                    new FSBlock(Material.DIRT),
                    new FSBlock(Material.GRASS)
            }),
            new Preset("supplementary", "s", new FSBlock[]{
                    new FSBlock(Material.TORCH),
                    new FSBlock(Material.GLOWSTONE),
                    new FSBlock(Material.LEAVES),
                    new FSBlock(Material.WOOD_DOOR),
                    new FSBlock(Material.SIGN),
                    new FSBlock(Material.FENCE),
                    new FSBlock(Material.DOUBLE_STEP),
                    new FSBlock(Material.WOOD_PLATE),
                    new FSBlock(Material.BOOKSHELF)
            }),
            new Preset("redstone", "r", new FSBlock[]{
                    new FSBlock(Material.STONE_BUTTON),
                    new FSBlock(Material.LEVER),
                    new FSBlock(Material.REDSTONE_TORCH_ON),
                    new FSBlock(Material.STONE_PLATE),
                    new FSBlock(Material.IRON_DOOR),
                    new FSBlock(Material.REDSTONE),
                    new FSBlock(Material.DIODE),
                    new FSBlock(Material.DISPENSER),
                    new FSBlock(Material.NOTE_BLOCK)
            }),
            new Preset("slabs_and_stairs", "ss", new FSBlock[]{
                    new FSBlock(Material.STEP),
                    new FSBlock(Material.STEP, 1),
                    new FSBlock(Material.STEP, 2),
                    new FSBlock(Material.STEP, 3),
                    new FSBlock(Material.COBBLESTONE_STAIRS),
                    new FSBlock(Material.WOOD_STAIRS),
                    new FSBlock(Material.DOUBLE_STEP),
                    new FSBlock(Material.getMaterial(43), 8),
                    new FSBlock(Material.STONE_BUTTON)
            }),
            new Preset("wool1", "w1", new FSBlock[]{
                    new FSBlock(Material.WOOL, 0),
                    new FSBlock(Material.WOOL, 1),
                    new FSBlock(Material.WOOL, 2),
                    new FSBlock(Material.WOOL, 3),
                    new FSBlock(Material.WOOL, 4),
                    new FSBlock(Material.WOOL, 5),
                    new FSBlock(Material.WOOL, 6),
                    new FSBlock(Material.WOOL, 7),
                    new FSBlock(Material.WOOL, 8)
            }),
            new Preset("wool2", "w2", new FSBlock[]{
                    new FSBlock(Material.WOOL, 9),
                    new FSBlock(Material.WOOL, 10),
                    new FSBlock(Material.WOOL, 11),
                    new FSBlock(Material.WOOL, 12),
                    new FSBlock(Material.WOOL, 13),
                    new FSBlock(Material.WOOL, 14),
                    new FSBlock(Material.WOOL, 15),
                    new FSBlock(Material.AIR, 16),
                    new FSBlock(Material.AIR, 0)
            }),
            new Preset("special", "sp", new FSBlock[]{
                    new FSBlock(Material.WATER),
                    new FSBlock(Material.LAVA),
                    new FSBlock(Material.FIRE),
                    new FSBlock(Material.SNOW_BLOCK),
                    new FSBlock(Material.SNOW),
                    new FSBlock(Material.TNT),
                    new FSBlock(Material.CAKE),
                    new FSBlock(Material.JUKEBOX),
                    new FSBlock(Material.LADDER)
            }),
            new Preset("natural1", "n1", new FSBlock[]{
                    new FSBlock(Material.LOG, 1),
                    new FSBlock(Material.LOG, 2),
                    new FSBlock(Material.LEAVES, 1),
                    new FSBlock(Material.YELLOW_FLOWER),
                    new FSBlock(Material.RED_ROSE),
                    new FSBlock(Material.BROWN_MUSHROOM),
                    new FSBlock(Material.RED_MUSHROOM),
                    new FSBlock(Material.SAPLING),
                    new FSBlock(Material.PUMPKIN)
            }),
            new Preset("natural2", "n2", new FSBlock[]{
                    new FSBlock(Material.SAND),
                    new FSBlock(Material.CLAY),
                    new FSBlock(Material.ICE),
                    new FSBlock(Material.SOUL_SAND),
                    new FSBlock(Material.NETHERRACK),
                    new FSBlock(Material.MOSSY_COBBLESTONE),
                    new FSBlock(Material.OBSIDIAN),
                    new FSBlock(Material.BEDROCK),
                    new FSBlock(Material.MOB_SPAWNER)
            }),
            new Preset("utilities", "u", new FSBlock[]{
                    new FSBlock(Material.WORKBENCH),
                    new FSBlock(Material.FURNACE),
                    new FSBlock(Material.BURNING_FURNACE),
                    new FSBlock(Material.CHEST),
                    new FSBlock(Material.BED),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR)
            }),
            new Preset("ore", "o", new FSBlock[]{
                    new FSBlock(Material.COAL_ORE),
                    new FSBlock(Material.IRON_ORE),
                    new FSBlock(Material.GOLD_ORE),
                    new FSBlock(Material.DIAMOND_ORE),
                    new FSBlock(Material.REDSTONE_ORE),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR)
            }),
            new Preset("tools", "t", new FSBlock[]{
                    new FSBlock(1, Material.DIAMOND_PICKAXE),
                    new FSBlock(1, Material.WOOD_AXE),
                    new FSBlock(1, Material.WOOD_SWORD),
                    new FSBlock(1, Material.STONE_SWORD),
                    new FSBlock(1, Material.IRON_SWORD),
                    new FSBlock(1, Material.GOLD_SWORD),
                    new FSBlock(1, Material.DIAMOND_SWORD),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR)
            }),
            new Preset("empty", "e", new FSBlock[]{
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR),
                    new FSBlock(Material.AIR)
            }),
    };

    @Override
    public boolean Command(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        List<String> help = new ArrayList<>();
        if(args.length == 0) {
            int counter = 0;
            int row = -1;
            StringBuilder rowtext = new StringBuilder();
            for(Preset preset : presets) {
                rowtext.append(preset.name).append(" (").append(preset.shortname).append(")    ");
                counter++;
                if(counter >= 3) {
                    row++;
                    help.add(rowtext.toString());
                    rowtext = new StringBuilder();
                    counter = 0;
                }
            }
            help.add(rowtext.toString());
            for(String item : help) {
                sender.sendMessage(ChatColor.BLUE + item);
            }
            sender.sendMessage(ChatColor.RED + "Usage: /row <name> <row (optional, defaults to hotbar)>");
            return true;
        }

        // 0 = hotbar, 3 = top row, you can guess the rest
        int row = 0;
        if(args.length == 2) {
            row = Integer.parseInt(args[1]);
        }

        Preset selected;
        String selectedName = args[0];

        selected = Arrays.stream(presets).filter(preset -> preset.name.equals(selectedName) || preset.shortname.equals(selectedName)).findFirst().orElse(null);

        if(selected == null) {
            sender.sendMessage(ChatColor.RED + selectedName + " does not exist");
            return true;
        }

        for(int x = 0; x < 9; x++) {
            int itemIndex = x + (9 * row);
            FSBlock block = selected.materials[x];
            if(block.material == Material.AIR) {
                player.getInventory().clear(itemIndex);
                continue;
            }
            ItemStack itemStack = new ItemStack(block.material, block.amount, (short)block.data);
            player.getInventory().setItem(itemIndex, itemStack);
        }

        return true;
    }
}
