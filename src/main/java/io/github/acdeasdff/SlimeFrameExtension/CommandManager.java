package io.github.acdeasdff.SlimeFrameExtension;

import co.aikar.commands.PaperCommandManager;
import java.util.Arrays;

import static io.github.acdeasdff.SlimeFrameExtension.SettingsManager.ConfigField.*;

public class CommandManager extends PaperCommandManager {

    private final SlimeFrameExtension plugin;
    private SettingsManager sm;

    public CommandManager(SlimeFrameExtension plugin) {
        super(plugin);
        this.plugin = plugin;
        this.sm=SlimeFrameExtension.getSettingsManager();
    }

    public void setup() {
        this.enableUnstableAPI("help");
        this.registerCommandReplacements();
        this.registerDependencies();
        this.registerCommandCompletions();
        this.registerCommand(new Commands());
    }

    private void registerDependencies() {
        this.registerDependency(SettingsManager.class, sm);
    }

    private void registerCommandReplacements() {
        this.getCommandReplacements().addReplacements(
                "slimeframeextension", String.join("|", this.sm.getStringList(SFE_CMD)),
                "endos", String.join("|", this.sm.getStringList(ENDOS_CMD)),
                "addendos", String.join("|", this.sm.getStringList(ADDENDOS_CMD))
        );
    }

    private void registerCommandCompletions() {

    }

}
