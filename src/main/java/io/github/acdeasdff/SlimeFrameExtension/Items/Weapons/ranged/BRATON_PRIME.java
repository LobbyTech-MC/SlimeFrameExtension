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

public class BRATON_PRIME extends AbstractRangedWeapon {
    public static List<String> BRATON_PRIME_LORE = properties.getReplacedProperties("Weapon_Braton_Prime_Lore1", 0, ChatColor.GRAY);
    public static SlimefunItemStack BRATON_PRIME = new SlimefunItemStack(
            "SFE_BRATON_PRIME",
            Material.GOLDEN_PICKAXE,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_Braton_Prime_Name"),
            BRATON_PRIME_LORE.toArray(new String[0])
    );
    public static SlimefunItemStack BRATON_PRIME_BARREL = new SlimefunItemStack(
            "SFE_BRATON_PRIME_BARREL",
            Material.GOLD_INGOT,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_Braton_Prime_Gun_Barrel_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_Braton_Prime_Gun_Barrel_Lore")
    );
    public static SlimefunItemStack BRATON_PRIME_TRIGGER = new SlimefunItemStack(
            "SFE_BRATON_PRIME_TRIGGER",
            Material.GOLD_NUGGET,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_Braton_Prime_Gun_Trigger_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_Braton_Prime_Gun_Trigger_Lore")
    );
    public static SlimefunItemStack BRATON_PRIME_BODY = new SlimefunItemStack(
            "SFE_BRATON_PRIME_BODY",
            Material.GOLD_BLOCK,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_Braton_Prime_Gun_Body_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_Braton_Prime_Gun_Body_Lore")
    );
    public static SlimefunItemStack BRATON_PRIME_BLUEPRINT = new SlimefunItemStack(
            "SFE_BRATON_PRIME_BLUEPRINT",
            Material.GOLD_BLOCK,
            Colors.GOLD_2 + properties.getReplacedProperty("Weapon_Braton_Prime_Blueprint_Name"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_Braton_Prime_Blueprint_Lore")
    );

    public BRATON_PRIME(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe,
                new double[]{
                        0, //pure
                        1, 6, 10,//slash impact puncture
                        0, 0, 0, 0,//heat cold toxin electricity
                        0, 0, 0, 0, 0, 0//blast gas radiation viral magnetic corrosive
                }
                , 1.0
                , 75
                , 2150
                , 104
                , 0.02
                , 0.12
                , 2.0
                , 0.26
                , 5000
                , 4
                , 0.
                , 0.
                , Instrument.BIT
                , Note.flat(1, Note.Tone.F)
                , true
        );
    }

}
