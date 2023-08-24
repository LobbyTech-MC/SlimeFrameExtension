package io.github.acdeasdff.SlimeFrameExtension.Items.Blocks;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.*;
import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractModifiableItem;
import io.github.acdeasdff.SlimeFrameExtension.Items.Abstracts.AbstractRangedWeapon;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.EquipmentInstance;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.ModifierInstance;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.PotatoableInstance;
import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;

import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.*;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Other.ModifierStatusRecorder.modifierBasicSize;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Other.ModifierStatusRecorder.modifierSizeAddPerLevel;
import static io.github.acdeasdff.SlimeFrameExtension.Items.registerOthers.*;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.logger;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;

public class ModifierTable extends SlimefunItem implements EnergyNetComponent {
    private static boolean needUpdate = true;
    public List<Player> playerOpeningMenu = new ArrayList<>();

    private static final int[] potatoSlot = new int[]{8};
    private static Player p;//player placed block
    private static final int[] statusSlot = new int[]{4};
    private static final int[] border = new int[]{
            0, 1,    3,    5,    7, 8,
            9, 10,   12,   14,   16,17,
            18,19,               25,26,
            27,28,               34,35,
            36,37,               43,44,
            45,46,               52,53
    };

    private static final int[] ExilusSlot = new int[]{
            15
    };

    private static final int[] ExilusSlotPolarity = new int[]{
            6
    };
    private static final int[] AuraOrStanceSlot = new int[]{
            11
    };
    private static final int[] AuraOrStanceSlotPolarity = new int[]{
            2
    };
    private static final int[] EquipmentSlot = new int[]{13};

    private static final int[] MODSlots = new int[]{

            30,31,32,33,
            39,40,41,42

    };

    private static final int[] PolaritySlots = new int[]{
            21,22,23,24,
                        //MODSLOTS
                        //MODSLOTS
            48,49,50,51
    };

    private static final int[] ExtraMODSlots = new int[]{

            29,
            38

    };

    private static final int[] ExtraPolaritySlots = new int[]{
            20,


            47
    };

    private static final int[] ExilusAndMODSlots = new int[]{
                     15,

            30,31,32,33,
            39,40,41,42
    };

    private static final int[] LargeMODSlots = new int[]{
            29,30,31,32,33,
            38,39,40,41,42
    };

    private static final int[] MeleeMODSlots = new int[]{
            11,

            30,31,32,33,
            39,40,41,42
    };

    private static final int[] FrameMODSlots = new int[]{
            11,      15,

            30,31,32,33,
            39,40,41,42
    };
    public int getPolaritySlotFromMODSlot(int MODSlot){
        if (MODSlot >= 36) {return MODSlot+9;} else {return MODSlot-9;}
    }

    public int getMODSlotFromPolaritySlot(int PolaritySlot){
        if (PolaritySlot <= 44) {return PolaritySlot+9;} else {return PolaritySlot-9;}
    }

    public int[] getPolaritySlotFromMODSlot(int[] MODSlots){
        int[] result = new int[MODSlots.length];
        for (int i=0;i<MODSlots.length;i++){
            result[i] = getPolaritySlotFromMODSlot(MODSlots[i]);
        }
        return result;
    }

