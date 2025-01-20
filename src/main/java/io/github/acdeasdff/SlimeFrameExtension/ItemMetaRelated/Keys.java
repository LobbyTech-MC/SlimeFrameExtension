package io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated;
//io.github.sefiraat.networks

import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

import static io.github.acdeasdff.SlimeFrameExtension.Commands.createKey;

@Data
@UtilityClass
public class Keys {

    //    public static final NamespacedKey ON_COOLDOWN = newKey("cooldown");
//    public static final NamespacedKey CARD_INSTANCE = newKey("ntw_card");
//    public static final NamespacedKey QUANTUM_STORAGE_INSTANCE = newKey("quantum_storage");
    public static final NamespacedKey MODIFIABLE_INSTANCE = newKey("sfe_modifiable");
    public static final NamespacedKey POTATOABLE_INSTANCE = newKey("sfe_potatoable");
    //    public static final NamespacedKey MODIFIABLE_ITEM = newKey("sfe_modifiable_item");
//    public static final NamespacedKey FACE = newKey("face");
//    public static final NamespacedKey ITEM = newKey("item");
    public static final NamespacedKey MODIFIER_ITEM = newKey("sfe_modifier");
    public static final NamespacedKey ENDOS_OWNED = createKey("sfe_endos_owned");

    @Nonnull
    public static NamespacedKey newKey(@Nonnull String value) {
        return new NamespacedKey(SlimeFrameExtension.instance(), value);
    }
}

