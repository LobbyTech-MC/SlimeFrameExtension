package io.github.acdeasdff.SlimeFrameExtension.Items.Other;

import io.github.acdeasdff.SlimeFrameExtension.Groups.Group;
import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractMobDropModifier;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.*;

public class ModifierStatusRecorder {
    public static Map<String, Map<String, Double>> modifierStatusMap = new HashMap<>();
    public static Map<String, Integer> modifierMaxLevel = new HashMap<>();
    public static Map<String, String> modifierType = new HashMap<>();
    public static Map<String, Integer> modifierBasicSize = new HashMap<>();
    public static Map<String, Double> modifierSizeAddPerLevel = new HashMap<>();
    public static Map<String, String> modifierRarityType = new HashMap<>();
    public static Map<String, String> modifierForEquipment = new HashMap<>();
    public static Map<String, String> modifierName = new HashMap<>();

    public static void setup(SlimeFrameExtension plugin) {
        modifierStatusMap = new HashMap<>();
        modifierMaxLevel = new HashMap<>();

        //register mod data(not item)
        {
            modifierStatusPutter(
                    "SplitChamber"
                    , 5,
                    4,
                    2
                    , List.of("multiShot")
                    , List.of(0.15),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "HighVoltage",
                    3,
                    4,
                    1,
                    List.of("electricityDamageMultiplier",
                            "statusChance")
                    , List.of(0.15,
                            0.15),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "RimeRounds",
                    3,
                    4,
                    1,
                    List.of("coldDamageMultiplier",
                            "statusChance"),
                    List.of(0.15,
                            0.15),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "ThermiteRounds",
                    3,
                    4,
                    1,
                    List.of("heatDamageMultiplier",
                            "statusChance"),
                    List.of(0.15,
                            0.15),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "MalignantForce"
                    , 3,
                    4,
                    1
                    , List.of("toxinDamageMultiplier",
                            "statusChance")
                    , List.of(0.15,
                            0.15),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "SpeedTrigger"
                    , 5,
                    4,
                    1
                    , List.of("cooldown")
                    , List.of(0.12),
                    "uncommon",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "Firestorm"
                    , 3,
                    6,
                    1
                    , List.of("explosionRadius")
                    , List.of(0.06),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PrimedFirestorm"
                    , 10,
                    6,
                    1
                    , List.of("explosionRadius")
                    , List.of(0.04),
                    "legendary",
                    "prime",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PointStrike"
                    , 5,
                    2,
                    7. / 5.
                    , List.of("criticalChance")
                    , List.of(0.25),
                    "common",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "VitalSense",
                    5,
                    2,
                    7. / 5.,
                    List.of("criticalDamage"),
                    List.of(0.2),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "ContinuousMisery",
                    3,
                    4,
                    1,
                    List.of("statusTimeMultiplier"),
                    List.of(0.25),
                    "common",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "Wildfire",
                    3,
                    6,
                    1,
                    List.of("heatDamageMultiplier",
                            "ammoScale"),
                    List.of(0.15,
                            0.05),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "HeavyCaliber",
                    10,
                    6,
                    1,
                    List.of("damage",
                            "spread"),
                    List.of(0.15,
                            0.05),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "Serration",
                    10,
                    4,
                    1,
                    List.of("damage"),
                    List.of(0.15),
                    "uncommon",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "Shred",
                    5,
                    6,
                    1,
                    List.of("cooldown",
                            "punch"),
                    List.of(0.05,
                            0.2),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "MetalAuger",
                    5,
                    6,
                    9. / 5.,
                    List.of(
                            "punch"),
                    List.of(
                            0.35),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PrimedShred",
                    10,
                    6,
                    1,
                    List.of("cooldown",
                            "punch"),
                    List.of(0.05,
                            0.2),
                    "legendary",
                    "prime",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "CryoRounds",
                    5,
                    6,
                    1,
                    List.of("coldDamageMultiplier"),
                    List.of(0.15),
                    "uncommon",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "InfectedClip",
                    5,
                    6,
                    1,
                    List.of("toxinDamageMultiplier"),
                    List.of(0.15),
                    "uncommon",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "Stormbringer",
                    5,
                    6,
                    1,
                    List.of("electricityDamageMultiplier"),
                    List.of(0.15),
                    "uncommon",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "Hellfire",
                    5,
                    6,
                    1,
                    List.of("heatDamageMultiplier"),
                    List.of(0.15),
                    "uncommon",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PrimedCryoRounds",
                    10,
                    6,
                    1,
                    List.of("coldDamageMultiplier"),
                    List.of(0.15),
                    "legendary",
                    "prime",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PrimedFastHands",
                    10,
                    2,
                    1,
                    List.of("reloadSpeed"),
                    List.of(0.05),
                    "legendary",
                    "prime",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "FastHands",
                    5,
                    2,
                    1,
                    List.of("reloadSpeed"),
                    List.of(0.05),
                    "common",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "MagazineWarp",
                    5,
                    4,
                    1,
                    List.of("ammoScale"),
                    List.of(0.05),
                    "common",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PrimedMagazineWarp",
                    10,
                    4,
                    1,
                    List.of("ammoScale"),
                    List.of(0.05),
                    "legendary",
                    "prime",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "HammerShot",
                    3,
                    6,
                    1,
                    List.of("criticalDamage",
                            "statusChance"),
                    List.of(0.15,
                            0.2),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "RifleAptitude",
                    5,
                    2,
                    7. / 5.,
                    List.of(
                            "statusChance"),
                    List.of(
                            0.15),
                    "uncommon",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "FangedFusillade",
                    5,
                    6,
                    1,
                    List.of(
                            "slashDamageMultiplier"),
                    List.of(
                            0.2),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "SawtoothClip",
                    5,
                    2,
                    7. / 5.,
                    List.of(
                            "slashDamageMultiplier"),
                    List.of(
                            0.15),
                    "common",
                    "normal",
                    "ranged",
                    plugin
            );


            modifierStatusPutter(
                    "CrashCourse",
                    5,
                    6,
                    1,
                    List.of(
                            "impactDamageMultiplier"),
                    List.of(
                            0.2),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "Rupture",
                    5,
                    2,
                    7. / 5.,
                    List.of(
                            "impactDamageMultiplier"),
                    List.of(
                            0.15),
                    "common",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PiercingCaliber",
                    5,
                    6,
                    1,
                    List.of(
                            "punctureDamageMultiplier"),
                    List.of(
                            0.2),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "PiercingHit",
                    5,
                    2,
                    7. / 5.,
                    List.of(
                            "punctureDamageMultiplier"),
                    List.of(
                            0.15),
                    "common",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "CriticalDelay",
                    5,
                    4,
                    1,
                    List.of("criticalChance",
                            "reloadSpeed"),
                    List.of(
                            (1. / 3.),
                            (-1. / 30.)
                    ),
                    "rare",
                    "normal",
                    "ranged",
                    plugin

            );

            modifierStatusPutter(
                    "VileAcceleration",
                    5,
                    4,
                    1,
                    List.of("cooldown",
                            "damage"),
                    List.of(
                            (0.15),
                            (-0.025)
                    ),
                    "rare",
                    "normal",
                    "ranged",
                    plugin
            );

            modifierStatusPutter(
                    "AmmoDrum",
                    5,
                    2,
                    1,
                    List.of(
                            "ammoScale"),
                    List.of(
                            0.15
                    ),
                    "common",
                    "exilus",
                    "ranged",
                    plugin
            );
        }

    }

