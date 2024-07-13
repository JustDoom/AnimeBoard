package com.imjustdoom.animeboard;

import com.imjustdoom.animeboard.handler.Handler;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AnimeScoreboard {

    public static HashMap<Player, Handler> cachedScoreboards = new HashMap<>();

    public static void addScoreboard(Player player, Handler handler){
        cachedScoreboards.put(player, handler);
        handler.createBoard();
    }

    public static void clearScoreboards(){
        cachedScoreboards.clear();
    }

    public static Handler getScoreboard(Player player){
        return cachedScoreboards.get(player);
    }

    public static void removeScoreboard(Player player){
        cachedScoreboards.remove(player);
    }

    public static void updateScoreboards() {
        for (Handler handler : cachedScoreboards.values()) handler.updateBoard();
    }
}