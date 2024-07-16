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
import java.util.ListIterator;

public class PacketHandler implements Handler {

    private final Player player;
    private final User user;

    public PacketHandler(Player player, User user) {
        this.player = player;
        this.user = user;
    }

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

        int i = 1;
        List<String> content = new ArrayList<>(AnimeBoard.getPlugin().getConfig().getStringList("scoreboard.content"));
        ListIterator<String> listIter = content.listIterator(content.size());

        while (listIter.hasPrevious()) {
            String key = listIter.previous();
            key = MessageUtil.translate(key);
            key = MessageUtil.setPlacerholders(player, key);

            user.sendPacket(new WrapperPlayServerUpdateScore(
                    key,
                    WrapperPlayServerUpdateScore.Action.CREATE_OR_UPDATE_ITEM,
                    "AnimeBoard",
                    i,
                    Component.text(key),
                    ScoreFormat.fixedScore(Component.text(""))
            ));

            i++;
        }
    }

    @Override
    public void removeBoard() {
        // This packet removes the objective AnimeBoard from being registered in the client.
        user.sendPacket(new WrapperPlayServerScoreboardObjective(
                "AnimeBoard", WrapperPlayServerScoreboardObjective.ObjectiveMode.REMOVE, Component.empty(), null
        ));
    }

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
                    "AnimeBoard", content.size() - i,
                    Component.text(key),
                    ScoreFormat.fixedScore(Component.text(""))
            ));
        }
    }
}
