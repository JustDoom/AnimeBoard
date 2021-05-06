package com.justdoom.animeboard.commands;

import com.justdoom.animeboard.AnimeBoard;
import com.justdoom.animeboard.Scoreboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCommands implements CommandExecutor {
    private final AnimeBoard plugin;

    public ScoreboardCommands(AnimeBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("animeboard") && sender.hasPermission("animeboard")) {

            if(args.length == 0){
                sender.sendMessage("AnimeBoard\nAuthor: JustDoom\nHelp Command: /animeboard help");
            }else if(args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();

                plugin.util.clearScoreboards();

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    com.justdoom.animeboard.Scoreboard board = new Scoreboard(plugin, p);
                    plugin.util.addScoreboard(p, board);
                }

                sender.sendMessage("AnimeBoard Reloaded");
            }
        }
        return false;
    }
}
