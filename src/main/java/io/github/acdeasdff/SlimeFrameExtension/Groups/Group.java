package io.github.acdeasdff.SlimeFrameExtension.Groups;

import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.mooy1.infinitylib.groups.MultiGroup;
import io.github.mooy1.infinitylib.groups.SubGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.voper.slimeframe.SlimeFrame;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;

import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;
import static me.voper.slimeframe.slimefun.groups.Groups.MAIN_GROUP;

public final class Group {

//    public static final NamespacedKey SFE_MODIFIER_KEY = createKey("slimeframeextension_modifiers");
//    public static final NamespacedKey SFE_BASIC_MACHINES_KEY = createKey("slimeframeextension_basic_machines");
//    public static final NamespacedKey SFE_MATERIAL_KEY = createKey("slimeframeextension_materials");
//    public static final NamespacedKey SFE_WEAPON_KEY = createKey("slimeframeextension_weapons");
//    public static final NamespacedKey SFE_MAIN_KEY = createKey("slimeframeextension_main");
    public static final ItemGroup MODIFIERS = new SubGroup(
            "slimeframeextension_modifiers",
            new CustomItemStack(Material.BROWN_DYE, properties.getReplacedProperty("SFE_MOD")
            ));
    public static final ItemGroup BASIC_MACHINES = new SubGroup(
            "slimeframeextension_basic_machines",
            new CustomItemStack(Material.CRAFTING_TABLE, properties.getReplacedProperty("SFE_BASIC_MACHINES"))
    );

//    public static final ItemGroup ADVANCED_MACHINES = new SubGroup("slimeframeextension_advanced_machines",
//            new CustomItemStack(Material.BLAST_FURNACE, "&b[SFE]&c高级&7机器"));
    public static final ItemGroup MATERIALS = new SubGroup(
        "slimeframeextension_materials",
            new CustomItemStack(Material.NETHERITE_BLOCK, properties.getReplacedProperty("SFE_MATERIAL")));

    public static final ItemGroup WEAPONS = new SubGroup(
            "slimeframeextension_weapons",
            new CustomItemStack(Material.NETHERITE_BLOCK, properties.getReplacedProperty("SFE_WEAPON")));

    public static final ItemGroup MAIN_CATEGORY = new MultiGroup(
            "slimeframeextension_main",
            new CustomItemStack(Material.YELLOW_DYE, properties.getReplacedProperty("SFE_MAIN_GROUP")
            ), 3,
            MODIFIERS, BASIC_MACHINES, MATERIALS, WEAPONS);
    public static void setup(SlimeFrameExtension inst) {
        MAIN_CATEGORY.register(inst);
    }

//    @Nonnull
//    @Contract("_ -> new")
//    public static NamespacedKey createKey(@Nonnull String key) {
//        return new NamespacedKey(SlimeFrameExtension.instance, key);
//    }
}
