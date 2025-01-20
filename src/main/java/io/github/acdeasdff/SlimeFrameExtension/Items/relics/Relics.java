package io.github.acdeasdff.SlimeFrameExtension.Items.relics;

import io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.voper.slimeframe.implementation.items.relics.Relic;
import me.voper.slimeframe.implementation.items.relics.RelicItemStack;

import java.util.ArrayList;
import java.util.List;

import static io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.BRATON_PRIME.*;
import static io.github.acdeasdff.SlimeFrameExtension.Items.Weapons.ranged.RUBICO_PRIME.*;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;
import static me.voper.slimeframe.implementation.SFrameStacks.*;
import static me.voper.slimeframe.implementation.items.relics.Relic.Era.MESO;
import static me.voper.slimeframe.implementation.items.relics.Relic.Era.NEO;

public class Relics {
    public static List<RelicItemStack> relics = new ArrayList<>();
    public static RelicItemStack MESO_R1 = new RelicItemStack(properties.getReplacedProperty("MESO_R1"), MESO,
            new SlimefunItemStack[]{BRATON_PRIME_TRIGGER, RUBICO_PRIME_TRIGGER},
            new SlimefunItemStack[]{BRATON_PRIME_BLUEPRINT, RUBICO_PRIME_BARREL},
            RUBICO_PRIME_BLUEPRINT
    );
    public static RelicItemStack NEO_R1 = new RelicItemStack(properties.getReplacedProperty("NEO_R1"), NEO,
            new SlimefunItemStack[]{BRATON_PRIME_BARREL, RUBICO_PRIME_TRIGGER},
            new SlimefunItemStack[]{BRATON_PRIME_BODY, RUBICO_PRIME_BODY},
            RUBICO_PRIME_BLUEPRINT
    );


    public static void setup(SlimeFrameExtension plugin) {
        relics.add(MESO_R1);
        relics.add(NEO_R1);

        for (RelicItemStack relicItemStack : relics) {
            switch (relicItemStack.getRelicEra()) {
                case LITH -> RANDOM_LITH_RELICS.add(relicItemStack);
                case MESO -> RANDOM_MESO_RELICS.add(relicItemStack);
                case NEO -> RANDOM_NEO_RELICS.add(relicItemStack);
                default -> RANDOM_AXI_RELICS.add(relicItemStack);
            }
            Relic.create(relicItemStack).register(plugin);
        }
    }
}
