package io.github.acdeasdff.SlimeFrameExtension;

import io.github.acdeasdff.SlimeFrameExtension.Groups.Group;
import io.github.acdeasdff.SlimeFrameExtension.Items.Other.ModifierStatusRecorder;
import io.github.acdeasdff.SlimeFrameExtension.Items.registerBlocks;
import io.github.acdeasdff.SlimeFrameExtension.Items.registerModifiers;
import io.github.acdeasdff.SlimeFrameExtension.Items.registerOthers;
import io.github.acdeasdff.SlimeFrameExtension.Items.registerWeapons;
import io.github.acdeasdff.SlimeFrameExtension.Items.relics.Relics;
import io.github.acdeasdff.SlimeFrameExtension.Listeners.BulletListener;
import io.github.acdeasdff.SlimeFrameExtension.Listeners.PlayerKillEntityListener;
import io.github.acdeasdff.SlimeFrameExtension.TweakedProperty2.TweakedProperty2;
import io.github.mooy1.infinitylib.common.Events;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.awt.*;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;


public class SlimeFrameExtension extends AbstractAddon {
    public static net.md_5.bungee.api.ChatColor COLOR_MIKU = net.md_5.bungee.api.ChatColor.of(new Color(0x39C5BB));
    public static ChatColor CommonMODColor = ChatColor.GOLD;
    public static ChatColor UncommonMODColor = ChatColor.GRAY;
    public static ChatColor RareMODColor = ChatColor.YELLOW;
    public static ChatColor PrimedMODColor = ChatColor.WHITE;
    public static ChatColor RivenMODColor = ChatColor.LIGHT_PURPLE;
    public static ChatColor StrangeMODColor = ChatColor.DARK_GRAY;
    public static SlimeFrameExtension instance;
    public static Logger logger;
    public static TweakedProperty2 properties;
    public static BulletListener bulletListener = new BulletListener();
    public static PlayerKillEntityListener playerKillEntityListener = new PlayerKillEntityListener();
    public static SlimeFrameExtension plugin;
    public static boolean placeholderAPIEnabled = false;
    public static boolean ProtocolLibEnabled = false;
    public static ProtocolLibSupport protocolLibSupport;
    private static String language;
    private CommandManager commandsManager;
    private SettingsManager settingsManager;

    public SlimeFrameExtension() {
        super("acdeasdff", "SlimeFrameExtension", "master", "auto-update");
    }

    public SlimeFrameExtension(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file, String githubUserName, String githubRepo, String autoUpdateBranch, String autoUpdateKey) {
        super(loader, description, dataFolder, file,
                "acdeasdff", "SlimeFrameExtension", "master", "auto-update");
    }

    public static SettingsManager getSettingsManager() {
        return instance.settingsManager;
    }

    @Override
    protected void enable() {

        this.settingsManager = new SettingsManager(this);

        instance = this;
        logger = this.getLogger();
        language = getConfig().getString("language");

        properties = new TweakedProperty2();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(this.getClassLoader().getResourceAsStream("language/" + language + ".properties"), StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.commandsManager = new CommandManager(this);
        this.commandsManager.setup();

        placeholderAPIEnabled = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        ProtocolLibEnabled = Bukkit.getPluginManager().isPluginEnabled("ProtocolLib");
        if (ProtocolLibEnabled) {
            protocolLibSupport = new ProtocolLibSupport();
        }

        new Metrics(this, 19233);
        Group.setup(this);

        ModifierStatusRecorder.setup(this);

        registerBlocks.setup(this);
        registerWeapons.setup(this);
        registerModifiers.setup(this);
        registerOthers.setup(this);
        Relics.setup(this);

        Events.registerListener(bulletListener);
        Events.registerListener(playerKillEntityListener);
//        new AdvancedMobDropListener2(this);
    }

    @Override
    protected void disable() {

    }


}
