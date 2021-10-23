package com.justdoom.animeboard;

import com.justdoom.animeboard.util.MessageUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.*;

public class AnimeScoreboard {

    private final Player player;

    public AnimeScoreboard(Player player) {

        this.player = player;

        String title = AnimeBoard.INSTANCE.getConfig().getString("scoreboard.title");

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        title = MessageUtil.translate(title);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            title = PlaceholderAPI.setPlaceholders(player, title);
        }

        Objective obj = board.registerNewObjective("scoreboard", "dummy");
        obj.setDisplayName(title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int number = 1;
        List<String> content = new ArrayList<String>(AnimeBoard.INSTANCE.getConfig().getStringList("scoreboard.content"));

        List<String> colours = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "c", "e", "a", "b", "d", "f", "l", "o", "n", "m", "k", "r"));

        ListIterator<String> listIter = content.listIterator(content.size());
        while (listIter.hasPrevious()) {
            String key = listIter.previous();
            key = MessageUtil.translate(key);
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                key = PlaceholderAPI.setPlaceholders(player, key);
            }
            Random rand = new Random();
            String colour = colours.get(rand.nextInt(colours.size()));
            colours.remove(colour);
            Team score2 = board.registerNewTeam("team " + number);
            score2.addEntry("§" + colour + "§r");

            if (key.length() <= 16) {
                score2.setPrefix(key);
                obj.getScore("§" + colour + "§r").setScore(number);
                number = number + 1;
                continue;
            }

            if (key.length() > 32) {
                key = key.substring(0, 32);
            }

            score2.setPrefix(key.substring(0, 16));
            score2.setSuffix(key.substring(16));

            obj.getScore("§" + colour + "§r").setScore(number);
            number = number + 1;
        }

        this.player.setScoreboard(board);

        new BukkitRunnable() {

            public void run() {

                Scoreboard board2 = player.getScoreboard();

                int number2 = 1;

                String title = AnimeBoard.INSTANCE.getConfig().getString("scoreboard.title");
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    title = PlaceholderAPI.setPlaceholders(player, title);
                }
                title = MessageUtil.translate(title);
                obj.setDisplayName(title);
                List<String> content = new ArrayList<String>(AnimeBoard.INSTANCE.getConfig().getStringList("scoreboard.content"));

                ListIterator<String> listIter = content.listIterator(content.size());
                while (listIter.hasPrevious()) {
                    String key = listIter.previous();
                    key = MessageUtil.translate(key);
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        key = PlaceholderAPI.setPlaceholders(player, key);
                    }
                    key = key.replaceAll("%playername%", player.getName());
                    key = key.replaceAll("%playerdisplayname%", player.getDisplayName());
                    key = key.replaceAll("%maxplayers%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                    key = key.replaceAll("%players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
                    if (board2.getTeam("team " + number2) == null) {
                        cancel();
                    } else {

                        if (key.length() <= 16) {
                            board2.getTeam("team " + number2).setPrefix(key);
                            number2 = number2 + 1;
                            continue;
                        }

                        if (key.length() > 32) {
                            key = key.substring(0, 32);
                        }

                        board2.getTeam("team " + number2).setPrefix(key.substring(0, 16));
                        board2.getTeam("team " + number2).setSuffix(ChatColor.getLastColors(key.substring(0, 16)) + key.substring(16));
                    }
                    number2 = number2 + 1;
                }
            }
        }.runTaskTimer(AnimeBoard.INSTANCE, 0, 20);
    }
}