package io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts;

import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.DataTypeMethods;
import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys;
import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.PersistentSFEModifierType;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.ModifierInstance;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractModifier extends SlimefunItem implements DistinctiveItem {


    public AbstractModifier(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    public static void ModifierSetLevel(ItemStack modifier, int level, String modifierType, int basicSize) {

        ItemMeta itemMeta = modifier.getItemMeta();

        DataTypeMethods.setCustom(itemMeta, Keys.MODIFIER_ITEM, PersistentSFEModifierType.TYPE, new ModifierInstance(level, modifierType, basicSize));

        modifier.setItemMeta(itemMeta);
    }

    @Override
    public boolean canStack(@NotNull ItemMeta itemMeta, @NotNull ItemMeta itemMeta1) {
        return itemMeta.equals(itemMeta1);
    }
}
