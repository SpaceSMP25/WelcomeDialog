package com.spacesmp.welcomedialog.command;

import com.spacesmp.welcomedialog.WelcomeDialogPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public final class WelcomeDialogCommand implements TabExecutor {

    private static final String ADMIN_PERMISSION = "welcomedialog.admin";
    private static final List<String> SUBCOMMANDS = List.of("reload", "preview");

    private final WelcomeDialogPlugin plugin;

    public WelcomeDialogCommand(WelcomeDialogPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(ADMIN_PERMISSION)) {
            sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Component.text("Usage: /" + label + " <reload|preview>", NamedTextColor.YELLOW));
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "reload" -> {
                this.plugin.reload();
                sender.sendMessage(Component.text("WelcomeDialog configuration reloaded.", NamedTextColor.GREEN));
            }
            case "preview" -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Component.text("Only players can preview the welcome dialog.", NamedTextColor.RED));
                    return true;
                }
                this.plugin.showWelcomeDialog(player);
            }
            default -> sender.sendMessage(Component.text("Usage: /" + label + " <reload|preview>", NamedTextColor.YELLOW));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(ADMIN_PERMISSION) || args.length != 1) {
            return List.of();
        }
        String prefix = args[0].toLowerCase(Locale.ROOT);
        return SUBCOMMANDS.stream()
                .filter(sub -> sub.startsWith(prefix))
                .toList();
    }
}
