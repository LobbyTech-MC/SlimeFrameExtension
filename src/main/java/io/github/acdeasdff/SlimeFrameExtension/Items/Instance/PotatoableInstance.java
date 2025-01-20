package io.github.acdeasdff.SlimeFrameExtension.Items.Instance;

import javax.annotation.Nonnull;


public class PotatoableInstance {

    private final boolean potatoed;
    private final String potato;


    public PotatoableInstance(@Nonnull boolean potatoed, String potato) {
        this.potatoed = potatoed;
        this.potato = potato;//blue or golden
    }

    public boolean getPotatoed() {
        return potatoed;
    }

    public String getPotato() {
        return potato;
    }
}


