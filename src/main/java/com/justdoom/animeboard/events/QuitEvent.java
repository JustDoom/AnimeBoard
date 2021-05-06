package com.justdoom.animeboard.events;

import com.justdoom.animeboard.AnimeBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    private final AnimeBoard plugin;

    public QuitEvent(AnimeBoard plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.util.removeScoreboard(player);
    }
}
