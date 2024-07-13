package com.imjustdoom.animeboard.listener;

import com.imjustdoom.animeboard.AnimeScoreboard;
import com.imjustdoom.animeboard.handler.BukkitHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AnimeScoreboard.addScoreboard(player, new BukkitHandler(player));
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        AnimeScoreboard.removeScoreboard(player);
    }
}
