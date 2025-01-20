package io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.acdeasdff.SlimeFrameExtension.Items.Instance.PotatoableInstance;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class PersistentSFEPotatoableType implements PersistentDataType<PersistentDataContainer, PotatoableInstance> {

    public static final PersistentDataType<PersistentDataContainer, PotatoableInstance> TYPE = new PersistentSFEPotatoableType();

    public static final NamespacedKey IS_POTATOABLE = Keys.newKey("sfe_potatoable");
    public static final NamespacedKey POTATOED = Keys.newKey("sfe_potatoed");
    public static final NamespacedKey POTATO = Keys.newKey("sfe_potato");

    @Override
    @Nonnull
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    @Nonnull
    public Class<PotatoableInstance> getComplexType() {
        return PotatoableInstance.class;
    }

    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull PotatoableInstance complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();

        container.set(IS_POTATOABLE, DataType.BOOLEAN, true);
        container.set(POTATOED, DataType.BOOLEAN, complex.getPotatoed());
        container.set(POTATO, DataType.STRING, complex.getPotato());
        return container;
    }

    @Override
    @Nonnull
    public PotatoableInstance fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        final boolean potatoed = primitive.get(POTATOED, DataType.BOOLEAN);
        final String potato = primitive.get(POTATO, DataType.STRING);
        return new PotatoableInstance(potatoed, potato);
    }
}
