package com.justdoom.animeboard.util;

import com.justdoom.animeboard.Scoreboard;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Util {
    public static HashMap<Player, Scoreboard> cachedScoreboards = new HashMap<>();

    public void addScoreboard(Player player, Scoreboard mineData){
        cachedScoreboards.put(player, mineData);
    }

    public Scoreboard getScoreboard(Player player){
        return cachedScoreboards.get(player);
    }

    public void removeScoreboard(Player player){
        cachedScoreboards.remove(player);
    }

    public void clearScoreboards(){
        cachedScoreboards.clear();
    }
}
