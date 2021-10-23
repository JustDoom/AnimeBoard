package com.justdoom.animeboard.events;

import com.justdoom.animeboard.AnimeBoard;
import com.justdoom.animeboard.AnimeScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        AnimeScoreboard board = new AnimeScoreboard(player);
        AnimeBoard.INSTANCE.getBoardHandler().addScoreboard(player, board);
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        AnimeBoard.INSTANCE.getBoardHandler().removeScoreboard(player);
    }
}
