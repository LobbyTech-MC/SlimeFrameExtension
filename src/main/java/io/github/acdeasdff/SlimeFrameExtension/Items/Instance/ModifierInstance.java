package io.github.acdeasdff.SlimeFrameExtension.Items.Instance;

import javax.annotation.Nonnull;


public class ModifierInstance {

    private final int level;
    private final String modifierType;
    private final int basicSize;


    public ModifierInstance(@Nonnull int level, String modifierType, int basicSize) {
        this.level = level;
        this.modifierType = modifierType;
        this.basicSize = basicSize;
    }

    public ModifierInstance(@Nonnull int level, String modifierType) {
        this.level = level;
        this.modifierType = modifierType;
        this.basicSize = 0;
    }

    public int getLevel() {
        return level;
    }

    public String getModifierType() {
        return modifierType;
    }

    @Deprecated
    public int getBasicSize() {
        return basicSize;
    }
}


