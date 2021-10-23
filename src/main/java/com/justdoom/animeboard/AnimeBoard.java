package com.justdoom.animeboard;

import com.justdoom.animeboard.commands.ScoreboardCommands;
import com.justdoom.animeboard.events.PlayerListener;
import com.justdoom.animeboard.events.tabcomplete.AnimeBoardTabCompletion;
import com.justdoom.animeboard.metrics.Metrics;
import com.justdoom.animeboard.handler.BoardHandler;
import com.justdoom.animeboard.util.MessageUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

@Getter
public final class AnimeBoard extends JavaPlugin {

    public static AnimeBoard INSTANCE;
    private BoardHandler boardHandler = new BoardHandler();

    public AnimeBoard() {
        INSTANCE = this;
    }

    private boolean placeholderAPI = false;

    @Override
    public void onEnable() {
        int pluginId = 9758;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> getConfig().getString("language", "en")));

        this.getCommand("animeboard").setExecutor(new ScoreboardCommands());
        this.getCommand("animeboard").setTabCompleter(new AnimeBoardTabCompletion());

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) placeholderAPI = true;
    }
}