package com.spacesmp.welcomedialog.listener;

import com.spacesmp.welcomedialog.WelcomeDialogPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {

    private final WelcomeDialogPlugin plugin;

    public PlayerJoinListener(WelcomeDialogPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.plugin.showWelcomeDialog(event.getPlayer());
    }
}
