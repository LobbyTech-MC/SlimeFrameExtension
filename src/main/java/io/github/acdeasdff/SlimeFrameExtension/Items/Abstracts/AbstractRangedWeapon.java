package io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts;

import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.DataTypeMethods;
import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.PersistentSFEEquipmentType;
import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.PersistentSFEModifierType;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.EquipmentInstance;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.ModifierInstance;
import io.github.acdeasdff.SlimeFrameExtension.Listeners.BulletListener;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.MODIFIABLE_INSTANCE;
import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.MODIFIER_ITEM;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Other.ModifierStatusRecorder.modifierStatusMap;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;

public abstract class AbstractRangedWeapon extends AbstractWeapons {

    public static final double[] standardMODAdder = new double[]{
            0,//0:damage(Multiplier)
            0, 0, 0,//multipliers for 1:slash, 2:impact, 3:puncture
            0, 0, 0, 0,//multipliers for 4:heat, 5:cold, 6:toxin, 7:electricity
            0, 0, 0, 0, 0, 0,//multipliers for 8:blast, 9:gas, 10:radiation, 11:viral, 12:magnetic, 13:corrosive ; may not be used
            0, 0, 0, 0,//multipliers for 14:criticalChance, 15:criticalDamage, 16:statusChance, 17:statusTime
            0, 0, 0, 0, 0, 0, 0,//18:multiShot,19:punch(Meters),20:cooldown,21:ammoScale,22:reloadSpeed,23:explosionRadius,24:spread
            0//25:keep it for st. like ConditionOverload;
    };//if U want it,DO CLONE.
    public static final NamespacedKey LAST_RELOAD = AbstractAddon.createKey("last_reload");
    public static final NamespacedKey LAST_USE = AbstractAddon.createKey("last_use");
    public double[] basicDamage;
    public double multiShot;
    public int ammoScale;
    public int reloadTime;//unit:ms
    public int cooldown;//unit:ms
    public double spread;

    Instrument instrument;
    Note note;

    double criticalMultipier;
    double criticalChance;
    double statusChance;
    double statusTime;
    double ammoSpeedMultiplier;
    double punchThrough;
    double explodeLevel;
    double[] MODAdder;
    boolean offGravity;
    Random random = new Random();


