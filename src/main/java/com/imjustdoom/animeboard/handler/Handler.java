package com.imjustdoom.animeboard.handler;

/**
 * This is the basic layout of how our scoreboard system works.
 * The scoreboard manager, the AnimeScoreboard class, functions primarily by calling these methods as appropriate.
 */
public interface Handler {

    /**
     * Create the scoreboard for the player.
     */
    void createBoard();

    /**
     * Remove the scoreboard for the player.
     */
    void removeBoard();

    /**
     * Update the scoreboard for the player.
     */
    void updateBoard();
}
