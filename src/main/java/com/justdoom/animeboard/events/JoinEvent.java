package com.justdoom.animeboard.events;

import com.justdoom.animeboard.AnimeBoard;
import com.justdoom.animeboard.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private final AnimeBoard plugin;

    public JoinEvent(AnimeBoard plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Scoreboard board = new Scoreboard(plugin, player);
        plugin.util.addScoreboard(player, board);
    }
}