    public AbstractRangedWeapon(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe
            , double[] basicDamage, double multiShot, int ammoScale, int reloadTime, int cooldown, double spread
            , double criticalChance, double criticalMultiplier
            , double statusChance, double statusTime, double ammoSpeedMultiplier, double punchThrough, double explodeLevel
            , Instrument instrument, Note note, boolean offGravity) {
        super(itemGroup, item, recipeType, recipe);
        this.basicDamage = basicDamage;
        this.multiShot = multiShot;
        this.ammoScale = ammoScale;
        this.reloadTime = reloadTime;
        this.cooldown = cooldown;
        this.spread = spread;
        this.criticalChance = criticalChance;
        this.criticalMultipier = criticalMultiplier;
        this.instrument = instrument;
        this.note = note;
        this.statusChance = statusChance;
        this.statusTime = statusTime;
        this.ammoSpeedMultiplier = ammoSpeedMultiplier;
        this.punchThrough = punchThrough;
        this.explodeLevel = explodeLevel;
        boolean[] isFormaed = new boolean[]{
                false,
                false, false, false, false,
                false, false, false, false
        };
        ModifyItem(item, new ItemStack[9], "ranged", isFormaed);
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ammoScale + "/" + ammoScale);
        if (itemMeta != null && itemMeta.getLore() != null) {
            lore.addAll(itemMeta.getLore());
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        this.MODAdder = standardMODAdder.clone();
        if (item.getItemMeta() != null) {
            Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(item.getItemMeta(), MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
            optional.ifPresent(equipmentInstance -> MODAdder = equipmentInstance.getMODAdder());
        }
        this.offGravity = offGravity;
    }

    public static double[] calculateMODAdderFromMODs(ItemStack[] mods) {
        double[] MODAdder = new double[]{
                0,//0:damage(Multiplier)
                0, 0, 0,//multipliers for 1:slash, 2:impact, 3:puncture
                0, 0, 0, 0,//multipliers for 4:heat, 5:cold, 6:toxin, 7:electricity
                0, 0, 0, 0, 0, 0,//multipliers for 8:blast, 9:gas, 10:radiation, 11:viral, 12:magnetic, 13:corrosive ; may not be used
                0, 0, 0, 0,//multipliers for 14:criticalChance, 15:criticalDamage, 16:statusChance, 17:statusTime
                0, 0, 0, 0, 0, 0, 0,//18:multiShot,19:punch(Meters),20:cooldown,21:ammoScale,22:reloadSpeed,23:explosionRadius,24:spread
                0//25:keep it for st. like ConditionOverload;
        };
        List<String> CalculatedMods = new ArrayList<>();
        for (ItemStack mod : mods) {
            if (mod != null) {
                ItemMeta modMeta = mod.getItemMeta();
                if (modMeta != null) {
                    Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(modMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                    if (optional2.isPresent()) {
                        ModifierInstance modifierInstance = optional2.get();
                        int modLevel = modifierInstance.getLevel();
                        String modName = modifierInstance.getModifierType().split("_")[2];
                        if (CalculatedMods.contains(modName)) {
                            continue;
                        } else {
                            CalculatedMods.add(modName);
                        }//I want only the first works when some 1 put 2(or more) same mods into a gun.
                        Map<String, Double> modAdd = modifierStatusMap.get(modName);
                        Set<String> modAddString = modAdd.keySet();

                        for (String s : modAddString) {
                            switch (s) {
                                case "damage" -> MODAdder[0] += modAdd.get(s) * (modLevel + 1);
                                case "slashDamageMultiplier" -> MODAdder[1] += modAdd.get(s) * (modLevel + 1);
                                case "impactDamageMultiplier" -> MODAdder[2] += modAdd.get(s) * (modLevel + 1);
                                case "punctureDamageMultiplier" -> MODAdder[3] += modAdd.get(s) * (modLevel + 1);
                                case "heatDamageMultiplier" -> MODAdder[4] += modAdd.get(s) * (modLevel + 1);
                                case "coldDamageMultiplier" -> MODAdder[5] += modAdd.get(s) * (modLevel + 1);
                                case "toxinDamageMultiplier" -> MODAdder[6] += modAdd.get(s) * (modLevel + 1);
                                case "electricityDamageMultiplier" -> MODAdder[7] += modAdd.get(s) * (modLevel + 1);
                                case "blastDamageMultiplier" -> MODAdder[8] += modAdd.get(s) * (modLevel + 1);
                                case "gasDamageMultiplier" -> MODAdder[9] += modAdd.get(s) * (modLevel + 1);
                                case "radiationDamageMultiplier" -> MODAdder[10] += modAdd.get(s) * (modLevel + 1);
                                case "viralDamageMultiplier" -> MODAdder[11] += modAdd.get(s) * (modLevel + 1);
                                case "magneticDamageMultiplier" -> MODAdder[12] += modAdd.get(s) * (modLevel + 1);
                                case "corrosiveDamageMultiplier" -> MODAdder[13] += modAdd.get(s) * (modLevel + 1);
                                case "criticalChance" -> MODAdder[14] += modAdd.get(s) * (modLevel + 1);
                                case "criticalDamage" -> MODAdder[15] += modAdd.get(s) * (modLevel + 1);
                                case "statusChance" -> MODAdder[16] += modAdd.get(s) * (modLevel + 1);
                                case "statusTime" -> MODAdder[17] += modAdd.get(s) * (modLevel + 1);
                                case "multiShot" -> MODAdder[18] += modAdd.get(s) * (modLevel + 1);
                                case "punch" -> MODAdder[19] += modAdd.get(s) * (modLevel + 1);
                                case "cooldown" -> MODAdder[20] += modAdd.get(s) * (modLevel + 1);
                                case "ammoScale" -> MODAdder[21] += modAdd.get(s) * (modLevel + 1);
                                case "reloadSpeed" -> MODAdder[22] += modAdd.get(s) * (modLevel + 1);
                                case "explosionRadius" -> MODAdder[23] += modAdd.get(s) * (modLevel + 1);
                                case "spread" -> MODAdder[24] += modAdd.get(s) * (modLevel + 1);
                            }
                        }
                        //mixing damage type
                        {
                            if (MODAdder[8] != 0 || (MODAdder[4] != 0 && MODAdder[5] != 0)) {
                                MODAdder[8] += MODAdder[4] + MODAdder[5];
                                MODAdder[4] = 0;
                                MODAdder[5] = 0;
                            }

                            if (MODAdder[9] != 0 || (MODAdder[4] != 0 && MODAdder[6] != 0)) {
                                MODAdder[9] += MODAdder[4] + MODAdder[6];
                                MODAdder[4] = 0;
                                MODAdder[6] = 0;
                            }

                            if (MODAdder[10] != 0 || (MODAdder[4] != 0 && MODAdder[7] != 0)) {
                                MODAdder[10] += MODAdder[4] + MODAdder[7];
                                MODAdder[4] = 0;
                                MODAdder[7] = 0;
                            }
                            if (MODAdder[11] != 0 || (MODAdder[6] != 0 && MODAdder[5] != 0)) {
                                MODAdder[11] += MODAdder[6] + MODAdder[5];
                                MODAdder[6] = 0;
                                MODAdder[5] = 0;
                            }

                            if (MODAdder[12] != 0 || (MODAdder[5] != 0 && MODAdder[7] != 0)) {
                                MODAdder[12] += MODAdder[5] + MODAdder[7];
                                MODAdder[5] = 0;
                                MODAdder[7] = 0;
                            }

                            if (MODAdder[13] != 0 || (MODAdder[6] != 0 && MODAdder[7] != 0)) {
                                MODAdder[13] += MODAdder[6] + MODAdder[7];
                                MODAdder[6] = 0;
                                MODAdder[7] = 0;
                            }
                        }
                    }
                }
            }
        }
        return MODAdder;
    }

    @NotNull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.getItem();
            if (e.getItem().getType().equals(Material.AIR)
                    || e.getItem().getItemMeta() == null
                    || e.getItem().getItemMeta().getLore() == null) {
                return;
            }
            Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(e.getItem().getItemMeta(), MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
            optional.ifPresent(equipmentInstance -> this.MODAdder = equipmentInstance.getMODAdder());
            if (Integer.parseInt(
                    e.getItem().getItemMeta().getLore().get(0).split("/")[0])
                    > 0
                    && !e.getHand().equals(EquipmentSlot.OFF_HAND)) {
                if (cooldown(e, MODAdder)) {
                    DecreaseAmmo(e.getItem());
                    double tempMultiShot = multiShot + MODAdder[18];
                    while (true) {
                        if (tempMultiShot > 0) {
                            if (tempMultiShot > 1) {
                                tempMultiShot -= 1;
                                weaponLaunchProjectile(e, MODAdder);
                            } else if (new Random().nextDouble() <= tempMultiShot) {
                                weaponLaunchProjectile(e, MODAdder);
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            } else {
                reload(e, MODAdder);
            }
            e.cancel();
        };
    }

    void reload(PlayerRightClickEvent e, double[] MODAdder) {
        int tempReloadTime = (int) (reloadTime / (1 + MODAdder[22]));
        ItemMeta meta = e.getItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        long lastReload = container.getOrDefault(LAST_RELOAD, PersistentDataType.LONG, 0L);
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastReload) < tempReloadTime) {
            return;
        }

        container.set(LAST_RELOAD, PersistentDataType.LONG, currentTime);
        if (e.getItem().getItemMeta() != null) {
            Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(e.getItem().getItemMeta(), MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
            optional.ifPresent(equipmentInstance -> this.MODAdder = equipmentInstance.getMODAdder());
        }
        int tempAmmoScale = (int) (ammoScale * (1 + MODAdder[21]));
        meta.setLore(Collections.singletonList(tempAmmoScale + "/" + tempAmmoScale));
        e.getItem().setItemMeta(meta);

//        e.getPlayer().sendMessage(properties.getReplacedProperty("Reloading"));
        BulletListener.sendActionBar(e.getPlayer(), ChatColor.GRAY + properties.getReplacedProperty("Reloading"));

        {
            e.getPlayer().playNote(
                    e.getPlayer().getLocation()
                    , Instrument.BIT
                    , Note.sharp(1, Note.Tone.C));

            e.getPlayer().playNote(
                    e.getPlayer().getLocation()
                    , Instrument.BIT
                    , Note.natural(1, Note.Tone.F));

            e.getPlayer().playNote(
                    e.getPlayer().getLocation()
                    , Instrument.BIT
                    , Note.sharp(1, Note.Tone.G));

            e.getPlayer().playNote(
                    e.getPlayer().getLocation()
                    , Instrument.BIT
                    , Note.sharp(0, Note.Tone.G));
        }

    }

    //cooling down for each shot
    boolean cooldown(PlayerRightClickEvent e, double[] MODAdder) {
        int tempCooldown = (int) (cooldown / (1 + MODAdder[20]));
        int tempReloadTime = (int) (reloadTime / (1 + MODAdder[22]));
        ItemMeta meta = e.getItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        long lastUse = container.getOrDefault(LAST_USE, PersistentDataType.LONG, 0L);
        long lastReload = container.getOrDefault(LAST_RELOAD, PersistentDataType.LONG, 0L);
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastUse) < tempCooldown) {
            return false;
        }
        if ((currentTime - lastReload) < tempReloadTime) {
            return false;
        }//no shooting when reloading
        container.set(LAST_USE, PersistentDataType.LONG, currentTime);
        e.getItem().setItemMeta(meta);
        return true;
    }

    void weaponLaunchProjectile(PlayerRightClickEvent e, double[] MODAdder) {

//        calculateMODAdderFromMODs

//        0,//0:damage(Multiplier)
//                0,0,0,//multipliers for 1:slash, 2:impact, 3:puncture
//                0,0,0,0,//multipliers for 4:heat, 5:cold, 6:toxin, 7:electricity
//                0,0,0,0,0,0,//multipliers for 8:blast, 9:gas, 10:radiation, 11:viral, 12:magnetic, 13:corrosive ; may not be used
//                0,0,0,0,//multipliers for 14:criticalChance, 15:criticalDamage, 16:statusChance, 17:statusTime
//                0,0,0,0,0,0,0,//18:multiShot,19:punch(Meters),20:cooldown,21:ammoScale,22:reloadSpeed,23:explosionRadius,24:spread
//                0//25:keep it for st. like ConditionOverload;

        e.getPlayer().playNote(e.getPlayer().getLocation(), instrument, note);
        Snowball projectile = e.getPlayer().launchProjectile(Snowball.class);
        double tempSpread = spread * (1 + MODAdder[24]);
        if (tempSpread > 0) {
            projectile.setVelocity(
                    projectile.getVelocity()
                            .add(new Vector(generateDouble(tempSpread), generateDouble(tempSpread), generateDouble(tempSpread)))
                            .multiply(ammoSpeedMultiplier)
            );
        }
        if (offGravity) {
            projectile.setGravity(false);
        }

        double[] tempBasicDamage = basicDamage.clone();
        double damage = 0;
        for (double i : basicDamage) {
            damage += i;
        }
        tempBasicDamage[1] += MODAdder[1] * damage;
        tempBasicDamage[2] += MODAdder[2] * damage;
        tempBasicDamage[3] += MODAdder[3] * damage;
        tempBasicDamage[4] += MODAdder[4] * damage;
        tempBasicDamage[5] += MODAdder[5] * damage;
        tempBasicDamage[6] += MODAdder[6] * damage;
        tempBasicDamage[7] += MODAdder[7] * damage;
        tempBasicDamage[8] += MODAdder[8] * damage;
        tempBasicDamage[9] += MODAdder[9] * damage;
        tempBasicDamage[10] += MODAdder[10] * damage;
        tempBasicDamage[11] += MODAdder[11] * damage;
        tempBasicDamage[12] += MODAdder[12] * damage;
        tempBasicDamage[13] += MODAdder[13] * damage;
        projectile.setMetadata("sfe_isGunBullet", new FixedMetadataValue(SlimeFrameExtension.instance(), true));
        projectile.setMetadata("sfe_damage",
                new FixedMetadataValue(
                        SlimeFrameExtension.instance()
                        , Arrays.toString(tempBasicDamage)));
        projectile.setMetadata("sfe_ExtraCalculates",
                new FixedMetadataValue(
                        SlimeFrameExtension.instance()
                        , Arrays.toString(new double[]{
                        criticalMultipier * (1 + MODAdder[15])
                        , criticalChance * (1 + MODAdder[14])
                        , statusChance * (1 + MODAdder[16])
                        , statusTime * (1 + MODAdder[17])
                        , punchThrough + MODAdder[19]
                        , explodeLevel * (1 + MODAdder[23])
                })
                ));
        projectile.setMetadata("sfe_MODAdders",
                new FixedMetadataValue(
                        SlimeFrameExtension.instance()
                        , Arrays.toString(MODAdder)
                ));
        Scheduler.run(200, projectile::remove);
    }

    double generateDouble(double max) {
        double nextdb;
        while (true) {
            nextdb = random.nextDouble();
            if (nextdb < Math.abs(max)) {
                return nextdb * generateSign();
            }
        }
    }

    int generateSign() {
        if (random.nextBoolean()) {
            return -1;
        } else return 1;
    }

    void DecreaseAmmo(ItemStack itemStack) {
        DecreaseAmmo(1, itemStack);
    }

//    double CalculateDamage(){
//        double criticalPow = Math.floor(criticalChance);//actually it's an int
//        if (criticalChance - criticalPow >= random.nextDouble()){
//            criticalPow += 1;
//        }
//        return basicDamage[0] * Math.pow(1+criticalMultipier, criticalPow);
//    }

    void DecreaseAmmo(int ammo, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(Collections.singletonList(
                Integer.parseInt(itemMeta.getLore().get(0).split("/")[0]) - ammo + "/" + ammoScale
        ));
        item.setItemMeta(itemMeta);
    }
}
