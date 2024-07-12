package com.imjustdoom.animeboard.command.subcommand;

import com.imjustdoom.animeboard.AnimeBoard;
import com.imjustdoom.animeboard.AnimeScoreboard;
import com.imjustdoom.cmdinstruction.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCmd extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnimeBoard.INSTANCE.reload();

        sender.sendMessage("AnimeBoard Reloaded");
    }
}
