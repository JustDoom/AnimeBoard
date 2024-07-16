package com.imjustdoom.animeboard.handler;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.score.ScoreFormat;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisplayScoreboard;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateScore;
import com.imjustdoom.animeboard.AnimeBoard;
import com.imjustdoom.animeboard.util.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This implementation of the Handler interface uses packets to perform scoreboard functions.
 * This handler requires PacketEvents to work.
 */
public class PacketHandler implements Handler {

    private final Player player;
    private final User user;

    public PacketHandler(Player player, User user) {
        this.player = player;
        this.user = user;
    }

    /**
     * Create the scoreboard for the player via packets.
     */
    @Override
    public void createBoard() {
        String title = AnimeBoard.getPlugin().getConfig().getString("scoreboard.title");
        title = MessageUtil.translate(title);
        title = MessageUtil.setPlacerholders(player, title);

        // This packet will register the objective AnimeBoard in the client.
        user.sendPacket(new WrapperPlayServerScoreboardObjective(
                "AnimeBoard",
                WrapperPlayServerScoreboardObjective.ObjectiveMode.CREATE,
                Component.text(title),
                null
        ));

        // This packet will tell the client to display the objective AnimeBoard as a scoreboard.
        user.sendPacket(new WrapperPlayServerDisplayScoreboard(1, "AnimeBoard"));

        List<String> content = new ArrayList<>(AnimeBoard.getPlugin().getConfig().getStringList("scoreboard.content"));

        for (int i = 0; i < content.size(); i++) {
            String key = content.get(i);
            key = MessageUtil.translate(key);
            key = MessageUtil.setPlacerholders(player, key);

            /*
             * This packet only contains the displayName and ScoreFormat field on 1.20.3+. The displayName field is
             * essential for operation and I don't feel like learning the team-based equivalent for prior versions, so
             * packet support will only be for 1.20.3+ for now.
             */
            user.sendPacket(new WrapperPlayServerUpdateScore(
                    String.valueOf(content.size() - i),
                    WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM,
                    "AnimeBoard",
                    content.size() - i,
                    Component.text(key),
                    ScoreFormat.fixedScore(Component.text(""))
            ));
        }
    }

    /**
     * Remove the scoreboard for the player via packets.
     */
    @Override
    public void removeBoard() {
        /*
         * This packet removes the objective AnimeBoard from being registered in the client.
         * If the same objective is attempted to be registered twice, as would happen during a reload if this function
         * is not called, the client will disconnect from the server due to incorrect networking.
         */
        user.sendPacket(new WrapperPlayServerScoreboardObjective(
                "AnimeBoard", WrapperPlayServerScoreboardObjective.ObjectiveMode.REMOVE, Component.empty(), null
        ));
    }

    /**
     * Update the scoreboard for the player via packets.
     */
    @Override
    public void updateBoard() {
        String title = AnimeBoard.getPlugin().getConfig().getString("scoreboard.title");
        title = MessageUtil.translate(title);
        title = MessageUtil.setPlacerholders(player, title);

        // This packet will update the title text of the AnimeBoard objective.
        user.sendPacket(new WrapperPlayServerScoreboardObjective(
                "AnimeBoard",
                WrapperPlayServerScoreboardObjective.ObjectiveMode.UPDATE,
                Component.text(title),
                null
        ));

        List<String> content = new ArrayList<>(AnimeBoard.getPlugin().getConfig().getStringList("scoreboard.content"));

        for (int i = 0; i < content.size(); i++) {
            String key = content.get(i);
            key = MessageUtil.translate(key);
            key = MessageUtil.setPlacerholders(player, key);

            user.sendPacket(new WrapperPlayServerUpdateScore(
                    String.valueOf(content.size() - i),
                    WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM,
                    "AnimeBoard",
                    content.size() - i,
                    Component.text(key),
                    ScoreFormat.fixedScore(Component.text(""))
            ));
        }
    }
}
