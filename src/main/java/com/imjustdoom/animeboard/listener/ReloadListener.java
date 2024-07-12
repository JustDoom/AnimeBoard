package com.imjustdoom.animeboard.listener;

import better.reload.api.ReloadEvent;
import com.imjustdoom.animeboard.AnimeBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReloadListener implements Listener {

    @EventHandler
    public void onReload(ReloadEvent event) {
        AnimeBoard.INSTANCE.reload();
    }
}
