package io.github.acdeasdff.SlimeFrameExtension;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import me.voper.slimeframe.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

import static io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys.ENDOS_OWNED;
import static io.github.acdeasdff.SlimeFrameExtension.SlimeFrameExtension.properties;

@CommandAlias("%slimeframeextension")
@Description("Root command for SlimeFrameExtension.")
public class Commands extends BaseCommand {

    @Dependency
    private SlimeFrameExtension plugin;
    @Dependency
    private SettingsManager sm;
//    @Dependency
//    private RelicInventoryManager relicMan;

    @Nonnull
    public static NamespacedKey createKey(@Nonnull String key) {
        return new NamespacedKey(SlimeFrameExtension.instance(), key);
    }

    @HelpCommand
    public void help(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("%endos")
    @CommandPermission("slimeframeextension.anyone.endos")
    @Description("Show the amount of endos a player has")
    public void showEndos(CommandSender sender, @Optional OnlinePlayer onlinePlayer) {
        if (!(sender instanceof Player p)) return;

        int endosOwned = PersistentDataAPI.getInt(onlinePlayer != null ? onlinePlayer.getPlayer() : p, ENDOS_OWNED, 0);

        if (onlinePlayer == null) {
            ChatUtils.sendMessage(p, ChatColor.GREEN + properties.getReplacedProperty("Endo_You_have") + ChatColor.AQUA + endosOwned + ChatColor.GREEN + properties.getReplacedProperty("Endo"));
        } else {
            if (p.isOp() || onlinePlayer.getPlayer().getUniqueId().equals(p.getUniqueId())) {
                ChatUtils.sendMessage(p,
                        ChatColor.WHITE + onlinePlayer.getPlayer().getName()
                                + properties.getReplacedProperty("Endo_have")
                                + ChatColor.GREEN + endosOwned
                                + ChatColor.YELLOW + properties.getReplacedProperty("Endo"));
            } else {
                ChatUtils.sendMessage(p, ChatColor.RED + properties.getReplacedProperty("NoPermission"));
            }
        }
    }

    @Subcommand("%addendos")
    @CommandPermission("slimeframeextension.admin.addendos")
    @Description("addEndos for a player")
    public void addEndos(CommandSender sender, @Optional OnlinePlayer onlinePlayer, @Optional int addedEndos) {
        if (!(sender instanceof Player p)) return;

        int endosOwned = PersistentDataAPI.getInt(onlinePlayer != null ? onlinePlayer.getPlayer() : p, ENDOS_OWNED, 0);

        if (onlinePlayer == null) {
            ChatUtils.sendMessage(p, ChatColor.RED + properties.getReplacedProperty("Commands_Player_Not_Found"));
        } else {
            if (p.isOp() || onlinePlayer.getPlayer().getUniqueId().equals(p.getUniqueId())) {
                PersistentDataAPI.setInt(onlinePlayer.getPlayer(), ENDOS_OWNED, endosOwned + addedEndos);
                ChatUtils.sendMessage(p,
                        ChatColor.WHITE + onlinePlayer.getPlayer().getName()
                                + properties.getReplacedProperty("Endo_have")
                                + ChatColor.GREEN + endosOwned + "+" + addedEndos
                                + ChatColor.YELLOW + properties.getReplacedProperty("Endo"));
            } else {
                ChatUtils.sendMessage(p, ChatColor.RED + properties.getReplacedProperty("NoPermission"));
            }
        }
    }
}
