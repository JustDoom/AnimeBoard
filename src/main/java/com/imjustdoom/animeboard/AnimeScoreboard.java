package com.imjustdoom.animeboard;

import com.github.retrooper.packetevents.PacketEvents;
import com.imjustdoom.animeboard.handler.BukkitHandler;
import com.imjustdoom.animeboard.handler.Handler;
import com.imjustdoom.animeboard.handler.PacketHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * This class functions as a manager for all instances of Handlers.
 * This class will keep track of who they're attached to and call functions within them.
 */
public class AnimeScoreboard {

    /**
     * This is a static method class, it should not be possible to initialize.
     */
    private AnimeScoreboard() {}

    @Getter
    @Setter
    private static boolean packetEvents;

    public static HashMap<Player, Handler> cachedScoreboards = new HashMap<>();

    /**
     * Create a scoreboard handler for the given player and add them to the cache.
     */
    public static void addScoreboard(Player player) {
        Handler handler;

        if (packetEvents) handler = new PacketHandler(player, PacketEvents.getAPI().getPlayerManager().getUser(player));
        else handler = new BukkitHandler(player);

        cachedScoreboards.put(player, handler);
        handler.createBoard();
    }

    /**
     * Reset and clear all scoreboards.
     */
    public static void clearScoreboards() {
        for (Handler handler : cachedScoreboards.values()) handler.removeBoard();
        cachedScoreboards.clear();
    }

    /**
     * Return the given player's scoreboard Handler.
     */
    public static Handler getScoreboard(Player player) {
        return cachedScoreboards.get(player);
    }

    /**
     * Remove the given player's scoreboard Handler from the cache.
     */
    public static void removeScoreboard(Player player) {
        Handler handler = cachedScoreboards.remove(player);
        if (handler != null) handler.removeBoard();
    }

    /**
     * Update all scoreboards.
     */
    public static void updateScoreboards() {
        for (Handler handler : cachedScoreboards.values()) handler.updateBoard();
    }
}