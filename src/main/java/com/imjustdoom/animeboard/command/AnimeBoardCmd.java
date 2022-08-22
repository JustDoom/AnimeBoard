package com.imjustdoom.animeboard.command;

import com.imjustdoom.cmdinstruction.Command;
import org.bukkit.command.CommandSender;

public class AnimeBoardCmd extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("AnimeBoard\nAuthor: JustDoom\nHelp Command: /animeboard help");
    }
}
