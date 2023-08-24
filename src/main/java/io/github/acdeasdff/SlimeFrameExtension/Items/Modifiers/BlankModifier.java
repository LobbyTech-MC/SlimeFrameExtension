package io.github.acdeasdff.SlimeFrameExtension.Items.Modifiers;

import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractNormalModifier;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;



public class BlankModifier extends AbstractNormalModifier {
    public BlankModifier(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        ModifierSetLevel(item, 0, "normal_blank_name", 0);
        //modifierType: [normal, riven, exilus, aura, stance, strange, prime] [type] [name] [equipmentType] ...[additional]
    }
}
