package io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated;


import com.jeff_media.morepersistentdatatypes.DataType;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.EquipmentInstance;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class PersistentSFEEquipmentType implements PersistentDataType<PersistentDataContainer, EquipmentInstance> {

    public static final PersistentDataType<PersistentDataContainer, EquipmentInstance> TYPE = new PersistentSFEEquipmentType();

    public static final NamespacedKey MODIFIER = Keys.newKey("sfe_modifiers");
    public static final NamespacedKey MODIFIER_META = Keys.newKey("sfe_modifier_meta");
    //    public static final NamespacedKey IS_MODIFIABLE = Keys.newKey("sfe_modifiable");
    public static final NamespacedKey ITEM_TYPE = Keys.newKey("sfe_item_type");
    public static final NamespacedKey FORMAED = Keys.newKey("sfe_formaed");

    @Override
    @Nonnull
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    @Nonnull
    public Class<EquipmentInstance> getComplexType() {
        return EquipmentInstance.class;
    }

    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull EquipmentInstance complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();

        ItemStack[] ModifierMaterials = new ItemStack[complex.getModifierMaterials().length];
        ItemMeta[] ModifierMetas = new ItemMeta[complex.getModifierMetas().length];
        for (int i = 0; i < complex.getModifiers().length; i++) {
            if (complex.getModifierMaterials()[i] != null) {
                ModifierMaterials[i] = new ItemStack(complex.getModifierMaterials()[i].getType(), complex.getModifierMaterials()[i].getAmount());
                ModifierMetas[i] = complex.getModifiers()[i].getItemMeta();
            } else {
                ModifierMaterials[i] = null;
                ModifierMetas[i] = null;
            }
        }
        container.set(MODIFIER, DataType.ITEM_STACK_ARRAY, ModifierMaterials);
        container.set(MODIFIER_META, DataType.ITEM_META_ARRAY, ModifierMetas);
        container.set(ITEM_TYPE, DataType.STRING, complex.getItemType());
        container.set(FORMAED, DataType.BOOLEAN_ARRAY, complex.getFormaed());
        return container;
    }

    @Override
    @Nonnull
    public EquipmentInstance fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        final ItemStack[] modifier = primitive.get(MODIFIER, DataType.ITEM_STACK_ARRAY);
        final ItemMeta[] modifierMetas = primitive.get(MODIFIER_META, DataType.ITEM_META_ARRAY);
        final String itemType = primitive.get(ITEM_TYPE, DataType.STRING);
        final boolean[] isFormaed = primitive.get(FORMAED, DataType.BOOLEAN_ARRAY);
        return new EquipmentInstance(modifier, modifierMetas, itemType, isFormaed);
    }
}