    public static void modifierStatusPutter(String name, int maxLevel, int basicSize, double sizeAddPerLevel, List<String> status, List<Double> statusValue, String rareType, String modType, String ForEquipment, SlimeFrameExtension plugin) {
        try {
            if ((status.size() != statusValue.size()) || status.size() == 0) {
                logger.log(Level.WARNING, name + "registerStatusFailed");
                return;
            }
            Map<String, Double> Status = new HashMap<>();
            for (int i = 0; i < status.size(); i++) {
                Status.put(status.get(i), statusValue.get(i));
            }
            String ShowMODName = properties.getReplacedProperty("MOD_" + name);
//            logger.log(Level.WARNING,"loading "+ShowMODName);
            modifierName.put(name.toLowerCase(), properties.getReplacedProperty("MOD_" + name));
            name = name.toLowerCase();
            modifierRarityType.put(name, rareType);
            modifierType.put(name, modType);
            modifierSizeAddPerLevel.put(name, sizeAddPerLevel);
            modifierMaxLevel.put(name, maxLevel);
            modifierBasicSize.put(name, basicSize);
            modifierStatusMap.put(name, Status);
            modifierForEquipment.put(name, ForEquipment);

            String[] loreBuilder = new String[status.size() + 1];
            loreBuilder[0] = ChatColor.GRAY + properties.getReplacedProperty("MOD_Level") + "0/" + maxLevel
                    + " | " + properties.getReplacedProperty("MOD_Size") + modifierBasicSize.get(name);
            //build lore like:
            //level: 0/10
            //+ 5% criticalChance
            for (int i = 0; i < status.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ChatColor.GRAY);
                if (statusValue.get(i) > 0) {
                    stringBuilder.append("+ ");
                } else {
                    stringBuilder.append("- ");
                }
                if (!status.get(i).equalsIgnoreCase("punch")) {
                    stringBuilder.append(statusValue.get(i) * (1) * 100);
                    stringBuilder.append("%");
                } else {
                    stringBuilder.append(statusValue.get(i) * (1));
                }
                stringBuilder.append(" ");
                stringBuilder.append(properties.getReplacedProperty("MOD_Value_" + status.get(i)));
                loreBuilder[i + 1] = stringBuilder.toString();
            }

            StringBuilder registerMODType = new StringBuilder();
            if (modType.equals("normal") || modType.equals("exilus")) {
                registerMODType.append(modType);
                registerMODType.append("_");
                registerMODType.append(name.replace("primed", ""));
                registerMODType.append("_");
                registerMODType.append(name);
                registerMODType.append("_");
                registerMODType.append(ForEquipment);
                name = name.toUpperCase();
//                logger.log(Level.WARNING,"SFE_MOD_" + name);
                if (rareType.equals("common")) {
                    SlimefunItemStack newMod = new SlimefunItemStack(
                            "SFE_MOD_" + name,
                            Material.BROWN_DYE,
                            CommonMODColor + ShowMODName,
                            loreBuilder
                    );
                    new AbstractMobDropModifier(Group.MODIFIERS, newMod, registerMODType.toString())
                            .setMobChanceMap(Map.of(
                                    EntityType.ZOMBIE, 1,
                                    EntityType.ZOMBIE_VILLAGER, 1,
                                    EntityType.SKELETON, 1,
                                    EntityType.CREEPER, 1,
                                    EntityType.SPIDER, 1
                            )).register(plugin);
                    return;
                }
                if (rareType.equals("uncommon")) {
                    SlimefunItemStack newMod = new SlimefunItemStack(
                            ("SFE_MOD_" + name),
                            Material.GRAY_DYE,
                            UncommonMODColor + ShowMODName,
                            loreBuilder
                    );
                    new AbstractMobDropModifier(Group.MODIFIERS, newMod, registerMODType.toString())
                            .setMobChanceMap(Map.of(
                                    EntityType.ENDERMAN, 1,
                                    EntityType.ENDERMITE, 1,
                                    EntityType.SKELETON_HORSE, 1,
                                    EntityType.PIGLIN, 1,
                                    EntityType.PIGLIN_BRUTE, 1,
                                    EntityType.ZOMBIFIED_PIGLIN, 1
                            )).register(plugin);
                    return;
                }
                if (rareType.equals("rare")) {
                    SlimefunItemStack newMod = new SlimefunItemStack(
                            ("SFE_MOD_" + name),
                            Material.YELLOW_DYE,
                            RareMODColor + ShowMODName,
                            loreBuilder
                    );
                    new AbstractMobDropModifier(Group.MODIFIERS, newMod, registerMODType.toString())
                            .setMobChanceMap(Map.of(
                                    EntityType.ENDER_DRAGON, 10,
                                    EntityType.WITHER, 1,
                                    EntityType.WITHER_SKELETON, 1,
                                    EntityType.GUARDIAN, 1,
                                    EntityType.ELDER_GUARDIAN, 20
                            )).register(plugin);
                    return;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, name);
            logger.log(Level.SEVERE, String.valueOf(maxLevel));
            logger.log(Level.SEVERE, String.valueOf(basicSize));
            logger.log(Level.SEVERE, String.valueOf(sizeAddPerLevel));
            logger.log(Level.SEVERE, String.valueOf(status));
            logger.log(Level.SEVERE, String.valueOf(statusValue));
            logger.log(Level.SEVERE, String.valueOf(rareType));
            logger.log(Level.SEVERE, String.valueOf(modType));
            logger.log(Level.SEVERE, String.valueOf(ForEquipment));
            e.printStackTrace();
        }
    }

