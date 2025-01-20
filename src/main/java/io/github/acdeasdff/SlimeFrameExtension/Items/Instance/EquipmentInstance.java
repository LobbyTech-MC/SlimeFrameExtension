package io.github.acdeasdff.SlimeFrameExtension.Items.Instance;

import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractRangedWeapon;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;


public class EquipmentInstance {

    //    double[] standardMODAdder = new double[]{
//            0,//DamageMultiplier
//            0,0,0,//multipliers for slash, impact, puncture
//            0,0,0,0,//multipliers for heat, cold, toxin, electricity
//            0,0,0,0,0,0,//multipliers for blast, gas, radiation, viral, magnetic, corrosive
//            0,0,0,0,//multipliers for criticalChance, criticalDamage, statusChance, statusTime
//            0,0,0,0,0,0,0,//multiShot,punch(Meters),cooldown,ammoScale,reloadSpeed,explosionRadius,spread
//            0//keep it for st. like ConditionOverload
//    };
    private final double[] MODAdder;
    private final ItemStack[] Modifiers;
    private final ItemStack[] ModifierMaterials;
    private ItemMeta[] ModifierMetas;
    private String itemType;
    private boolean[] isFormaed;


//    private final ItemStack outputItem;

//    public BlueprintInstance(@Nonnull ItemStack[] recipeItems, @Nonnull ItemStack expectedOutput) {
//        super(expectedOutput);
//        this.recipeItems = recipeItems;
//        this.outputMeta = expectedOutput.getItemMeta();
//        this.outputItem = expectedOutput.clone();
//        if (!(outputMeta==null)){
//            itemHasMeta = true;
//            this.outputItem.setItemMeta(outputMeta);
//        }else {itemHasMeta = false;outputItem.setItemMeta(null);}
//    }

    public EquipmentInstance(@Nonnull ItemStack[] Modifiers, ItemMeta[] ModifierMetas, String itemType, boolean[] isFormaed) {
//        super(expectedOutput);
        this.ModifierMaterials = Modifiers;
        this.Modifiers = Modifiers;
        this.ModifierMetas = ModifierMetas;
        this.itemType = itemType;
        this.isFormaed = isFormaed;
        for (int i = 0; i < Modifiers.length; i++) {
            ItemStack modifier = Modifiers[i];
            if (modifier != null) {
                Modifiers[i].setItemMeta(ModifierMetas[i]);
            }
        }
        this.MODAdder = AbstractRangedWeapon.calculateMODAdderFromMODs(Modifiers);
    }


    public ItemStack[] getModifiers() {
        return Modifiers;
    }

    public ItemMeta[] getModifierMetas() {
        return ModifierMetas;
    }

    public ItemStack[] getModifierMaterials() {
        return ModifierMaterials;
    }

    public String getItemType() {
        return itemType;
    }

    public boolean[] getFormaed() {
        return isFormaed;
    }

    public double[] getMODAdder() {
        return MODAdder;
    }
}

