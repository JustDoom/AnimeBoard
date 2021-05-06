package com.justdoom.animeboard;

import com.justdoom.animeboard.commands.ScoreboardCommands;
import com.justdoom.animeboard.events.JoinEvent;
import com.justdoom.animeboard.events.QuitEvent;
import com.justdoom.animeboard.events.tabcomplete.AnimeBoardTabCompletion;
import com.justdoom.animeboard.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public final class AnimeBoard extends JavaPlugin {

    public MessageHandler msgHandler = new MessageHandler();
    public Util util = new Util();

    @Override
    public void onEnable() {
        int pluginId = 9758;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new Metrics.SimplePie("used_language", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getConfig().getString("language", "en");
            }
        }));

        this.getCommand("animeboard").setExecutor(new ScoreboardCommands(this));
        this.getCommand("animeboard").setTabCompleter(new AnimeBoardTabCompletion());

        Bukkit.getPluginManager().registerEvents(new JoinEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(this), this);

        saveDefaultConfig();
    }
}