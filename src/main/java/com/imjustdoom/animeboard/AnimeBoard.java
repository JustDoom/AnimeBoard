package com.imjustdoom.animeboard;

import com.imjustdoom.animeboard.command.AnimeBoardCmd;
import com.imjustdoom.animeboard.command.subcommand.ReloadCmd;
import com.imjustdoom.animeboard.listener.PlayerListener;
import com.imjustdoom.animeboard.metric.Metrics;
import com.imjustdoom.animeboard.handler.BoardHandler;
import com.imjustdoom.cmdinstruction.CMDInstruction;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class AnimeBoard extends JavaPlugin {

    public static AnimeBoard INSTANCE;
    private final BoardHandler boardHandler = new BoardHandler();

    public AnimeBoard() {
        INSTANCE = this;
    }

    private boolean placeholderAPI = false;

    @Override
    public void onEnable() {
        int pluginId = 9758;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> getConfig().getString("language", "en")));

        CMDInstruction.registerCommands(this, new AnimeBoardCmd().setName("animeboard").setPermission("animeboard")
                .setSubcommands(new ReloadCmd().setName("reload").setTabCompletions("reload")));

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) placeholderAPI = true;
    }
}