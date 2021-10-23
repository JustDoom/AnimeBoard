package com.justdoom.animeboard.commands;

import com.justdoom.animeboard.AnimeBoard;
import com.justdoom.animeboard.AnimeScoreboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("animeboard") && sender.hasPermission("animeboard")) {

            if (args.length == 0) {
                sender.sendMessage("AnimeBoard\nAuthor: JustDoom\nHelp Command: /animeboard help");
            } else if (args[0].equalsIgnoreCase("reload")) {
                AnimeBoard.INSTANCE.reloadConfig();

                AnimeBoard.INSTANCE.getBoardHandler().clearScoreboards();

                for (Player p : AnimeBoard.INSTANCE.getServer().getOnlinePlayers()) {
                    AnimeScoreboard board = new AnimeScoreboard(p);
                    AnimeBoard.INSTANCE.getBoardHandler().addScoreboard(p, board);
                }

                sender.sendMessage("AnimeBoard Reloaded");
            }
        }
        return false;
    }
}