    public static ChatColor getColorFromType(String rareType, String modType) {
        if (modType.equals("riven")) {
            return RivenMODColor;
        }

        if (modType.equals("strange")) {
            return StrangeMODColor;
        }

        if (modType.equals("normal") || modType.equals("aura") || modType.equals("stance") || modType.equals("exilus")) {
            return switch (rareType) {
                case "common" -> CommonMODColor;
                case "uncommon" -> UncommonMODColor;
                case "rare" -> RareMODColor;
                case "prime" -> PrimedMODColor;
                default -> ChatColor.BLACK;
            };
        }

        return ChatColor.BLACK;
    }

    public static Material getMODMaterialFromType(String rareType, String modType) {
        if (modType.equals("riven")) {
            return Material.PURPLE_DYE;
        }

        if (modType.equals("strange")) {
            return Material.BLACK_DYE;
        }

        if (modType.equals("normal") || modType.equals("aura") || modType.equals("stance") || modType.equals("exilus")) {
            return switch (rareType) {
                case "common" -> Material.BROWN_DYE;
                case "uncommon" -> Material.GRAY_DYE;
                case "rare" -> Material.YELLOW_DYE;
                case "prime" -> Material.WHITE_DYE;
                default -> Material.BARRIER;
            };
        }

        return Material.BARRIER;
    }


