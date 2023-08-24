package io.github.acdeasdff.SlimeFrameExtension.Items;

import io.github.acdeasdff.SlimeFrameExtension.Groups.Group;
import io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.BlankWeapon;
import io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.BRATON_PRIME;
import io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.RUBICO_PRIME;
import io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.Tonkor;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.voper.slimeframe.utils.Colors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.BRATON_PRIME.*;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.RUBICO_PRIME.*;
import static io.github.acdeasdff.SlimeFrameExtension.Items.registerOthers.OXIUM;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;
import static me.voper.slimeframe.slimefun.SFrameStacks.*;
import static me.voper.slimeframe.slimefun.items.multiblocks.Foundry.RECIPE_TYPE;

public class registerWeapons {


    public static ItemStack OROKIN_CELL_10 = OROKIN_CELL.clone();

//    public static SlimefunItemStack BLANK_WEAPON = new SlimefunItemStack(
//            "SFE_BLANK_WEAPON",
//            Material.DIAMOND_HOE,
//            "SFE_BLANK_WEAPON_NAME",
//            "SFE_BLANK_WEAPON_LORE"
//    );
    public static ItemStack SALVAGE_64 = SALVAGE.clone();
    public static ItemStack SALVAGE_22 = SALVAGE.clone();
    public static ItemStack OXIUM_20 = OXIUM.clone();
    public static ItemStack CRYOTIC_64 = CRYOTIC.clone();
    public static ItemStack CRYOTIC_16 = CRYOTIC.clone();
    public static ItemStack ARGON_CRYSTAL_2 = ARGON_CRYSTAL.clone();
    public static void setup(SlimeFrameExtension plugin){
        OROKIN_CELL_10.setAmount(10);
        SALVAGE_64.setAmount(64);
        SALVAGE_22.setAmount(22);
        OXIUM_20.setAmount(20);
        CRYOTIC_64.setAmount(64);
        CRYOTIC_16.setAmount(16);
        ARGON_CRYSTAL_2.setAmount(2);

//        new BlankWeapon(Group.WEAPONS, BLANK_WEAPON, RecipeType.MOB_DROP,
//                new ItemStack[] {null, null, null, null,
//                        new CustomItemStack(SlimefunUtils.getCustomHead(HeadTexture.IRON_GOLEM.getTexture()), "&aIron Golem")
//                        , null, null, null, null}).register(plugin);

        new BRATON_PRIME(Group.WEAPONS, BRATON_PRIME, RECIPE_TYPE, new ItemStack[]{
                BRATON_PRIME_BODY, BRATON_PRIME_TRIGGER, BRATON_PRIME_BARREL,
                OROKIN_CELL_10,BRATON_PRIME_BLUEPRINT,null,
                null,null,null
        }).register(plugin);

        new RUBICO_PRIME(Group.WEAPONS, RUBICO_PRIME, RECIPE_TYPE, new ItemStack[]{
                RUBICO_PRIME_BODY, RUBICO_PRIME_TRIGGER, RUBICO_PRIME_BARREL,
                OROKIN_CELL_10,RUBICO_PRIME_BLUEPRINT,null,
                null,null,null
        }).register(plugin);

        new Tonkor(Group.WEAPONS, Tonkor.TONKOR, RECIPE_TYPE, new ItemStack[]{
                SALVAGE_64, SALVAGE_64, SALVAGE_22,
                OXIUM_20, CRYOTIC_64, CRYOTIC_16,
                ARGON_CRYSTAL_2,null,null
        }).register(plugin);
    }
}
