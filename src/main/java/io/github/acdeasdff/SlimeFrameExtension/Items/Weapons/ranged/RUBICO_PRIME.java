package io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged;

import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractRangedWeapon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.voper.slimeframe.utils.Colors;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;

public class RUBICO_PRIME extends AbstractRangedWeapon {
    public static List<String> RUBICO_PRIME_LORE = properties.getReplacedProperties("Weapon_RUBICO_PRIME_Lore1", 0, ChatColor.GRAY);
    public static SlimefunItemStack RUBICO_PRIME = new SlimefunItemStack(
            "SFE_RUBICO_PRIME",
            Material.GOLDEN_HOE,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Name"),
            RUBICO_PRIME_LORE.toArray(new String[0])
    );
    public static SlimefunItemStack RUBICO_PRIME_BARREL = new SlimefunItemStack(
            "SFE_RUBICO_PRIME_BARREL",
            Material.GOLD_INGOT,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Gun_Barrel_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Gun_Barrel_Lore")
    );
    public static SlimefunItemStack RUBICO_PRIME_TRIGGER = new SlimefunItemStack(
            "SFE_RUBICO_PRIME_TRIGGER",
            Material.GOLD_NUGGET,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Gun_Trigger_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Gun_Trigger_Lore")
    );
    public static SlimefunItemStack RUBICO_PRIME_BODY = new SlimefunItemStack(
            "SFE_RUBICO_PRIME_BODY",
            Material.GOLD_BLOCK,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Gun_Body_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Gun_Body_Lore")
    );
    public static SlimefunItemStack RUBICO_PRIME_BLUEPRINT = new SlimefunItemStack(
            "SFE_RUBICO_PRIME_BLUEPRINT",
            Material.GOLD_BLOCK,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Blueprint_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_RUBICO_PRIME_Blueprint_Lore")
    );

    public RUBICO_PRIME(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe,
                new double[]{
                        0, //pure
                        4.65, 74.7, 14.05, //slash impact puncture
                        0, 0, 0, 0,//heat cold toxin electricity
                        0, 0, 0, 0, 0, 0//blast gas radiation viral magnetic corrosive
                }
                , 1.0
                , 5
                , 2000
                , 375
                , 0.01
                , 0.38
                , 3.5
                , 0.16
                , 5000
                , 4
                , 1.
                , 0.
                , Instrument.SNARE_DRUM
                , Note.flat(1, Note.Tone.F)
                , true
        );
    }


}
