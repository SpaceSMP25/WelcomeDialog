package com.spacesmp.welcomedialog.command;

import com.spacesmp.welcomedialog.WelcomeDialogPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public final class InfoCommand implements TabExecutor {

    private final WelcomeDialogPlugin plugin;

    public InfoCommand(WelcomeDialogPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can open the welcome dialog.", NamedTextColor.RED));
            return true;
        }

        this.plugin.showWelcomeDialog(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }
}
