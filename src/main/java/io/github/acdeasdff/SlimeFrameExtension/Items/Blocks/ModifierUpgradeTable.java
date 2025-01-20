package io.github.acdeasdff.SlimeFrameExtension.Items.Blocks;

import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.DataTypeMethods;
import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys;
import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.PersistentSFEModifierType;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.ModifierInstance;
import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.MODIFIER_ITEM;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractModifier.ModifierSetLevel;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Other.ModifierStatusRecorder.*;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.logger;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;

public class ModifierUpgradeTable extends SlimefunItem {

    private static final int[] borders = new int[]{
            0, 1, 2, 3, 5, 6, 7, 8,
            9, 10, 11, 12, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,

            39, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };
    private static final int[] statusSlot = new int[]{4};
    private static final int[] modifierSlot = new int[]{13};
    private static final int[] modLevelSlot = new int[]{
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36
    };
    private static final int[] modLevelSwitcher = new int[]{
            37, 38
    };
    private static final int[] confirmButton = new int[]{40};
    private static Player p;//player placed block

    public ModifierUpgradeTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        constructMenu(properties.getReplacedProperty("ModifierUpgradeTable_Name"));
        addItemHandler(onPlace());
        addItemHandler(onBreak());
    }

    public static int[] getInputSlots() {
        return modifierSlot;
    }

    public static int getInputSlot() {
        return modifierSlot[0];
    }

    public static int getMODEndoMultiplierFromRarity(ItemStack MOD) {
        Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(MOD.getItemMeta(), MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
        if (!optional2.isPresent()) {
            return -1;
        } else return getMODEndoMultiplierFromRarity(
                optional2.get().getModifierType().split("_")[2].toLowerCase()
        );
    }

    public static int getMODEndoMultiplierFromRarity(String MODName) {
        MODName = MODName.toLowerCase();
        return switch (modifierRarityType.get(MODName)) {
            case "common" -> 10;
            case "uncommon" -> 20;
            case "rare" -> 30;
            case "legendary" -> 40;
            default -> {
                logger.log(Level.WARNING, "No rarity" + modifierRarityType.get(MODName) + "for" + MODName);
                yield -1;
            }
        };
    }

    private BlockPlaceHandler onPlace() {
        return new BlockPlaceHandler(true) {

            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                p = e.getPlayer();
            }

            @Override
            public void onBlockPlacerPlace(@Nonnull BlockPlacerPlaceEvent e) {
            }
        };
    }

    private BlockBreakHandler onBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack i, @Nonnull List<ItemStack> list) {
                Block b = e.getBlock();
                BlockMenu inv = BlockStorage.getInventory(b);

                if (inv != null) {
                    inv.dropItems(b.getLocation(), getInputSlots());
                }
            }
        };
    }

    private void constructMenu(String displayName) {
        new BlockMenuPreset(getId(), displayName) {
            @Override
            public void init() {
                constructMenu(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                menu.addPlayerInventoryClickHandler(new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        if (clickAction.isShiftClicked()) {
                            return false;
                        }
                        return true;
                    }
                });
                menu.addMenuClickHandler(getInputSlot(), new AdvancedMenuClickHandler() {
                    @Override
                    public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        if (!inventoryClickEvent.isLeftClick()) {
                            return false;
                        }
                        ItemStack cursorItem = inventoryClickEvent.getCursor();
                        if (menu.getItemInSlot(i) != null && menu.getItemInSlot(i).getType() != Material.AIR) {
                            ModifierTable.DropItem(player, menu.getItemInSlot(i));
                            menu.replaceExistingItem(i, null);
                        }
                        if (cursorItem != null && cursorItem.getType() != Material.AIR) {
                            if (cursorItem.getAmount() != 1) {
                                updateMenu(menu, null);
                                return false;
                            } else {
                                updateMenu(menu, cursorItem);
                                return true;
                            }
                        } else {
                            updateMenu(menu, null);
                            return false;
                        }
                    }

                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        return false;
                    }
                });
            }

            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass")
                        || Slimefun.getProtectionManager().hasPermission(p, b.getLocation(),
                        Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return new int[0];
            }

        };
    }

    protected void constructMenu(BlockMenuPreset preset) {
        ModifierTable.borders(preset, borders);

        ModifierTable.borders(preset, statusSlot, Material.RED_STAINED_GLASS_PANE, ChatColor.RED + properties.getReplacedProperty("ModifierUpgradeTable_No_MOD"));
        ModifierTable.borders(preset, modLevelSlot, Material.BLACK_CONCRETE, ChatColor.GRAY + properties.getReplacedProperty("ModifierUpgradeTable_No_Level"));
        ModifierTable.borders(preset, confirmButton, Material.BLACK_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Confirm"));
        ModifierTable.borders(preset, modLevelSwitcher[0], Material.GRAY_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Level_Down"));
        ModifierTable.borders(preset, modLevelSwitcher[1], Material.GRAY_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Level_Up"));

    }

    protected void constructMenu(BlockMenu preset) {
        ModifierTable.borders(preset, borders);

        ModifierTable.borders(preset, statusSlot, Material.RED_STAINED_GLASS_PANE, ChatColor.RED + properties.getReplacedProperty("ModifierUpgradeTable_No_MOD"));
        ModifierTable.borders(preset, modLevelSlot, Material.BLACK_CONCRETE, ChatColor.GRAY + properties.getReplacedProperty("ModifierUpgradeTable_No_Level"));
        ModifierTable.borders(preset, confirmButton, Material.BLACK_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Confirm"));
        ModifierTable.borders(preset, modLevelSwitcher[0], Material.GRAY_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Level_Down"));
        ModifierTable.borders(preset, modLevelSwitcher[1], Material.GRAY_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Level_Up"));

    }

    private void updateMenu(BlockMenu menu, ItemStack MOD) {
        updateMenu(menu, MOD, true);
    }

    private void updateMenu(BlockMenu menu, ItemStack MOD, boolean updateLevelSlot) {
        if (MOD != null && MOD.getType() != Material.AIR) {
            Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(MOD.getItemMeta(), MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
            if (!optional2.isPresent()) {
                constructMenu(menu);
            } else {
                ModifierInstance modifierInstance = optional2.get();
                String[] modInfo = modifierInstance.getModifierType().split("_");
                final int[] modLevel = {modifierInstance.getLevel()};
//                logger.log(Level.WARNING, String.valueOf(modLevel[0]));
                final int[] addLevel = {0};
                String modName = modInfo[2];

                if (updateLevelSlot) {
                    for (int i = 0; i < modLevel[0]; i++) {
                        menu.replaceExistingItem(modLevelSlot[i], new CustomItemStack(Material.CYAN_CONCRETE, " "));
                        menu.addMenuClickHandler(modLevelSlot[i], (player, i1, itemStack, clickAction) -> false);
                    }
                    for (int i = modLevel[0]; i < modLevelSlot.length; i++) {
                        menu.replaceExistingItem(modLevelSlot[i], new CustomItemStack(Material.BLACK_CONCRETE, " "));
                        menu.addMenuClickHandler(modLevelSlot[i], (player, i1, itemStack, clickAction) -> false);
                    }
                } else {
                    for (int j : modLevelSlot) {
                        if (menu.getItemInSlot(j) != null
                                && menu.getItemInSlot(j).getType().equals(Material.BLUE_CONCRETE)) {
                            addLevel[0] += 1;
                        }
                    }
                }

                menu.replaceExistingItem(confirmButton[0], new CustomItemStack(Material.WHITE_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Confirm")));
                menu.replaceExistingItem(modLevelSwitcher[0], new CustomItemStack(Material.GRAY_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Level_Down")));
                menu.replaceExistingItem(modLevelSwitcher[1], new CustomItemStack(Material.GRAY_CONCRETE, ChatColor.WHITE + properties.getReplacedProperty("ModifierUpgradeTable_Switcher_Level_Up")));

                menu.addMenuClickHandler(modLevelSwitcher[0], new ChestMenu.MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i0, ItemStack itemStack, ClickAction clickAction) {
                        if (clickAction.isShiftClicked() || clickAction.isRightClicked()) {
                            return false;
                        } else {
                            int ADDLevel = addLevel[0];
                            if (ADDLevel <= 0) {
                                return false;
                            }
//                            else if (ADDLevel + modLevel[0] >= modifierMaxLevel.get(modName)){return false;}
                            else {
                                ADDLevel -= 1;
                                for (int i = modLevel[0]; i < modLevel[0] + ADDLevel; i++) {

                                    menu.replaceExistingItem(modLevelSlot[i], new CustomItemStack(Material.BLUE_CONCRETE, " "));
                                    menu.addMenuClickHandler(modLevelSlot[i], (player14, i15, itemStack14, clickAction14) -> false);
                                }
                                for (int i = modLevel[0] + ADDLevel; i < modLevelSlot.length; i++) {
                                    menu.replaceExistingItem(modLevelSlot[i], new CustomItemStack(Material.BLACK_CONCRETE, " "));
                                    menu.addMenuClickHandler(modLevelSlot[i], (player13, i14, itemStack13, clickAction13) -> false);
                                }
                            }
                            updateMenu(menu, menu.getItemInSlot(getInputSlot()), false);
                        }
                        return false;
                    }
                });

                menu.addMenuClickHandler(modLevelSwitcher[1], new ChestMenu.MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i0, ItemStack itemStack, ClickAction clickAction) {
                        int ADDLevel = addLevel[0];
                        if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                            if (ADDLevel < modifierMaxLevel.get(modName) - modLevel[0]) {
                                ADDLevel += 1;
                                for (int i = modLevel[0]; i < (modLevel[0] + ADDLevel); i++) {
//                                    logger.log(Level.WARNING, "A");
                                    menu.replaceExistingItem(modLevelSlot[i], new CustomItemStack(Material.BLUE_CONCRETE, " "));
                                    menu.addMenuClickHandler(modLevelSlot[i], (player14, i15, itemStack14, clickAction14) -> false);
                                }
                                for (int i = modLevel[0] + ADDLevel; i < modLevelSlot.length; i++) {
//                                    logger.log(Level.WARNING, "B");
                                    menu.replaceExistingItem(modLevelSlot[i], new CustomItemStack(Material.BLACK_CONCRETE, " "));
                                    menu.addMenuClickHandler(modLevelSlot[i], (player13, i14, itemStack13, clickAction13) -> false);
                                }
//                                logger.log(Level.WARNING, "C");
                                updateMenu(menu, menu.getItemInSlot(getInputSlot()), false);
                            }
                        }
                        return false;
                    }
                });

                menu.addMenuClickHandler(confirmButton[0], new ChestMenu.MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        if (addLevel[0] > 0) {
                            int endoCost = (2 << (modLevel[0] + addLevel[0])) - (2 << (modLevel[0]));
                            endoCost *= getMODEndoMultiplierFromRarity(menu.getItemInSlot(getInputSlot()));
                            if (endoCost > 0) {
                                int endos = PersistentDataAPI.getInt(player, Keys.ENDOS_OWNED);
                                if (endos >= endoCost) {
                                    PersistentDataAPI.setInt(player, Keys.ENDOS_OWNED, endos - endoCost);
                                    ModifierSetLevel(menu.getItemInSlot(getInputSlot()), modLevel[0] + addLevel[0], optional2.get().getModifierType(), 0);
                                    String[] lore = loreForMod(optional2.get().getModifierType().split("_")[2], modLevel[0] + addLevel[0]);
                                    ItemMeta itemMeta = menu.getItemInSlot(getInputSlot()).getItemMeta();
                                    itemMeta.setLore(Arrays.asList(lore));
                                    menu.getItemInSlot(getInputSlot()).setItemMeta(itemMeta);
                                    updateMenu(menu, menu.getItemInSlot(getInputSlot()), true);
                                } else {
                                    player.sendMessage(properties.getReplacedProperty("ModifierUpgradeTable_Not_Enough_Endo"));
                                }
                            } else {
                                logger.log(Level.WARNING, "MOD " + modName + " has negative endo cost");
                            }
                        }
                        return false;
                    }
                });

                menu.replaceExistingItem(statusSlot[0], new CustomItemStack(
                        Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN +
                        properties.getReplacedProperty("ModifierUpgradeTable_Endo_Cost"),
                        ChatColor.GREEN + String.valueOf(getMODEndoMultiplierFromRarity(MOD) * ((2 << (modLevel[0] + addLevel[0])) - (2 << (modLevel[0]))))
                ));
                menu.addMenuClickHandler(statusSlot[0], (player, i, itemStack, clickAction) -> false);
            }
        } else {
            constructMenu(menu);
        }
    }
}
