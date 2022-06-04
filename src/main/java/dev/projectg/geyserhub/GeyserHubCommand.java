package dev.projectg.geyserhub;

import com.google.common.collect.ImmutableMap;
import dev.projectg.geyserhub.reloadable.ReloadableRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;

public class GeyserHubCommand implements CommandExecutor {

    private static final Map<Logger.Level, ChatColor> LOGGER_COLORS = ImmutableMap.of(
            Logger.Level.INFO, ChatColor.RESET,
            Logger.Level.WARN, ChatColor.GOLD,
            Logger.Level.SEVERE, ChatColor.RED);

    private static final String[] HELP = {
            "/ghub - Opens the default form if one exists. If not, shows the help page",
            "/ghub - Opens the help page",
            "/ghub form <form> - Open a form with the defined name",
            "/ghub form <form> <player> - Sends a form to a given player",
            "/ghub reload - reloads the selector"
    };

    private static final String NO_PERMISSION = "Sorry, you don't have permission to run that command!";
    private static final String UNKNOWN = "Sorry, that's an unknown command!";

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player || commandSender instanceof ConsoleCommandSender)) {
            return false;
        }
        // todo: cleanup
        if (args.length == 0) {
            sendHelp(commandSender);
            return true;
        }

        // At least one arg
        switch (args[0]) {
            case "reload":
                if (commandSender.hasPermission("geyserhub.reload")) {
                    if (!ReloadableRegistry.reloadAll()) {
                        sendMessage(commandSender, Logger.Level.SEVERE, "There was an error reloading something! Please check the server console for further information.");
                    }
                } else {
                    sendMessage(commandSender, Logger.Level.SEVERE, NO_PERMISSION);
                }
                break;
            case "help":
                sendHelp(commandSender);
                break;
            default:
                sendMessage(commandSender, Logger.Level.SEVERE, UNKNOWN);
                break;
        }
        return true;
    }

    private void sendHelp(CommandSender commandSender) {
        // todo: only show players with the given permissions certain entries? not sure if it can be integrated any way into spigot command completions
        commandSender.sendMessage(HELP);
    }

    public static void sendMessage(@Nonnull CommandSender sender, @Nonnull Logger.Level level, @Nonnull String message) {
        Objects.requireNonNull(sender);
        Objects.requireNonNull(level);
        Objects.requireNonNull(message);

        if (sender instanceof ConsoleCommandSender) {
            Logger.getLogger().log(level, message);
        } else {
            sender.sendMessage("[GeyserHub] " + LOGGER_COLORS.getOrDefault(level, ChatColor.RESET) + message);
        }
    }
}