    public static String[] loreForMod(String modName, int level) {
//        logger.log(Level.WARNING, modName+level);
        int maxLevel = modifierMaxLevel.get(modName);
        Map<String, Double> StatusMap = modifierStatusMap.get(modName);

        List<String> status = new ArrayList<>(StatusMap.keySet());
        List<Double> statusValue = new ArrayList<>();
        for (String s : status) {
            statusValue.add(StatusMap.get(s));
        }

        String[] loreBuilder = new String[status.size() + 1];
        loreBuilder[0] = ChatColor.GRAY + properties.getReplacedProperty("MOD_Level") + level + "/" + maxLevel
                + " | " + properties.getReplacedProperty("MOD_Size") + ((int) (Math.round((level) * modifierSizeAddPerLevel.get(modName))) + modifierBasicSize.get(modName));
        for (int i = 0; i < status.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ChatColor.GRAY);
            if (statusValue.get(i) > 0) {
                stringBuilder.append("+ ");
            } else {
                stringBuilder.append("- ");
            }
            if (!status.get(i).equalsIgnoreCase("punch")) {
                stringBuilder.append(statusValue.get(i) * (level + 1) * 100);
                stringBuilder.append("%");
            } else {
                stringBuilder.append(statusValue.get(i) * (level + 1));
            }
            stringBuilder.append(" ");
            stringBuilder.append(properties.getReplacedProperty("MOD_Value_" + status.get(i)));
            loreBuilder[i + 1] = stringBuilder.toString();
        }
        return loreBuilder;
    }
}