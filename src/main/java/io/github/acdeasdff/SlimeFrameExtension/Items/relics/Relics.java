package io.github.acdeasdff.SlimeFrameExtension.Items.relics;

import io.github.acdeasdff.SlimeFrameExtension.Groups.Group;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.voper.slimeframe.implementation.items.relics.Relic;
import me.voper.slimeframe.implementation.items.relics.RelicItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.BRATON_PRIME.*;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.RUBICO_PRIME.*;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;
import static me.voper.slimeframe.implementation.SFrameStacks.*;
import static me.voper.slimeframe.implementation.items.relics.Relic.Era.MESO;
import static me.voper.slimeframe.implementation.items.relics.Relic.Era.NEO;

public class Relics {
    public static List<RelicItemStack> relics = new ArrayList<>();
    public static RelicItemStack MESO_R1 = new RelicItemStack(properties.getReplacedProperty("MESO_R1"), MESO,
            new SlimefunItemStack[]{BRATON_PRIME_TRIGGER, RUBICO_PRIME_TRIGGER},
            new SlimefunItemStack[]{BRATON_PRIME_BLUEPRINT, RUBICO_PRIME_BARREL},
            RUBICO_PRIME_BLUEPRINT
    );
    public static RelicItemStack NEO_R1 = new RelicItemStack(properties.getReplacedProperty("NEO_R1"), NEO,
            new SlimefunItemStack[]{BRATON_PRIME_BARREL, RUBICO_PRIME_TRIGGER},
            new SlimefunItemStack[]{BRATON_PRIME_BODY, RUBICO_PRIME_BODY},
            RUBICO_PRIME_BLUEPRINT
    );


    public static void setup(SlimeFrameExtension plugin) {
        relics.add(MESO_R1);
        relics.add(NEO_R1);

        for (RelicItemStack relicItemStack : relics) {
            switch (relicItemStack.getRelicEra()) {
                case LITH -> RANDOM_LITH_RELICS.add(relicItemStack);
                case MESO -> RANDOM_MESO_RELICS.add(relicItemStack);
                case NEO -> RANDOM_NEO_RELICS.add(relicItemStack);
                default -> RANDOM_AXI_RELICS.add(relicItemStack);
            }
            Relic.create(relicItemStack).register(plugin);
        }

        for (RelicItemStack relicItemStack : relics) {
            List<SlimefunItemStack> drops = new ArrayList<>();
            for (SlimefunItemStack sfis : relicItemStack.getCommonDrops()) {
                drops.add(sfis);
            }

            for (SlimefunItemStack sfis : relicItemStack.getUncommonDrops()) {
                drops.add(sfis);
            }

            drops.add(relicItemStack.getRareDrop());

            RecipeType recipeType = new RecipeType(new NamespacedKey(SlimeFrameExtension.instance(), "relic_recipe_" + relicItemStack.getItemId().toLowerCase()), relicItemStack);
            for (SlimefunItemStack sfis : drops) {
                if (SlimefunItem.getByItem(sfis) == null) {
                    // item not registered yet, register it
                    new SlimefunItem(Group.WEAPONS, sfis, recipeType, new ItemStack[] {
                            null, null, null,
                            null, getBukkit(relicItemStack), null,
                            null, null, null
                    }).register(SlimeFrameExtension.instance());
                }
            }
        }
    }

    @Nonnull
    public static ItemStack getBukkit(ItemStack itemStack) {
        if (itemStack == null) {
            return new ItemStack(Material.AIR);
        }

        ItemStack clone = new ItemStack(itemStack.getType());
        clone.setAmount(itemStack.getAmount());
        ItemMeta meta = clone.getItemMeta();
        if (meta != null) {
            clone.setItemMeta(meta);
        }

        return clone;
    }
}
