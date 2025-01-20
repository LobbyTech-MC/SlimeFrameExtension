package io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.voper.slimeframe.core.attributes.AdvancedMobDrop;
import me.voper.slimeframe.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static me.voper.slimeframe.implementation.items.resources.MobDropItem.RECIPE_TYPE;

public class AbstractMobDropModifier extends AbstractModifier implements AdvancedMobDrop, RecipeDisplayItem {

    private int globalDrobChance = 0;
    private Map<EntityType, Integer> mobChanceMap = new HashMap<>();

    public AbstractMobDropModifier(ItemGroup itemGroup, SlimefunItemStack item, String modifierType) {
        super(itemGroup, item, RECIPE_TYPE, Utils.NULL_ITEMS_ARRAY);
        ModifierSetLevel(item, 0, modifierType, 0);
        //modifierType: [normal, riven, exilus, aura, stance, strange, prime] [type] [name] [equipmentType] ...[additional]
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        Validate.notEmpty(mobChanceMap, "The method setMobChanceMap must be called before registering the item");
        for (EntityType mob : mobChanceMap.keySet()) {
            Set<ItemStack> drops = Slimefun.getRegistry().getMobDrops().getOrDefault(mob, new HashSet<>());
            drops.add(getItem());
            Slimefun.getRegistry().getMobDrops().put(mob, drops);
        }
        super.register(addon);
    }


    @Override
    public boolean useVanillaBlockBreaking() {
        return false;
    }

    public AbstractMobDropModifier setMobs(List<EntityType> mobList) {
        this.mobChanceMap.putAll(mobList.stream()
                .collect(Collectors.toMap(mob -> mob, mob -> globalDrobChance)));

        return this;
    }

    public AbstractMobDropModifier setMobChanceMap(@Nonnull Map<EntityType, Integer> mobChanceMap) {
        this.mobChanceMap = new HashMap<>(mobChanceMap);
        return this;
    }

    public AbstractMobDropModifier setDropChance(EntityType entity, int dropChance) {
        mobChanceMap.put(entity, dropChance);
        return this;
    }

    public AbstractMobDropModifier setDropChance(int chance) {
        mobChanceMap.replaceAll((entityType, integer) -> chance);
        this.globalDrobChance = chance;
        return this;
    }

    @Override
    public int getMobDropChance() {
        return globalDrobChance;
    }

    @Override
    public int getMobDropChance(EntityType entity) {
        return mobChanceMap.getOrDefault(entity, 0);
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7⇩ 该物品由以下生物掉落 ⇩";
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> recipes = new ArrayList<>();
        mobChanceMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(
                        entry -> {
                            recipes.add(new CustomItemStack(Material.PAPER,
                                    ChatColor.WHITE + "掉落几率: " + ChatColor.AQUA + entry.getValue() + "%"));
                            Material material;
                            if (entry.getKey().name().equals("WITHER")) {
                                material = Material.WITHER_SKELETON_SPAWN_EGG;
                            } else if (entry.getKey().name().equals("ENDER_DRAGON")) {
                                material = Material.ENDERMAN_SPAWN_EGG;
                            } else {
                                material = Material.getMaterial(entry.getKey().name() + "_SPAWN_EGG");
//                        SlimeFrame.getInstance().getLogger().log(Level.WARNING, entry.getKey().name());
                            }
                            if (material != null) {
                                CustomItemStack spawnEgg = new CustomItemStack(material, ChatColor.WHITE + entry.getKey().name().toLowerCase());
                                recipes.add(spawnEgg);
                            }
                        });

        return recipes;
    }
}
