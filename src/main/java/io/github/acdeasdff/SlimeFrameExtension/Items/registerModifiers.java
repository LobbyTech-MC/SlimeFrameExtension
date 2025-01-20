package io.github.acdeasdff.SlimeFrameExtension.Items;

import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.Material;

public class registerModifiers {

    public static SlimefunItemStack BLANK_MODIFIER = new SlimefunItemStack("SFE_BLANK_MODIFIER",
            Material.BROWN_DYE,
            "SFE_BLANK_MODIFIER_NAME",
            "SFE_BLANK_MODIFIER_LORE");

    public static void setup(SlimeFrameExtension plugin) {
//        BLANK_MODIFIER.setItemMeta(BLANK_MODIFIER.getItemMeta().);
//        new BlankModifier(Group.MODIFIERS, BLANK_MODIFIER, RecipeType.MOB_DROP, new ItemStack[] {null, null, null, null,
//                new CustomItemStack(SlimefunUtils.getCustomHead(HeadTexture.IRON_GOLEM.getTexture()), "&aIron Golem")
//                , null, null, null, null}).register(plugin);
    }
}
