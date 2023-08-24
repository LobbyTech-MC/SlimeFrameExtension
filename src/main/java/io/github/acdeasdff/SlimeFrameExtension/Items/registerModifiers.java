package io.github.acdeasdff.SlimeFrameExtension.Items;

import io.github.acdeasdff.SlimeFrameExtension.Groups.Group;
import io.github.acdeasdff.SlimeFrameExtension.Items.Modifiers.BlankModifier;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;

import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.MODIFIER_ITEM;

public class registerModifiers {

    public static SlimefunItemStack BLANK_MODIFIER = new SlimefunItemStack("SFE_BLANK_MODIFIER",
            Material.BROWN_DYE,
            "SFE_BLANK_MODIFIER_NAME",
            "SFE_BLANK_MODIFIER_LORE");
    public static void setup(SlimeFrameExtension plugin){
//        BLANK_MODIFIER.setItemMeta(BLANK_MODIFIER.getItemMeta().);
//        new BlankModifier(Group.MODIFIERS, BLANK_MODIFIER, RecipeType.MOB_DROP, new ItemStack[] {null, null, null, null,
//                new CustomItemStack(SlimefunUtils.getCustomHead(HeadTexture.IRON_GOLEM.getTexture()), "&aIron Golem")
//                , null, null, null, null}).register(plugin);
    }
}
