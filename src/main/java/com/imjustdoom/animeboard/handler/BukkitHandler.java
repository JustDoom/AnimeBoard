package com.imjustdoom.animeboard.handler;

import com.imjustdoom.animeboard.AnimeBoard;
import com.imjustdoom.animeboard.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class BukkitHandler implements Handler {

    private final Player player;

    private Objective objective;
    private Scoreboard scoreboard;

    public BukkitHandler(Player player) {
        this.player = player;
    }

    @Override
    public void createBoard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        // Despite IntelliJ warnings, this should not be null as long as we load post-world.
        scoreboard = manager.getNewScoreboard();

        String title = AnimeBoard.getPlugin().getConfig().getString("scoreboard.title");
        title = MessageUtil.translate(title);
        title = MessageUtil.setPlacerholders(player, title);

        /*
         * The scoreboard system was changed in modern versions. We avoid using the deprecated method as it may be
         * removed, but we default back to it if it causes an exception.
         */
        try {
            objective = scoreboard.registerNewObjective("AnimeBoard", Criteria.DUMMY, title);
        } catch (NoClassDefFoundError error) {
            objective = scoreboard.registerNewObjective("AnimeBoard", "dummy");
            objective.setDisplayName(title);
        }

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int number = 1;

        List<String> content = new ArrayList<>(AnimeBoard.getPlugin().getConfig().getStringList("scoreboard.content"));
        List<String> colours = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "c", "e", "a", "b", "d", "f", "l", "o", "n", "m", "k", "r"));
        ListIterator<String> listIter = content.listIterator(content.size());

        while (listIter.hasPrevious()) {
            String key = listIter.previous();
            key = MessageUtil.translate(key);
            key = MessageUtil.setPlacerholders(player, key);

            if (key.length() > 32) key = key.substring(0, 32);

            Random rand = new Random();
            String colour = colours.get(rand.nextInt(colours.size()));
            colours.remove(colour);
            Team score2 = scoreboard.registerNewTeam("team " + number);
            score2.addEntry("§" + colour + "§r");

            if (key.length() <= 16) {
                score2.setPrefix(key);
                objective.getScore("§" + colour + "§r").setScore(number);
                number = number + 1;
                continue;
            }

            score2.setPrefix(key.substring(0, 16));
            score2.setSuffix(key.substring(16));

            objective.getScore("§" + colour + "§r").setScore(number);
            number = number + 1;
        }

        player.setScoreboard(scoreboard);
    }

    @Override
    public void removeBoard() {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    @Override
    public void updateBoard() {
        int number2 = 1;

        String title = AnimeBoard.getPlugin().getConfig().getString("scoreboard.title");
        title = MessageUtil.translate(title);
        title = MessageUtil.setPlacerholders(player, title);

        objective.setDisplayName(title);

        List<String> content = new ArrayList<>(AnimeBoard.getPlugin().getConfig().getStringList("scoreboard.content"));
        ListIterator<String> listIter = content.listIterator(content.size());

        while (listIter.hasPrevious()) {
            String key = listIter.previous();
            key = MessageUtil.translate(key);
            key = MessageUtil.setPlacerholders(player, key);

            if (scoreboard.getTeam("team " + number2) == null) return;
            else {

                if (key.length() <= 16) {
                    scoreboard.getTeam("team " + number2).setPrefix(key);
                    number2 = number2 + 1;
                    continue;
                }

                if (key.length() > 32) key = key.substring(0, 32);

                scoreboard.getTeam("team " + number2).setPrefix(key.substring(0, 16));
                scoreboard.getTeam("team " + number2).setSuffix(ChatColor.getLastColors(key.substring(0, 16)) + key.substring(16));
            }
            number2 = number2 + 1;
        }
    }
}
