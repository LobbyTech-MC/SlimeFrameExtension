package io.github.acdeasdff.SlimeFrameExtension.Items.Instance;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;



public class PotatoableInstance{

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


