package io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated;


import com.jeff_media.morepersistentdatatypes.DataType;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.ModifierInstance;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class PersistentSFEModifierType implements PersistentDataType<PersistentDataContainer, ModifierInstance> {

    public static final PersistentDataType<PersistentDataContainer, ModifierInstance> TYPE = new PersistentSFEModifierType();

    public static final NamespacedKey IS_MODIFIER = Keys.newKey("sfe_modifier");
    public static final NamespacedKey MODIFIER_LEVEL = Keys.newKey("sfe_modifier_level");
    public static final NamespacedKey MODIFIER_BASIC_SIZE = Keys.newKey("sfe_modifier_basic_size");
    public static final NamespacedKey MODIFIER_TYPE = Keys.newKey("sfe_modifier_type");

    @Override
    @Nonnull
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    @Nonnull
    public Class<ModifierInstance> getComplexType() {
        return ModifierInstance.class;
    }

    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull ModifierInstance complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();

        container.set(IS_MODIFIER, DataType.BOOLEAN, true);
        container.set(MODIFIER_LEVEL, DataType.INTEGER, complex.getLevel());
        container.set(MODIFIER_TYPE, DataType.STRING, complex.getModifierType());
//        container.set(MODIFIER_BASIC_SIZE, DataType.INTEGER, complex.getBasicSize());
        return container;
    }

    @Override
    @Nonnull
    public ModifierInstance fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        final int level = primitive.get(MODIFIER_LEVEL, DataType.INTEGER);
        final String type = primitive.get(MODIFIER_TYPE, DataType.STRING);
//        final int basicSize = primitive.get(MODIFIER_BASIC_SIZE, DataType.INTEGER);
        return new ModifierInstance(level, type);
    }
}
