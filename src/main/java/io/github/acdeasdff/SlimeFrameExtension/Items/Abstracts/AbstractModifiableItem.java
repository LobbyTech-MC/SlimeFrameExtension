package io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts;

import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.*;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.EquipmentInstance;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.ModifierInstance;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.PotatoableInstance;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.DistinctiveItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.MODIFIER_ITEM;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Other.ModifierStatusRecorder.*;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;

public abstract class AbstractModifiableItem extends SimpleSlimefunItem<ItemUseHandler> implements DistinctiveItem {
    String id;

    public AbstractModifiableItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }


    @Override
    public boolean canStack(@NotNull ItemMeta itemMeta, @NotNull ItemMeta itemMeta1) {
        return itemMeta.equals(itemMeta1);
    }

    public static void ModifyItem(ItemStack modifiableItem, ItemStack[] mods, String itemType, boolean[] isFormaed){
        ItemStack[] modsMaterial = new ItemStack[mods.length];
        ItemMeta[] modsMeta = new ItemMeta[mods.length];
        ItemMeta itemMeta = modifiableItem.getItemMeta();

        String[] loreBuilder = new String[mods.length + 1];

        List<String> itemLore = new ArrayList<>();

        if (itemMeta != null){
             itemLore = itemMeta.getLore();
        }
        if (itemLore != null){
            loreBuilder[0] = itemLore.get(0);
        }else {
            loreBuilder[0] = "0/1";
        }

        for (int i=0; i<mods.length;i++){
            if (mods[i] != null){
                ItemMeta modMeta = mods[i].getItemMeta();
                if (modMeta != null){
                    Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(modMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                    if (optional2.isPresent()) {
                        ModifierInstance modifierInstance = optional2.get();
                        String modName = modifierInstance.getModifierType().split("_")[2];

                        modsMaterial[i] = new ItemStack(mods[i].getType(), mods[i].getAmount());
                        modsMeta[i] = mods[i].getItemMeta();
                        loreBuilder[i + 1] =
                                getColorFromType(modifierRarityType.get(modName), modifierType.get(modName))
                                + modifierName.get(modName) + " " + modifierInstance.getLevel() + "/" + modifierMaxLevel.get(modName);
                    }
                }
            }
        }

        DataTypeMethods.setCustom(itemMeta, Keys.MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE, new EquipmentInstance(modsMaterial, modsMeta, itemType, isFormaed));
        itemMeta.setLore(Arrays.asList(loreBuilder));
        modifiableItem.setItemMeta(itemMeta);
    }

}
