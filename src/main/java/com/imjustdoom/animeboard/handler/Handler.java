package com.imjustdoom.animeboard.handler;

import org.bukkit.entity.Player;

public abstract class Handler {

    protected final Player player;

    public Handler(Player player) {
        this.player = player;
    }

    public abstract void createBoard();

    public abstract void removeBoard();

    public abstract void updateBoard();
}
