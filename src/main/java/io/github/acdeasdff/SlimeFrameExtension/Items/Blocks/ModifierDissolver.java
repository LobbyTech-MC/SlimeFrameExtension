package io.github.acdeasdff.SlimeFrameExtension.Items.Blocks;

import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.DataTypeMethods;
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
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.voper.slimeframe.utils.ChatUtils;
import org.apache.commons.math3.util.Pair;
import org.bukkit.ChatColor;
//import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.ENDOS_OWNED;
import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.MODIFIER_ITEM;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Other.ModifierStatusRecorder.modifierRarityType;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.*;

public class ModifierDissolver extends SlimefunItem {

    private boolean calculateEndos = false;
    public ModifierDissolver(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        constructMenu(properties.getReplacedProperty("ModifierDissolver_Name"));
        addItemHandler(onPlace());
        addItemHandler(onBreak());
    }

    private static ItemStack README = new CustomItemStack(
            Material.CYAN_STAINED_GLASS_PANE,
            COLOR_MIKU + properties.getReplacedProperty("ModifierDissolver_Name"),
            COLOR_MIKU + properties.getReplacedProperty("ModifierDissolver_Lore1"),
            COLOR_MIKU + properties.getReplacedProperty("ModifierDissolver_Lore2")
    );
    private static Player p;
    private static int[] READMESlot = new int[]{0};

    //These three only useful for MODs with no level
    private static int[] DissolveCommonMODButton = new int[]{1};
    private static int[] DissolveUncommonMODButton = new int[]{2};
    private static int[] DissolveRareMODButton = new int[]{3};

    //This is useful for MODs in machine
    private static int[] DissolveButton = new int[]{4};

    private static int[] border = new int[]{53};
    private static int[] inputSlots = new int[]{
                            5, 6, 7, 8,
            9, 10,11,12,13,14,15,16,17,
            18,19,20,21,22,23,24,25,26,
            27,28,29,30,31,32,33,34,35,
            36,37,38,39,40,41,42,43,44,
            45,46,47,48,49,50,51,52,
//            54,55,56,57,58,59,60,61
    };

