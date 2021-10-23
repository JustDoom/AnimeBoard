package com.justdoom.animeboard.handler;

import com.justdoom.animeboard.AnimeScoreboard;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BoardHandler {
    public static HashMap<Player, AnimeScoreboard> cachedScoreboards = new HashMap<>();

    public void addScoreboard(Player player, AnimeScoreboard mineData){
        cachedScoreboards.put(player, mineData);
    }

    public AnimeScoreboard getScoreboard(Player player){
        return cachedScoreboards.get(player);
    }

    public void removeScoreboard(Player player){
        cachedScoreboards.remove(player);
    }

    public void clearScoreboards(){
        cachedScoreboards.clear();
    }
}
