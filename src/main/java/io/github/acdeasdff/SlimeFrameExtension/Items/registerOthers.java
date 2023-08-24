package io.github.acdeasdff.SlimeFrameExtension.Items;

import io.github.acdeasdff.SlimeFrameExtension.Groups.Group;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.voper.slimeframe.slimefun.groups.Groups;
import me.voper.slimeframe.slimefun.items.multiblocks.Foundry;
import me.voper.slimeframe.slimefun.items.resources.MobDropItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;
import static me.voper.slimeframe.slimefun.SFrameStacks.*;

public class registerOthers {
    public static SlimefunItemStack BLUE_POTATO = new SlimefunItemStack(
            "SFE_OROIN_CATALYZER",
            Material.POTATO,
            ChatColor.BLUE + properties.getReplacedProperty("Others_Orokin_Catalyzer"),
            properties.getReplacedProperty("Others_Orokin_Catalyzer_Lore")
    );

    public static SlimefunItemStack GOLDEN_POTATO = new SlimefunItemStack(
            "SFE_OROKIN_REACTOR",
            Material.BAKED_POTATO,
            ChatColor.YELLOW + properties.getReplacedProperty("Others_Orokin_Reactor"),
            properties.getReplacedProperty("Others_Orokin_Reactor_Lore")
    );

    public static SlimefunItemStack FORMA = new SlimefunItemStack(
            "SFE_FORMA",
            Material.GOLD_INGOT,
            ChatColor.YELLOW +properties.getReplacedProperty("Others_Forma"),
            properties.getReplacedProperty("Others_Forma_Lore")
    );

    public static SlimefunItemStack OXIUM = new SlimefunItemStack(
            "SFE_OXIUM",
            Material.GLOWSTONE,
            ChatColor.GOLD + properties.getReplacedProperty("Material_Oxium"),
            ChatColor.GRAY + properties.getReplacedProperty("Material_Oxium_Lore")
    );
    public static void setup(SlimeFrameExtension plugin){
        new UnplaceableBlock(Group.MATERIALS, BLUE_POTATO, Foundry.RECIPE_TYPE, new ItemStack[]{
                OROKIN_CELL,                 MORPHICS,                      CONTROL_MODULE,
                GALLIUM,                     new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)
        }).register(plugin);

        new UnplaceableBlock(Group.MATERIALS, GOLDEN_POTATO, Foundry.RECIPE_TYPE, new ItemStack[]{
                OROKIN_CELL,                    NEURODES,               NEURAL_SENSORS,
                MORPHICS,                   new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)
        }).register(plugin);

        new UnplaceableBlock(Group.MATERIALS, FORMA, Foundry.RECIPE_TYPE, new ItemStack[]{
                new ItemStack(Material.AIR),    NEURODES,                   NEURAL_SENSORS,
                         MORPHICS,              OROKIN_CELL,                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR), new ItemStack(Material.AIR),   new ItemStack(Material.AIR)
        }).register(plugin);
        new MobDropItem(Groups.RESOURCES, OXIUM)
                .setMobChanceMap(Map.of(
                        EntityType.BLAZE, 20,
                        EntityType.PHANTOM, 80,
                        EntityType.GHAST, 100))
                .register(plugin);

    }
}