    public static int[] getInputSlots() {
        return inputSlots;
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
                menu.replaceExistingItem(DissolveCommonMODButton[0],new CustomItemStack(
                        Material.BROWN_STAINED_GLASS_PANE,
                        ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_1")
                        + CommonMODColor +  properties.getReplacedProperty("ModifierDissolver_Dissolve_Common_MODs")
                        + ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_2")
                ));

                menu.replaceExistingItem(DissolveUncommonMODButton[0],new CustomItemStack(
                        Material.GRAY_STAINED_GLASS_PANE,
                        ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_1")
                                + UncommonMODColor +  properties.getReplacedProperty("ModifierDissolver_Dissolve_Uncommon_MODs")
                                + ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_2")
                ));
                menu.replaceExistingItem(DissolveRareMODButton[0],new CustomItemStack(
                        Material.YELLOW_STAINED_GLASS_PANE,
                        ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_1")
                                + RareMODColor +  properties.getReplacedProperty("ModifierDissolver_Dissolve_Rare_MODs")
                                + ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_2")
                ));
                menu.replaceExistingItem(DissolveButton[0],new CustomItemStack(
                        Material.RED_STAINED_GLASS_PANE,
                        ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_1")
                                + ChatColor.RED +  properties.getReplacedProperty("ModifierDissolver_Dissolve_ALL_MODs")
                                + ChatColor.WHITE + properties.getReplacedProperty("ModifierDissolver_Dissolve_MODs_2")
                ));

                menu.addMenuClickHandler(DissolveCommonMODButton[0], new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        int endosOwned = PersistentDataAPI.getInt(player, ENDOS_OWNED, 0);
                        Pair<Integer, List<Integer>> DissolvePair = getDissolveMODsFromMachine(menu,false, false, "common");
                        int addedEndos = DissolvePair.getFirst();
                        PersistentDataAPI.setInt(player, ENDOS_OWNED
                                , endosOwned + addedEndos);
                        removeItemsInMenu(menu, DissolvePair.getSecond());
                        ChatUtils.sendMessage(player,
                                ChatColor.WHITE + player.getName()
                                        + properties.getReplacedProperty("Endo_have")
                                        + ChatColor.GREEN + endosOwned + "+"  + addedEndos
                                        + ChatColor.YELLOW + properties.getReplacedProperty("Endo"));
                        updateInfo(menu, 0);
                        return false;
                    }
                });
                menu.addMenuClickHandler(DissolveUncommonMODButton[0], new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        int endosOwned = PersistentDataAPI.getInt(player, ENDOS_OWNED, 0);
                        Pair<Integer, List<Integer>> DissolvePair = getDissolveMODsFromMachine(menu,false, false, "uncommon");
                        int addedEndos = DissolvePair.getFirst();
                        PersistentDataAPI.setInt(player, ENDOS_OWNED
                                , endosOwned + addedEndos);
                        removeItemsInMenu(menu, DissolvePair.getSecond());
                        ChatUtils.sendMessage(player,
                                ChatColor.WHITE + player.getName()
                                        + properties.getReplacedProperty("Endo_have")
                                        + ChatColor.GREEN + endosOwned + "+"  + addedEndos
                                        + ChatColor.YELLOW + properties.getReplacedProperty("Endo"));
                        updateInfo(menu, 0);
                        return false;
                    }
                });
                menu.addMenuClickHandler(DissolveRareMODButton[0], new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        int endosOwned = PersistentDataAPI.getInt(player, ENDOS_OWNED, 0);
                        Pair<Integer, List<Integer>> DissolvePair = getDissolveMODsFromMachine(menu,false, false, "rare");
                        int addedEndos = DissolvePair.getFirst();
                        PersistentDataAPI.setInt(player, ENDOS_OWNED
                                , endosOwned + addedEndos);
                        removeItemsInMenu(menu, DissolvePair.getSecond());
                        ChatUtils.sendMessage(player,
                                ChatColor.WHITE + player.getName()
                                        + properties.getReplacedProperty("Endo_have")
                                        + ChatColor.GREEN + endosOwned + "+"  + addedEndos
                                        + ChatColor.YELLOW + properties.getReplacedProperty("Endo"));
                        updateInfo(menu, 0);
                        return false;
                    }
                });
                menu.addMenuClickHandler(DissolveButton[0], new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        int endosOwned = PersistentDataAPI.getInt(player, ENDOS_OWNED, 0);
                        Pair<Integer, List<Integer>> DissolvePair = getDissolveMODsFromMachine(menu, true, true,"", getInputSlots());
                        int addedEndos = DissolvePair.getFirst();
                        PersistentDataAPI.setInt(player, ENDOS_OWNED
                                , endosOwned + addedEndos);
                        removeItemsInMenu(menu, DissolvePair.getSecond());
                        ChatUtils.sendMessage(player,
                                ChatColor.WHITE + player.getName()
                                        + properties.getReplacedProperty("Endo_have")
                                        + ChatColor.GREEN + endosOwned + "+"  + addedEndos
                                        + ChatColor.YELLOW + properties.getReplacedProperty("Endo"));
                        updateInfo(menu, 0);
                        return false;
                    }
                });
                menu.addMenuClickHandler(READMESlot[0], new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        calculateEndos = true;
                        return false;
                    }
                });
            }
            @Override
            public boolean canOpen(@NotNull Block block, @NotNull Player player) {
                return player.hasPermission("slimefun.inventory.bypass")
                        || Slimefun.getProtectionManager().hasPermission(player, block.getLocation(),
                        Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return getInputSlots();
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return getInputSlots();
            }
        };
    }

    protected void constructMenu(BlockMenuPreset preset) {
        ModifierTable.borders(preset, border);
        ModifierTable.borders(preset, READMESlot, README);
    }

    protected void constructMenu(BlockMenu preset) {
        ModifierTable.borders(preset, border);
        ModifierTable.borders(preset, READMESlot, README);
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            public void tick(Block b, SlimefunItem sf, Config data) {
                ModifierDissolver.this.tick(b);
            }

            @Override
            public boolean isSynchronized() {
                return false;
            }
        });
    }
    protected void tick(Block block) {
        if (calculateEndos){
            calculateEndos=false;
            BlockMenu menu = BlockStorage.getInventory(block);
            updateInfo(menu, getExpectedEndosFromMachine(menu, true, true, "", getInputSlots()));
        }
    }
    private void updateInfo(BlockMenu menu,int EndoWillGet){
        if (EndoWillGet == 0){
            ModifierTable.borders(menu, READMESlot, README);
        }else {
            ModifierTable.borders(menu,READMESlot
                    , new CustomItemStack(
                            Material.CYAN_STAINED_GLASS_PANE
                            , COLOR_MIKU + properties.getReplacedProperty("ModifierDissolver_Endo_Calculate")
                            , COLOR_MIKU + String.valueOf(EndoWillGet)
                    )
            );
        }
    }

    //getDissolveMODsFromMachine Methods
    public static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu, boolean ignoreRarity, String rarity, int[] inputSlots){
        return getDissolveMODsFromMachine(menu, false, ignoreRarity, rarity,inputSlots);
    }

    public static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu, int[] inputSlots){
        return getDissolveMODsFromMachine(menu, false, true, "",inputSlots);
    }

    public static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu, boolean ignoreLevel, int[] inputSlots){
        return getDissolveMODsFromMachine(menu, ignoreLevel, true, "",inputSlots);
    }

    private static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu, boolean ignoreRarity, String rarity){
        return getDissolveMODsFromMachine(menu, false, ignoreRarity, rarity);
    }

    private static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu){
        return getDissolveMODsFromMachine(menu, false, true, "");
    }

    private static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu, boolean ignoreLevel){
        return getDissolveMODsFromMachine(menu, ignoreLevel, true, "");
    }

    private static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu, boolean ignoreLevel, boolean ignoreRarity, String rarity){
        return getDissolveMODsFromMachine(menu, ignoreLevel, ignoreRarity, rarity, getInputSlots());
    }

    public static Pair<Integer, List<Integer>> getDissolveMODsFromMachine(BlockMenu menu, boolean ignoreLevel, boolean ignoreRarity, String rarity, int[] inputSlots){
        List<Integer> returnVar = new ArrayList<>();
        int endosCanEarn = 0;
        for (int i:inputSlots){
            ItemStack checkItemStack = menu.getItemInSlot(i);
            if (checkItemStack != null){
                ItemMeta checkItemMeta = checkItemStack.getItemMeta();
                int Amount = checkItemStack.getAmount();
                if(checkItemStack != null){
                    Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(checkItemMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                    if (optional2.isPresent()){
                        int checkLevel = optional2.get().getLevel();
                        if (!ignoreLevel && checkLevel > 0){
                            continue;
                        }
                        String checkRarity = modifierRarityType.get(optional2.get().getModifierType().split("_")[2]);
//                        logger.log(Level.WARNING,checkRarity);
                        if (!ignoreRarity && !rarity.equals(checkRarity)){
                            continue;
                        }
                        endosCanEarn += Amount*calculateEndosEarn(checkRarity, checkLevel);
                        returnVar.add(i);
                    }
                }
            }
        }
        return new Pair<>(endosCanEarn, returnVar);
    }

    public static int getExpectedEndosFromMachine(BlockMenu menu, boolean ignoreLevel, boolean ignoreRarity, String rarity, int[] inputSlots){
//        List<Integer> returnVar = new ArrayList<>();
        int endosCanEarn = 0;
        for (int i:inputSlots){
            ItemStack checkItemStack = menu.getItemInSlot(i);
            if (checkItemStack != null){
                ItemMeta checkItemMeta = checkItemStack.getItemMeta();
                int Amount = checkItemStack.getAmount();
                if(checkItemStack != null){
                    Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(checkItemMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                    if (optional2.isPresent()){
                        int checkLevel = optional2.get().getLevel();
                        if (!ignoreLevel && checkLevel > 0){
                            continue;
                        }
                        String checkRarity = modifierRarityType.get(optional2.get().getModifierType().split("_")[2]);
                        if (!ignoreRarity && !rarity.equals(checkRarity)){
                            continue;
                        }
                        endosCanEarn += Amount*calculateEndosEarn(checkRarity, checkLevel);
//                        returnVar.add(i);
                    }
                }
            }
        }
        return endosCanEarn;
    }

    //getDissolveMODsFromMachine Methods end
    public static int calculateEndosEarn(String rarity, int level){
        return switch (rarity){
            case "common" -> (int) Math.round((10./3.)*level + 5);
            case "uncommon" -> (int) Math.round(7.5*level + 10);
            case "rare" -> (int) Math.round(12.5*level + 15);
            case "legendary" -> (1+level)*20;
            default -> 0;
        };
    }

    public static void removeItemsInMenu(BlockMenu menu, Integer... slots){
        for (int i:slots){
            menu.replaceExistingItem(i,null);
        }
    }

    public static void removeItemsInMenu(BlockMenu menu, int[] slots){
        for (int i:slots){
            menu.replaceExistingItem(i,null);
        }
    }

    public static void removeItemsInMenu(BlockMenu menu, List<Integer> slots){
        for (int i:slots){
            menu.replaceExistingItem(i,null);
        }
    }
}
