package com.imjustdoom.animeboard.command.subcommand;

import com.imjustdoom.animeboard.AnimeBoard;
import com.imjustdoom.cmdinstruction.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadCmd extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnimeBoard.reload();
        sender.sendMessage("AnimeBoard Reloaded");
    }
}
