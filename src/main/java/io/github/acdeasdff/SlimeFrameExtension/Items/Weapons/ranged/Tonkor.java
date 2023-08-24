package io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged;

import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractRangedWeapon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.inventory.ItemStack;

import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;
import static me.voper.slimeframe.utils.Colors.GOLD_2;

public class Tonkor extends AbstractRangedWeapon {
    public Tonkor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe){
        super(itemGroup, item, recipeType, recipe,
                new double[]{
                        0, //pure
                        0, 0, 37.5, //slash impact puncture
                        0, 0, 0, 0,//heat cold toxin electricity
                        325., 0, 0, 0, 0, 0//blast gas radiation viral magnetic corrosive
                }
                , 1.0
                , 1
                , 1700
                , 315
                , 0.
                , 0.25
                , 2.5
                , 0.1
                , 5000
                , 1
                , 0.
                , 3.5
                , Instrument.BASS_DRUM
                , Note.flat(1, Note.Tone.F)
                , false
        );
    }

    public static final SlimefunItemStack TONKOR = new SlimefunItemStack(
            "SFE_TONKOR",
            Material.IRON_HOE,
            GOLD_2 + properties.getReplacedProperty("Weapon_Tonkor"),
            ChatColor.GRAY + properties.getReplacedProperty("Weapon_Tonkor_Lore")
    );
}
