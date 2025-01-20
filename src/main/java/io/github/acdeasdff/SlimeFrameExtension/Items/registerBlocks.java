package io.github.acdeasdff.SlimeFrameExtension.Items;

import io.github.acdeasdff.SlimeFrameExtension.Groups.Group;
import io.github.acdeasdff.SlimeFrameExtension.Items.Blocks.ModifierDissolver;
import io.github.acdeasdff.SlimeFrameExtension.Items.Blocks.ModifierTable;
import io.github.acdeasdff.SlimeFrameExtension.Items.Blocks.ModifierUpgradeTable;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.COLOR_MIKU;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;
import static me.voper.slimeframe.implementation.SFrameStacks.*;

public class registerBlocks {
    public static SlimefunItemStack MODIFIER_TABLE = new SlimefunItemStack(
            "SFE_MODIFIER_TABLE",
            Material.FLETCHING_TABLE,
            COLOR_MIKU + properties.getReplacedProperty("ModifierTable_DisplayName"),
            ChatColor.GRAY + properties.getReplacedProperty("ModifierTable_DisplayLore"),
            ChatColor.RED + properties.getReplacedProperty("ModifierTable_DisplayLore_2")
    );

    public static SlimefunItemStack MODIFIER_UPGRADE_TABLE = new SlimefunItemStack(
            "SFE_MODIFIER_UPGRADE_TABLE",
            Material.CARTOGRAPHY_TABLE,
            COLOR_MIKU +
                    properties.getReplacedProperty("ModifierUpgradeTable_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("ModifierUpgradeTable_Lore1")
    );
    public static SlimefunItemStack MODIFIER_DISSOLVER = new SlimefunItemStack(
            "SFE_MODIFIER_DISSOLVER",
            Material.GRINDSTONE,
            COLOR_MIKU +
                    properties.getReplacedProperty("ModifierDissolver_Name"),
            COLOR_MIKU + properties.getReplacedProperty("ModifierDissolver_Lore1")
    );

    public static void setup(SlimeFrameExtension plugin) {
        new ModifierTable(Group.BASIC_MACHINES, MODIFIER_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                FERSTEEL_ALLOY, FERSTEEL_ALLOY, FERSTEEL_ALLOY,
                AUROXIUM_ALLOY, MORPHICS, AUROXIUM_ALLOY,
                PYROTIC_ALLOY, ADRAMAL_ALLOY, PYROTIC_ALLOY
        }).register(plugin);

        new ModifierUpgradeTable(Group.BASIC_MACHINES, MODIFIER_UPGRADE_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                OROKIN_CELL, AUROXIUM_ALLOY, OROKIN_CELL,
                NEURAL_SENSORS, NEURODES, NEURAL_SENSORS,
                OROKIN_CELL, AUROXIUM_ALLOY, OROKIN_CELL
        }).register(plugin);

        new ModifierDissolver(Group.BASIC_MACHINES, MODIFIER_DISSOLVER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                OROKIN_CELL, new ItemStack(Material.ANVIL), OROKIN_CELL,
                new ItemStack(Material.OBSIDIAN), new ItemStack(Material.GRINDSTONE), new ItemStack(Material.OBSIDIAN),
                new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.LAVA_BUCKET)
        }).register(plugin);
    }
}
