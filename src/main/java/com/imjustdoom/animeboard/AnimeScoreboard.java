package com.imjustdoom.animeboard;

import com.github.retrooper.packetevents.PacketEvents;
import com.imjustdoom.animeboard.handler.BukkitHandler;
import com.imjustdoom.animeboard.handler.Handler;
import com.imjustdoom.animeboard.handler.PacketHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AnimeScoreboard {

    @Getter
    @Setter
    private static boolean packetEvents;

    public static HashMap<Player, Handler> cachedScoreboards = new HashMap<>();

    public static void addScoreboard(Player player) {
        Handler handler;

        if (packetEvents) handler = new PacketHandler(player, PacketEvents.getAPI().getPlayerManager().getUser(player));
        else handler = new BukkitHandler(player);

        cachedScoreboards.put(player, handler);
        handler.createBoard();
    }

    public static void clearScoreboards() {
        for (Handler handler : cachedScoreboards.values()) handler.removeBoard();
        cachedScoreboards.clear();
    }

    public static Handler getScoreboard(Player player) {
        return cachedScoreboards.get(player);
    }

    public static void removeScoreboard(Player player) {
        Handler handler = cachedScoreboards.remove(player);
        if (handler != null) handler.removeBoard();
    }

    public static void updateScoreboards() {
        for (Handler handler : cachedScoreboards.values()) handler.updateBoard();
    }
}