package com.spacesmp.welcomedialog;

import com.spacesmp.welcomedialog.command.InfoCommand;
import com.spacesmp.welcomedialog.command.WelcomeDialogCommand;
import com.spacesmp.welcomedialog.config.DialogConfig;
import com.spacesmp.welcomedialog.dialog.WelcomeDialogFactory;
import com.spacesmp.welcomedialog.listener.PlayerJoinListener;
import io.papermc.paper.dialog.Dialog;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.nio.file.Path;

public final class WelcomeDialogPlugin extends JavaPlugin {

    private DialogConfig dialogConfig;
    private Dialog welcomeDialog;

    @Override
    public void onEnable() {
        reload();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        PluginCommand adminCommand = getCommand("welcomedialog");
        if (adminCommand != null) {
            WelcomeDialogCommand handler = new WelcomeDialogCommand(this);
            adminCommand.setExecutor(handler);
            adminCommand.setTabCompleter(handler);
        } else {
            getLogger().warning("Command 'welcomedialog' is missing from plugin.yml.");
        }

        PluginCommand infoCommand = getCommand("info");
        if (infoCommand != null) {
            InfoCommand infoHandler = new InfoCommand(this);
            infoCommand.setExecutor(infoHandler);
            infoCommand.setTabCompleter(infoHandler);
        } else {
            getLogger().warning("Command 'info' is missing from plugin.yml.");
        }
    }

    public void reload() {
        Path configPath = getDataFolder().toPath().resolve("config.json");
        if (!Files.exists(configPath)) {
            saveResource("config.json", false);
        }

        this.dialogConfig = DialogConfig.load(configPath, getLogger());
        this.welcomeDialog = WelcomeDialogFactory.create(this.dialogConfig, getLogger());
    }

    public void showWelcomeDialog(Player player) {
        if (this.welcomeDialog != null) {
            player.showDialog(this.welcomeDialog);
        }
    }

    public DialogConfig getDialogConfig() {
        return this.dialogConfig;
    }
}
