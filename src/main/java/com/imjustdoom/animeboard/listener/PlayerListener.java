package com.imjustdoom.animeboard.listener;

import com.imjustdoom.animeboard.AnimeScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        AnimeScoreboard.addScoreboard(event.getPlayer());
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        AnimeScoreboard.removeScoreboard(event.getPlayer());
    }
}
