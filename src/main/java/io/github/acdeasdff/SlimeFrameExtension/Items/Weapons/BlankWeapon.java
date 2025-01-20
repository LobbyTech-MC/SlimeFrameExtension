package io.github.acdeasdff.SlimeFrameExtension.Items.Weapons;

import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractRangedWeapon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.inventory.ItemStack;

public class BlankWeapon extends AbstractRangedWeapon {
    //only for testing
    public BlankWeapon(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe,
                new double[]{
                        0, //pure
                        1, 1, 3,//slash impact puncture
                        0, 0, 0, 0,//heat cold toxin electricity
                        0, 0, 0, 0, 0, 0//blast gas radiation viral magnetic corrosive
                }
                , 1.0
                , 30
                , 4000
                , 50
                , 0.5
                , 0.2
                , 2.2
                , 1.65
                , 5000
                , 2
                , 3
                , 0.
                , Instrument.STICKS
                , Note.flat(1, Note.Tone.F)
                , true
        );
    }

}