    public int[] getMODSlotFromPolaritySlot(int[] PolaritySlots){
        int[] result = new int[PolaritySlots.length];
        for (int i=0;i<PolaritySlots.length;i++){
            result[i] = getMODSlotFromPolaritySlot(PolaritySlots[i]);
        }
        return result;
    }
    public ModifierTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        constructMenu(properties.getReplacedProperty("ModifierTable_DisplayName"));
        addItemHandler(onPlace());
        addItemHandler(onBreak());
    }

    private BlockBreakHandler onBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack i, @Nonnull List<ItemStack> list) {
                Block b = e.getBlock();
                BlockMenu inv = BlockStorage.getInventory(b);

                if (inv != null) {
//                    inv.dropItems(b.getLocation(), getInputSlots());
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.YELLOW + properties.getReplacedProperty("ModifierTable_Take_Equipment_First"));
                }
            }
            @Override
            public void onAndroidBreak(AndroidMineEvent e) {
                e.setCancelled(true);
            }
        };
    }

    public int[] getInputSlots(){return EquipmentSlot;}
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

    private void constructMenu(String displayName){
        new BlockMenuPreset(getId(), displayName) {

            @Override
            public void init() {
                constructMenu(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
//                CheckInputSlot(menu);
                menu.addMenuOpeningHandler(new MenuOpeningHandler() {
                    @Override
                    public void onOpen(Player player) {
                        if (!playerOpeningMenu.contains(player)){
                            playerOpeningMenu.add(player);
                        }
                    }
                });

                menu.addMenuCloseHandler(new MenuCloseHandler() {
                    @Override
                    public void onClose(Player player) {
                        playerOpeningMenu.remove(player);
                    }
                });
                menu.addPlayerInventoryClickHandler(new AdvancedMenuClickHandler() {
                    @Override
                    public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        if (!inventoryClickEvent.isLeftClick()){return false;}
                        else {
                            if (menu.getItemInSlot(i) != null && menu.getItemInSlot(i).getType() != Material.AIR){
                                menu.consumeItem(i, 1);
                                inventoryClickEvent.getCursor().setType(menu.getItemInSlot(i).getType());
                                inventoryClickEvent.getCursor().setAmount(1);
                                inventoryClickEvent.getCursor().setItemMeta(menu.getItemInSlot(i).getItemMeta());
                            }
                            return false;
                        }
                    }//but doesn't work

                    @Override
                    public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                        if (clickAction.isShiftClicked() || clickAction.isRightClicked()){return false;}
                        if (itemStack!=null && itemStack.getType() != Material.AIR)
                        {
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            if (itemMeta != null)
                            {
                                Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(itemMeta, MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
                                if (optional.isPresent())
                                {
                                    if (menu.getItemInSlot(getInputSlots()[0]) != null)
                                    {
                                        UpdateMODSlots(menu, menu.getItemInSlot(getInputSlots()[0]));
                                        SaveMODSlots(menu
                                                ,getTargetSlots(optional.get())
                                                ,optional.get().getItemType());
                                        Map<Integer, ItemStack> remaining = player.getInventory().addItem(menu.getItemInSlot(getInputSlots()[0]));
                                        for (ItemStack stack : remaining.values()) {
                                            player.getWorld().dropItemNaturally(player.getLocation(), stack);
                                        }
                                    }
                                    UpdateMODSlots(menu,itemStack);
                                    UpdatePotatoSlot(menu, itemStack);
                                    needUpdate=true;
                                    menu.replaceExistingItem(getInputSlots()[0],itemStack.clone());
                                    player.getInventory().setItem(i,null);
                                    return false;
                                }
                                else
                                {
                                    Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(itemMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                                    if (optional2.isPresent()){
                                        return itemStack.getAmount() == 1;
                                    }else {
                                        return SlimefunUtils.isItemSimilar(BLUE_POTATO, itemStack, false, false, false, false)
                                                || SlimefunUtils.isItemSimilar(GOLDEN_POTATO, itemStack, false, false, false, false)
                                                || SlimefunUtils.isItemSimilar(FORMA, itemStack, false, false, false, false);
                                    }
                                }
                            }else {return false;}
                        }else {return true;}
                    }
                });
                menu.addMenuClickHandler(getInputSlots()[0],
                        new AdvancedMenuClickHandler() {
                            @Override
                            public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                                if (clickAction.isShiftClicked()){return false;}
                                ItemStack cursorItem = inventoryClickEvent.getCursor();
                                itemStack = menu.getItemInSlot(getInputSlots()[0]);
                                if (cursorItem != null && cursorItem.getType() != Material.AIR){
                                    if (itemStack != null && itemStack.getType() != Material.AIR){
                                        return false;
                                    }
                                }else {
                                    if (itemStack != null && itemStack.getType() != Material.AIR){
                                    ItemMeta itemStackMeta = itemStack.getItemMeta();
                                    if (itemStackMeta != null){
                                        Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(itemStackMeta, MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
                                        optional.ifPresent
                                                (equipmentInstance ->
                                                        SaveMODSlots(
                                                                menu,
                                                                getTargetSlots(equipmentInstance),
                                                                equipmentInstance.getItemType()
                                                        )
                                                );//i know U can now save many same mods in one gun,but i'll make only 1 work
                                    }
                                    UpdateMODSlots(menu, itemStack);
                                    Map<Integer, ItemStack> remaining = player.getInventory().addItem(itemStack);
                                    for (ItemStack stack : remaining.values()) {
                                        player.getWorld().dropItemNaturally(player.getLocation(), stack);
                                    }

                                    menu.replaceExistingItem(getInputSlots()[0], null);
                                    player.updateInventory();
                                    needUpdate = true;
                                    UpdateMODSlots(menu, getInputSlots()[0]);
                                    UpdatePotatoSlot(menu, menu.getItemInSlot(getInputSlots()[0]));
                                    return false;
                                    }
                                }
                                needUpdate = true;
//                                InputSlotOnClick(menu, player, i, itemStack, clickAction, inventoryClickEvent, MODSlots);
                                ItemStack input = inventoryClickEvent.getCursor();
                                UpdateMODSlots(menu, input);
                                UpdatePotatoSlot(menu, cursorItem);
//                                newInstance(menu, b);
//                                player.updateInventory();
                                return true;
                            }

                            @Override
                            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                                return false;
                            }
                        }
                );
//                UpdateMODSlots(menu, getInputSlots()[0]);

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
        borders(preset, border);

        borders(preset, MODSlots, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));
        borders(preset, ExtraMODSlots, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));
        borders(preset, AuraOrStanceSlot, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));
        borders(preset, ExilusSlot, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));

        borders(preset, PolaritySlots, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));
        borders(preset, AuraOrStanceSlotPolarity, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));
        borders(preset, ExilusSlotPolarity, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));
        borders(preset, ExtraPolaritySlots, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));

        borders(preset, statusSlot, Material.RED_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_NOEquipment"));
    }

    protected void constructMenu(BlockMenu preset) {
//        borders(preset, border);

        borders(preset, MODSlots, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));
        borders(preset, ExtraMODSlots, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));
        borders(preset, AuraOrStanceSlot, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));
        borders(preset, ExilusSlot, Material.BARRIER, properties.getReplacedProperty("ModifierTable_NoSlot"));

        borders(preset, PolaritySlots, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));
        borders(preset, AuraOrStanceSlotPolarity, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));
        borders(preset, ExilusSlotPolarity, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));
        borders(preset, ExtraPolaritySlots, Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity"));

        borders(preset, statusSlot, Material.RED_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_NOEquipment"));
    }

    static void borders(BlockMenuPreset preset, int border, ItemStack itemStack) {
        preset.addItem(border, itemStack,
                (p, slot, item, action) -> false);
    }
    static void borders(BlockMenuPreset preset, int[] border, ItemStack itemStack) {
        for (int i : border) {
            preset.addItem(i, itemStack,
                    (p, slot, item, action) -> false);
        }

    }
    static void borders(BlockMenuPreset preset, int[] border) {
        for (int i : border) {
            preset.addItem(i, new CustomItemStack(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " "),
                    (p, slot, item, action) -> false);
        }

    }

    static void borders(BlockMenuPreset preset, int[] border, Material material, String name) {
        for (int i : border) {
//            preset.addItem(i, new CustomItemStack(new ItemStack(material), name),
//                    (p, slot, item, action) -> false);
            borders(preset, i, material, name);
        }

    }

    static void borders(BlockMenuPreset preset, int border, Material material, String name) {
        preset.addItem(border, new CustomItemStack(new ItemStack(material), name),
                (p, slot, item, action) -> false);
    }
    static void borders(BlockMenu preset, int border){
        borders(preset, new int[]{border});
    }
    static void borders(BlockMenu preset, int border, Material material, String name){
        borders(preset, new int[]{border}, material, name);
    }

    static void borders(BlockMenu preset, int[] border) {
        for (int i : border) {
            preset.replaceExistingItem(i, new CustomItemStack(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), " "));
            preset.addMenuClickHandler(i,(p, slot, item, action) -> false);
        }

    }

    static void borders(BlockMenu preset, int[] border, Material material, String name) {
        for (int i : border) {
            preset.replaceExistingItem(i, new CustomItemStack(new ItemStack(material), name));
            preset.addMenuClickHandler(i,(p, slot, item, action) -> false);
        }

    }

    static void borders(BlockMenu preset, int border, ItemStack itemStack){
        borders(preset, new int[]{border}, itemStack);
    }

    static void borders(BlockMenu preset, int[] border, ItemStack itemStack) {
        for (int i : border) {
            preset.replaceExistingItem(i, itemStack);
            preset.addMenuClickHandler(i,(p, slot, item, action) -> false);
        }

    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            public void tick(Block b, SlimefunItem sf, Config data) {
                ModifierTable.this.tick(b);
            }

            @Override
            public boolean isSynchronized() {
                return false;
            }
        });
    }

    protected void tick(Block block) {
        BlockMenu menu = BlockStorage.getInventory(block);
        ItemStack MODStatus = menu.getItemInSlot(MODSlots[0]);
        if (needUpdate){
            needUpdate = false;
            if (menu.getItemInSlot(getInputSlots()[0]) != null){
                ItemMeta itemMeta = menu.getItemInSlot(getInputSlots()[0]).getItemMeta();
                if (itemMeta != null) {
                    Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(itemMeta, MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
                    if (optional.isPresent()) {
                        borders(menu, statusSlot, Material.GREEN_STAINED_GLASS_PANE, " ");
                    } else {
                        borders(menu, statusSlot, Material.RED_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_NOEquipment"));
                        DropItem(block.getWorld(), block.getLocation(),menu.getItemInSlot(getInputSlots()[0]));
                        menu.replaceExistingItem(getInputSlots()[0],null);
                    }
                }
            }
            NoticeItemInput(menu, menu.getItemInSlot(getInputSlots()[0]));
        }
//        if (menu.getItemInSlot(getInputSlots()[0])!=null){
//            if (menu.getItemInSlot(statusSlot[0]) != null && menu.getItemInSlot(statusSlot[0]).getType().equals(Material.RED_STAINED_GLASS)) {
//                ItemMeta itemMeta = menu.getItemInSlot(getInputSlots()[0]).getItemMeta();
//                if (itemMeta != null) {
//                    Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(itemMeta, MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
//                    if (optional.isPresent()) {
//                        borders(menu, statusSlot, Material.GREEN_STAINED_GLASS_PANE, " ");
//                    } else {
//                        borders(menu, statusSlot, Material.RED_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_NOEquipment"));
//                        DropItem(block.getWorld(), block.getLocation(),menu.getItemInSlot(getInputSlots()[0]));
//                        menu.replaceExistingItem(getInputSlots()[0],null);
//                    }
//                    NoticeItemInput(menu, menu.getItemInSlot(getInputSlots()[0]));
//                }else{
//                    DropItem(block.getWorld(), block.getLocation(),menu.getItemInSlot(getInputSlots()[0]));
//                    menu.replaceExistingItem(getInputSlots()[0],null);
//                    NoticeItemInput(menu, menu.getItemInSlot(getInputSlots()[0]));
//                }
//            }
//        }
//        if (MODstatus != null){
//            if ((MODstatus.getType() != Material.BARRIER)) {
//                NoticeItemInput(menu, menu.getItemInSlot(getInputSlots()[0]));
//                borders(menu, statusSlot, Material.GREEN_STAINED_GLASS_PANE, " ");
//            }else {
//                borders(menu, statusSlot, Material.RED_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_NOEquipment"));
//            }
//        }else {
//            if (menu.getItemInSlot(statusSlot[0]) != null && menu.getItemInSlot(statusSlot[0]).getType() == Material.RED_STAINED_GLASS_PANE){
//                NoticeItemInput(menu, menu.getItemInSlot(getInputSlots()[0]));
//                borders(menu, statusSlot, Material.GREEN_STAINED_GLASS_PANE, " ");
//            }
//        }
//        timer++;
        //update
        return;
    }



    public void CheckInputSlot(BlockMenu menu){
        ItemStack input = menu.getItemInSlot(getInputSlots()[0]);
        UpdateMODSlots(menu, input);
    }

    public void UpdateMODSlots(BlockMenu menu, int input){
        UpdateMODSlots(menu, menu.getItemInSlot(input));
    }
    public void UpdateMODSlots(BlockMenu menu, ItemStack input){

        if (input != null && input.getType() != Material.AIR){
            if (input.getItemMeta() != null)
            {
                ItemMeta itemMeta = input.getItemMeta();
                Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(itemMeta, MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);

                if (optional.isPresent()) {
                    EquipmentInstance equipmentInstance = optional.get();
                    int modslotLength = equipmentInstance.getModifiers().length;

                    if (modslotLength == 8){
                        InputSlotOnClickPart2(menu, MODSlots, equipmentInstance);
                    }
                    else if(modslotLength == 9){
                        if (equipmentInstance.getItemType().equals("ranged")){
                            InputSlotOnClickPart2(menu, ExilusAndMODSlots, equipmentInstance);
                        }else {
                            InputSlotOnClickPart2(menu, AuraOrStanceSlot, equipmentInstance);
                        }
                    }
                    else if(modslotLength == 10){
                        if (equipmentInstance.getItemType().equals("archwing")
                                || equipmentInstance.getItemType().equals("warframe")//may not exists
                        ){
                            InputSlotOnClickPart2(menu, FrameMODSlots, equipmentInstance);
                        }
                        else {
                            InputSlotOnClickPart2(menu, LargeMODSlots, equipmentInstance);
                        }
                    }
//                            for (int i : MODSlots) {
//                                menu.replaceExistingItem(i, null);
//                                menu.addMenuClickHandler(i, null);
//                            }
                }else {constructMenu(menu);}

            }else {
                constructMenu(menu);
            }
        }else {
            input = menu.getItemInSlot(getInputSlots()[0]);
            if (input != null && input.getItemMeta() != null){
                ItemMeta itemMeta = input.getItemMeta();
                Optional<EquipmentInstance> optional = DataTypeMethods.getOptionalCustom(itemMeta, MODIFIABLE_INSTANCE, PersistentSFEEquipmentType.TYPE);
                if (optional.isPresent()) {
                    EquipmentInstance equipmentInstance = optional.get();
                    int modslotLength = equipmentInstance.getModifiers().length;

                    if (modslotLength == 8){
                        InputSlotOnClickPart2(menu, MODSlots, equipmentInstance);
                    }else if(modslotLength == 9){
                        if (equipmentInstance.getItemType().equals("ranged")){
                            InputSlotOnClickPart2(menu, ExilusAndMODSlots, equipmentInstance);
                        }else {
                            InputSlotOnClickPart2(menu, AuraOrStanceSlot, equipmentInstance);
                        }
                    }else if(modslotLength == 10){
                        if (equipmentInstance.getItemType().equals("archwing")
                                || equipmentInstance.getItemType().equals("warframe")//may not exists
                        ){
                            InputSlotOnClickPart2(menu, FrameMODSlots, equipmentInstance);
                        }
                        else {
                            InputSlotOnClickPart2(menu, LargeMODSlots, equipmentInstance);
                        }
                    }

                }else {constructMenu(menu);}
            }
            constructMenu(menu);
        }

    }
    public void InputSlotOnClick(BlockMenu menu,Player player, int uselessInt, ItemStack itemStack, ClickAction clickAction, InventoryClickEvent inventoryClickEvent, int[] targetSlots) {
//                        newInstance(menu, b);
//                        if (clickAction.isRightClicked() || clickAction.isShiftClicked()){return false;}
//                        ItemStack input = menu.getItemInSlot(getInputSlots()[0]);
        ItemStack input = inventoryClickEvent.getCursor();
        UpdateMODSlots(menu, input);
//        UpdatePotatoSlot(menu, input);

    }

    public void NoticeItemInput(BlockMenu blockMenu, ItemStack input){
//        UpdateMODSlots(blockMenu, input);
        for (Player player:playerOpeningMenu){
            player.updateInventory();
        }
    }


    public void InputSlotOnClickPart2(BlockMenu menu,Player player, int uselessInt, ItemStack itemStack, ClickAction clickAction, InventoryClickEvent inventoryClickEvent, int[] targetSlots, EquipmentInstance equipmentInstance){
        InputSlotOnClickPart2(menu, targetSlots, equipmentInstance);
    }
    public void InputSlotOnClickPart2(BlockMenu menu, int[] targetSlots, EquipmentInstance equipmentInstance) {
        for (int i=0; i < targetSlots.length; i++){
        if (equipmentInstance.getModifiers()[i] != null){
            equipmentInstance.getModifiers()[i].setItemMeta(equipmentInstance.getModifierMetas()[i]);
            menu.replaceExistingItem(targetSlots[i], equipmentInstance.getModifiers()[i]);
        }else{
            menu.replaceExistingItem(targetSlots[i], null);}
            menu.addMenuClickHandler(targetSlots[i], new ChestMenu.AdvancedMenuClickHandler() {
            @Override
            public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack2, ClickAction clickAction) {
                ItemStack itemStack = inventoryClickEvent.getCursor();
//                logger.log(Level.WARNING, inventoryClickEvent.getAction().toString());
//                logger.log(Level.WARNING, inventoryClickEvent.getClick().toString());
                if(!inventoryClickEvent.isLeftClick()){return false;}
                if ((itemStack != null && itemStack.getType() != Material.AIR)){
                    if (itemStack.getAmount() != 1){return false;}
                    ItemMeta itemMeta1 = itemStack.getItemMeta();
                    if (itemMeta1 == null){return false;}
                    Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(itemMeta1, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                    if (optional2.isEmpty()){return false;}
                    else {
                        ModifierInstance modInstance = optional2.get();//
                        String[] modifierType = modInstance.getModifierType().split("_");

                        //check the same mod and check size
                        {
                            if ((countMODSize(menu, targetSlots, i, itemStack) >= getInputEquipmentSize(menu, menu.getItemInSlot(getInputSlots()[0])))) {
                                player.sendMessage(properties.getReplacedProperty("ModifierTable_Out_Of_Size"));
                                return false;
                            }

                            if (!(modifierType[0].equals("aura")
                                    || modifierType[0].equals("stance"))
                                    && i == AuraOrStanceSlot[0]) {
                                player.sendMessage(properties.getReplacedProperty("ModifierTable_Slot_Error_Aura_Or_Stance"));
                                return false;
                            }

                            if (equipmentInstance.getItemType().equals("melee")
                                    && modifierType[0].equals("aura")) {
                                player.sendMessage(properties.getReplacedProperty("ModifierTable_Slot_Error_Aura_Not_Stance"));
                                return false;
                            }

                            if (!equipmentInstance.getItemType().equals("melee")
                                    && modifierType[0].equals("stance")) {
                                player.sendMessage(properties.getReplacedProperty("ModifierTable_Slot_Error_Stance_Not_Aura"));
                                return false;
                            }

                            if (i == ExilusSlot[0]
                                    && !modifierType[0].equals("exilus")) {
                                player.sendMessage(properties.getReplacedProperty("ModifierTable_Slot_Error_Exilus_Only"));
                                return false;
                            }

                            //maybe one day i'll add riven mods with Material.PURPLE_DYE
                            if (modifierType[0].equals("riven")) {
                                for (int i1 : targetSlots) {
                                    if (menu.getItemInSlot(i1) != null) {
                                        if (menu.getItemInSlot(i1).getType() == Material.PURPLE_DYE && i1 == i) {
                                            player.sendMessage(properties.getReplacedProperty("ModifierTable_Slot_Error_One_Riven_Same_Time"));
                                            return false;
                                        }
                                    }
                                }
                            }

                            if (!modifierType[3].equals(equipmentInstance.getItemType())){
                                player.sendMessage(properties.getReplacedProperty("ModifierTable_Slot_Error_MOD_For_Type"));
                                return false;
                            }
                        }
                        //still checking
                        for (int i1:targetSlots){
                            ItemStack itemStack1 = menu.getItemInSlot(i1);
                            if (itemStack1 != null && itemStack1.getType() != Material.AIR){
                                ItemMeta itemMeta2 = itemStack1.getItemMeta();
                                if (itemMeta2 == null){continue;}
                                Optional<ModifierInstance> optional3 = DataTypeMethods.getOptionalCustom(itemMeta2, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);

                                if (optional3.isPresent()){
                                    if (optional3.get().getModifierType().split("_")[1].equals(modifierType[1])){
                                        if (i != i1){
                                            player.sendMessage(properties.getReplacedProperty("ModifierTable_Slot_Error_No_Same_Mod"));
                                            player.sendMessage(itemMeta2.getDisplayName());
                                            player.sendMessage(itemStack.getItemMeta().getDisplayName());
                                            return false;
                                        }else {break;}
                                    }
                                }
                            }
                        }

                        ItemStack[] itemStack1 = new ItemStack[targetSlots.length];
                        List<String> modList = new ArrayList<>();
                        //dropping not excepted items
                        for (int ite=0;ite<targetSlots.length;ite++){
                            if (i != targetSlots[ite]){
                                ItemStack targetItem = menu.getItemInSlot(targetSlots[ite]);
                                if (targetItem != null && targetItem.getAmount() == 1){
                                    ItemMeta targetMeta = targetItem.getItemMeta();
                                    if (targetMeta != null){
                                        Optional<ModifierInstance> optional3 = DataTypeMethods.getOptionalCustom(targetMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                                        if (optional3.isPresent()){
                                            String[] tempStr = optional3.get().getModifierType().split("_");
                                            if (tempStr[0].equals("riven")){
                                                if (!modList.contains("riven")){
                                                    modList.add("riven");
                                                    itemStack1[ite] = targetItem.clone();
                                                }else {
                                                    DropItem(player, targetItem);
                                                    menu.replaceExistingItem(targetSlots[ite], null);
                                                }

                                            }else {
                                                if (!modList.contains(tempStr[1])){
                                                    modList.add(tempStr[1]);
                                                    itemStack1[ite] = targetItem.clone();
                                                }else {
                                                    DropItem(player, targetItem);
                                                    menu.replaceExistingItem(targetSlots[ite], null);
                                                }
                                            }

                                        }else {
                                            DropItem(player, targetItem);
                                            menu.replaceExistingItem(targetSlots[ite], null);
                                        }
                                    }else {
                                        DropItem(player, targetItem);
                                        menu.replaceExistingItem(targetSlots[ite], null);
                                    }
                                }else {
                                    if (targetItem != null){
                                        DropItem(player, targetItem);
                                        menu.replaceExistingItem(targetSlots[ite], null);
                                    }
                                }
                            }else {itemStack1[ite] = itemStack.clone();}
                        }

                        int PolaritySlot = getPolaritySlotFromMODSlot(i);
                        if (menu.getItemInSlot(PolaritySlot) != null
                                && !menu.getItemInSlot(PolaritySlot).getType().equals(Material.BLACK_WOOL))
                        {
                            menu.replaceExistingItem(PolaritySlot, new CustomItemStack(Material.LIME_WOOL, ChatColor.GREEN + String.valueOf(countMODSize(menu, i, itemStack))));
//                            player.updateInventory();
                        }

                        SaveMODSlots(menu
                                ,itemStack1
                                ,equipmentInstance.getItemType()
                                ,getFormaed(menu, getPolaritySlotFromMODSlot(targetSlots)));
                        return true;
                    }

                }

                else {
                    itemStack2 = menu.getItemInSlot(i);
                    if (!(itemStack2 == null || itemStack2.getType() == Material.AIR)){

                        DropItem(player, itemStack2);
                        menu.replaceExistingItem(i, null);

                        InputSlotClickPart3(menu, player, targetSlots);
//                        List<String> modList = new ArrayList<>();
//                        for (int ite=0;ite<targetSlots.length;ite++){
//                            ItemStack targetItem = menu.getItemInSlot(targetSlots[ite]);
//                            if (targetItem != null && targetItem.getAmount() == 1){
//                                ItemMeta targetMeta = targetItem.getItemMeta();
//                                if (targetMeta != null){
//                                    Optional<ModifierInstance> optional3 = DataTypeMethods.getOptionalCustom(targetMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
//                                    if (optional3.isPresent()){
//                                        String[] tempStr = optional3.get().getModifierType().split("_");
//                                        if (tempStr[0].equals("riven")){
//                                            if (!modList.contains("riven")){
//                                                modList.add("riven");
//                                                itemStack1[ite] = targetItem.clone();
//                                            }else {
//                                                DropItem(player, targetItem);
//                                                menu.replaceExistingItem(targetSlots[ite], null);
//                                            }
//
//                                        }else {
//                                            if (!modList.contains(tempStr[1])){
//                                                modList.add(tempStr[1]);
//                                                itemStack1[ite] = targetItem.clone();
//                                            }else {
//                                                DropItem(player, targetItem);
//                                                menu.replaceExistingItem(targetSlots[ite], null);
//                                            }
//                                        }
//
//                                    }else {
//                                        DropItem(player, targetItem);
//                                        menu.replaceExistingItem(targetSlots[ite], null);
//                                    }
//                                }else {
//                                    DropItem(player, targetItem);
//                                    menu.replaceExistingItem(targetSlots[ite], null);
//                                }
//                            }else {
//                                if (targetItem != null){
//                                    DropItem(player, targetItem);
//                                    menu.replaceExistingItem(targetSlots[ite], null);
//                                }
//                            }
//                    }
                        SaveMODSlots(menu,targetSlots,equipmentInstance.getItemType());
                        int PolaritySlot = getPolaritySlotFromMODSlot(i);
                        if (menu.getItemInSlot(PolaritySlot) != null
                        && !menu.getItemInSlot(PolaritySlot).getType().equals(Material.BLACK_WOOL)){
                            menu.replaceExistingItem(PolaritySlot, new CustomItemStack(Material.WHITE_WOOL, properties.getReplacedProperty("ModifierTable_Formaed")));
                        }

//                        ItemStack[] itemStack1 = new ItemStack[targetSlots.length];
//
//                        SaveMODSlots(menu
//                                ,itemStack1
//                                ,equipmentInstance.getItemType()
//                                ,getFormaed(menu, getPolaritySlotFromMODSlot(targetSlots)));
                    }
                    return false;
                }
            }

            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        });
        ConstructPolaritySlots(menu, getPolaritySlotFromMODSlot(targetSlots), equipmentInstance.getFormaed(), targetSlots, equipmentInstance.getItemType());
    }}

    public void InputSlotClickPart3(BlockMenu menu, Player player,int[] targetSlots){
        ItemStack[] itemStack1 = new ItemStack[targetSlots.length];
        List<String> modList = new ArrayList<>();
        for (int ite=0;ite<targetSlots.length;ite++){
            ItemStack targetItem = menu.getItemInSlot(targetSlots[ite]);
            if (targetItem != null && targetItem.getAmount() == 1){
                ItemMeta targetMeta = targetItem.getItemMeta();
                if (targetMeta != null){
                    Optional<ModifierInstance> optional3 = DataTypeMethods.getOptionalCustom(targetMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                    if (optional3.isPresent()){
                        String[] tempStr = optional3.get().getModifierType().split("_");
                        if (tempStr[0].equals("riven")){
                            if (!modList.contains("riven")){
                                modList.add("riven");
                                itemStack1[ite] = targetItem.clone();
                            }else {
                                DropItem(player, targetItem);
                                menu.replaceExistingItem(targetSlots[ite], null);
                            }

                        }else {
                            if (!modList.contains(tempStr[1])){
                                modList.add(tempStr[1]);
                                itemStack1[ite] = targetItem.clone();
                            }else {
                                DropItem(player, targetItem);
                                menu.replaceExistingItem(targetSlots[ite], null);
                            }
                        }

                    }else {
                        DropItem(player, targetItem);
                        menu.replaceExistingItem(targetSlots[ite], null);
                    }
                }else {
                    DropItem(player, targetItem);
                    menu.replaceExistingItem(targetSlots[ite], null);
                }
            }else {
                if (targetItem != null){
                    DropItem(player, targetItem);
                    menu.replaceExistingItem(targetSlots[ite], null);
                }
            }
        }

    }
    public void SaveMODSlots(BlockMenu menu, int[] MODSlots, String itemType){
        ItemStack[] itemStacks = new ItemStack[MODSlots.length];
        for (int i1=0;i1<MODSlots.length;i1++){
            if (menu.getItemInSlot(MODSlots[i1]) != null
                    && menu.getItemInSlot(MODSlots[i1]).getType() != Material.BARRIER){
                ItemMeta itemMeta =menu.getItemInSlot(MODSlots[i1]).getItemMeta();
                if (itemMeta != null){
                    Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(itemMeta, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                    if (optional2.isPresent()){
                        itemStacks[i1] = menu.getItemInSlot(MODSlots[i1]).clone();
                    }
                }
            }
        }
        SaveMODSlots(menu
                ,itemStacks
                ,itemType
                ,getFormaed(
                        menu
                        ,getPolaritySlotFromMODSlot(MODSlots)
                )
        );
    }

    public void SaveMODSlots(BlockMenu menu,ItemStack[] itemStacks,String itemType, boolean[] isFormaed){
        AbstractModifiableItem.ModifyItem(menu.getItemInSlot(getInputSlots()[0]), itemStacks, itemType, isFormaed);
    }
    @NotNull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CAPACITOR;
    }

    @Override
    public int getCapacity() {
        return 1;
    }

    @Override
    public boolean isChargeable() {
        return false;
    }

    @Override
    public int getCharge(@NotNull Location l) {
        return 1;
    }

    public void UpdatePotatoSlot(BlockMenu menu, ItemStack input){
        if (input != null && input.getType() != Material.AIR){
//            logger.log(Level.WARNING, "0-1");
            ItemMeta itemMeta = input.getItemMeta();
            if (itemMeta == null){
                borders(menu, potatoSlot);
                return;
            }
            Optional<PotatoableInstance> optional = DataTypeMethods.getOptionalCustom(itemMeta, POTATOABLE_INSTANCE, PersistentSFEPotatoableType.TYPE);
            if (optional.isPresent()){
//                logger.log(Level.WARNING, "1-1");
                PotatoableInstance potatoableInstance = optional.get();//input potato status
                if (!potatoableInstance.getPotatoed()){
                    if (potatoableInstance.getPotato().equals("blue")){
                        borders(menu, potatoSlot,Material.RED_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_Orokin_Catalyzer_Not_Installed"));
                        menu.addMenuClickHandler(potatoSlot[0], new ChestMenu.AdvancedMenuClickHandler() {
                            @Override
                            public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                                if (inventoryClickEvent.isLeftClick()){
//                                    logger.log(Level.WARNING, "2-1");
                                    ItemStack itemStack1 = inventoryClickEvent.getCursor();
                                    if (itemStack1 != null){
//                                        logger.log(Level.WARNING, "2-2");
//                                        if (itemStack1.getItemMeta() != null){
//                                            logger.log(Level.WARNING, "2-3");
                                            if (SlimefunUtils.isItemSimilar(itemStack1, BLUE_POTATO, false, false)){
//                                                logger.log(Level.WARNING, "2-4");
                                                itemStack1.setAmount(itemStack1.getAmount() - 1);
                                                ItemMeta itemMeta1 = menu.getItemInSlot(getInputSlots()[0]).getItemMeta();
                                                DataTypeMethods.setCustom(itemMeta1, Keys.POTATOABLE_INSTANCE, PersistentSFEPotatoableType.TYPE, new PotatoableInstance(true, "blue"));
                                                menu.getItemInSlot(getInputSlots()[0]).setItemMeta(itemMeta1);
                                                borders(menu, potatoSlot,Material.GREEN_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_Orokin_Catalyzer_Installed"));
                                                player.updateInventory();
                                            }
//                                        }
                                    }
                                }
                                return false;
                            }

                            @Override
                            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                                return false;
                            }
                        });
                    }
                    else {
                        borders(menu, potatoSlot,Material.RED_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_Orokin_Reactor_Not_Installed"));
                        menu.addMenuClickHandler(potatoSlot[0], new ChestMenu.AdvancedMenuClickHandler() {
                            @Override
                            public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                                if (inventoryClickEvent.isLeftClick()){
                                    ItemStack itemStack1 = inventoryClickEvent.getCursor();
                                    if (itemStack1 != null){
//                                        if (itemStack1 instanceof SlimefunItemStack){
                                            if (SlimefunUtils.isItemSimilar(itemStack1, BLUE_POTATO, false, false)){
                                                itemStack1.setAmount(itemStack1.getAmount() - 1);
                                                ItemMeta itemMeta1 = menu.getItemInSlot(getInputSlots()[0]).getItemMeta();
                                                DataTypeMethods.setCustom(itemMeta1, Keys.POTATOABLE_INSTANCE, PersistentSFEPotatoableType.TYPE, new PotatoableInstance(true, "golden"));
                                                menu.getItemInSlot(getInputSlots()[0]).setItemMeta(itemMeta1);
                                                borders(menu, potatoSlot,Material.GREEN_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_Orokin_Reactor_Installed"));
                                            }
//                                        }
                                    }
                                }
                                return false;
                            }

                            @Override
                            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                                return false;
                            }
                        });
                    }
                }else {
                    if (potatoableInstance.getPotato().equals("blue")){borders(menu, 8,Material.GREEN_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_Orokin_Catalyzer_Installed"));}
                    else {borders(menu, potatoSlot,Material.GREEN_STAINED_GLASS_PANE, properties.getReplacedProperty("ModifierTable_Orokin_Reactor_Installed"));}
                }
            }
            else {
//                logger.log(Level.WARNING, "1-2");
                borders(menu, potatoSlot);}
        }else {
//            logger.log(Level.WARNING, "0-2");
            borders(menu, potatoSlot);}
    }

    public void ConstructPolaritySlot(BlockMenu menu,int PolaritySlot, boolean isFormaed, int[] targetSlots, String equipmentType){
        if(!isFormaed){
//            logger.log(Level.WARNING, PolaritySlot + " addHandler");
            menu.replaceExistingItem(PolaritySlot, new CustomItemStack(Material.BLACK_WOOL, properties.getReplacedProperty("ModifierTable_NOPolarity")));
            menu.addMenuClickHandler(PolaritySlot, new ChestMenu.AdvancedMenuClickHandler() {
                @Override
                public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    if (!inventoryClickEvent.isLeftClick()){
                        return false;
                    }else {
                        ItemStack cursorItem = inventoryClickEvent.getCursor();
                        if (SlimefunUtils.isItemSimilar(cursorItem, FORMA, false, false)){
                            menu.replaceExistingItem(PolaritySlot, new CustomItemStack(Material.WHITE_WOOL, properties.getReplacedProperty("ModifierTable_Formaed")));
                            menu.addMenuClickHandler(PolaritySlot, new ChestMenu.MenuClickHandler() {
                                @Override
                                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                                    return false;
                                }
                            });
                            InputSlotClickPart3(menu, player, targetSlots);
                            SaveMODSlots(menu, targetSlots, equipmentType);
                            cursorItem.setAmount(cursorItem.getAmount() - 1);
                            player.updateInventory();
                        }
                        return false;
                    }
                }

                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    return false;
                }
            });
        }
        else {
            ItemStack itemStack = menu.getItemInSlot(getMODSlotFromPolaritySlot(PolaritySlot));
            if (itemStack != null && itemStack.getItemMeta() != null){
                Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(itemStack.getItemMeta(), MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
                if (optional2.isPresent()){
                    menu.replaceExistingItem(PolaritySlot, new CustomItemStack(Material.LIME_WOOL,
                            ChatColor.GREEN + String.valueOf(countMODSize(menu, getMODSlotFromPolaritySlot(PolaritySlot), itemStack))));
                }else{
                    menu.replaceExistingItem(PolaritySlot, new CustomItemStack(Material.WHITE_WOOL, ChatColor.GRAY + properties.getReplacedProperty("ModifierTable_Formaed")));
                }
            }else {
                menu.replaceExistingItem(PolaritySlot, new CustomItemStack(Material.WHITE_WOOL, ChatColor.GRAY + properties.getReplacedProperty("ModifierTable_Formaed")));
            }

            menu.addMenuClickHandler(PolaritySlot, new ChestMenu.MenuClickHandler() {
                @Override
                public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                    return false;
                }
            });
        }
    }

    public void ConstructPolaritySlots(BlockMenu menu,int[] PolaritySlot, boolean[] isFormaed, int[] targetSlots, String equipmentType){
        for (int i=0; i<PolaritySlot.length; i++){
            ConstructPolaritySlot(menu, PolaritySlot[i], isFormaed[i],targetSlots,equipmentType);
        }
    }

    public boolean[] getFormaed (BlockMenu menu, int[] PolaritySlots){
        boolean[] booleans = new boolean[PolaritySlots.length];
        for (int i=0;i<PolaritySlots.length;i++){
            booleans[i] = getFormaed(menu, PolaritySlots[i]);
        }
        return booleans;
    }

    public boolean getFormaed(BlockMenu menu, int PolaritySlot){
        return menu.getItemInSlot(PolaritySlot) != null && menu.getItemInSlot(PolaritySlot).getType() != Material.BLACK_WOOL;
    }

    public int countMODSize(BlockMenu menu, int[] MODSlots){
        int result = 0;
        for (int i:MODSlots){
            result += countMODSize(menu, i);
        }
        return result;
    }

    public int countMODSize(BlockMenu menu, int[] MODSlots, int replaceMODSlot, ItemStack replaceWithMOD){
        int result = 0;
        for (int i:MODSlots){
            if (i != replaceMODSlot){
                result += countMODSize(menu, i);
            }else {
                result += countMODSize(menu, replaceMODSlot, replaceWithMOD);
            };
        }
        return result;
    }
    public int countMODSize(BlockMenu menu, int MODSlot){
        ItemStack mod = menu.getItemInSlot(MODSlot);
        if (mod != null && mod.getType() != Material.AIR){
            ItemMeta itemMeta1 = mod.getItemMeta();
            if (itemMeta1 == null){
//                logger.log(Level.WARNING,"1");
                return 0;}
            Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(itemMeta1, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
            if (optional2.isEmpty()){
//                logger.log(Level.WARNING,"2");
                return 0;}
            else {
                ModifierInstance modInstance = optional2.get();
                String[] modInfo = modInstance.getModifierType().split("_");
                String modName = modInfo[2];

                int size =
                        ((int) (Math.round(modInstance.getLevel()*modifierSizeAddPerLevel.get(modName))))
                        + modifierBasicSize.get(modName);
                if (getFormaed(menu, getPolaritySlotFromMODSlot(MODSlot))){
                    if (MODSlot == AuraOrStanceSlot[0])
                    {
                        size = (size * 2);
                    }
                    else
                    {
                        size = (size/2);
                    }
                }
                if (MODSlot == AuraOrStanceSlot[0]){
                    size *= -1;
                }
                return size;
            }
        }else {return 0;}
    }

    public int countMODSize(BlockMenu menu, int MODSlot, ItemStack inputMOD){
        ItemStack mod = inputMOD;
        if (mod != null && mod.getType() != Material.AIR){
            ItemMeta itemMeta1 = mod.getItemMeta();
            if (itemMeta1 == null){return 0;}
            Optional<ModifierInstance> optional2 = DataTypeMethods.getOptionalCustom(itemMeta1, MODIFIER_ITEM, PersistentSFEModifierType.TYPE);
            if (!optional2.isPresent()){return 0;}
            else {
                ModifierInstance modInstance = optional2.get();
                String[] modInfo = modInstance.getModifierType().split("_");
                String modName = modInfo[2];

                int size =
                        ((int) (Math.round(modInstance.getLevel()*modifierSizeAddPerLevel.get(modName))))
                                + modifierBasicSize.get(modName);

                if (getFormaed(menu, getPolaritySlotFromMODSlot(MODSlot))){
                    if (MODSlot == AuraOrStanceSlot[0]){
                        size = (size * 2);
                    }else {size = (size/2);}
                }
                if (MODSlot == AuraOrStanceSlot[0]){
                    size *= -1;
                }
                return size;
            }
        }else {return 0;}
    }

    public int getInputEquipmentSize(BlockMenu menu, ItemStack input){
        if (input != null && input.getType() != Material.AIR){
            ItemMeta itemMeta = input.getItemMeta();
            if (itemMeta == null){return 0;}
            Optional<PotatoableInstance> optional = DataTypeMethods.getOptionalCustom(itemMeta, POTATOABLE_INSTANCE, PersistentSFEPotatoableType.TYPE);
            if (optional.isPresent()){
                if (optional.get().getPotatoed()){
                    return 60;
                }else {return 30;}
            }
            else {return 0;}
        }else {return 0;}
    }

    public static void DropItem(Player player, ItemStack itemStack){
        Map<Integer, ItemStack> remaining = player.getInventory().addItem(itemStack);
        for (ItemStack stack : remaining.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), stack);
        }
    }
    public static void DropItem(World world,Location location, ItemStack itemStack){
        world.dropItemNaturally(location, itemStack);
    }

    public static int[] getTargetSlots(EquipmentInstance equipmentInstance){
        int modslotLength = equipmentInstance.getModifiers().length;

        if (modslotLength == 8){
            return MODSlots;
        }else if(modslotLength == 9){
            if (equipmentInstance.getItemType().equals("ranged")){
                return ExilusAndMODSlots;
            }else {
                return AuraOrStanceSlot;
            }
        }else if(modslotLength == 10){
            if (equipmentInstance.getItemType().equals("archwing")
                    || equipmentInstance.getItemType().equals("warframe")//may not exists
            ){
                return FrameMODSlots;
            }
            else {
                return LargeMODSlots;
            }
        }else {return new int[0];}
    }
}
