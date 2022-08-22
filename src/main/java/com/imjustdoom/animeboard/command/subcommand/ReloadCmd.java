package com.imjustdoom.animeboard.command.subcommand;

import com.imjustdoom.animeboard.AnimeBoard;
import com.imjustdoom.animeboard.AnimeScoreboard;
import com.imjustdoom.cmdinstruction.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCmd extends SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        AnimeBoard.INSTANCE.reloadConfig();

        AnimeBoard.INSTANCE.getBoardHandler().clearScoreboards();

        for (Player p : AnimeBoard.INSTANCE.getServer().getOnlinePlayers()) {
            AnimeScoreboard board = new AnimeScoreboard(p);
            AnimeBoard.INSTANCE.getBoardHandler().addScoreboard(p, board);
        }

        sender.sendMessage("AnimeBoard Reloaded");
    }
}
