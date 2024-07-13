package com.imjustdoom.animeboard;

import com.imjustdoom.animeboard.command.AnimeBoardCmd;
import com.imjustdoom.animeboard.command.subcommand.ReloadCmd;
import com.imjustdoom.animeboard.handler.BukkitHandler;
import com.imjustdoom.animeboard.listener.PlayerListener;
import com.imjustdoom.animeboard.listener.ReloadListener;
import com.imjustdoom.animeboard.metric.Metrics;
import com.imjustdoom.animeboard.util.MessageUtil;
import com.imjustdoom.cmdinstruction.CMDInstruction;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public final class AnimeBoard extends JavaPlugin {

    private static final int PLUGIN_ID = 9758;

    @Getter
    private static AnimeBoard plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // bStats
        Metrics metrics = new Metrics(this, PLUGIN_ID);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> getConfig().getString("language", "en")));

        // Register required events and commands.
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        CMDInstruction.registerCommands(this, new AnimeBoardCmd().setName("animeboard").setPermission("animeboard")
                .setTabCompletions("reload").setSubcommands(new ReloadCmd().setName("reload").setTabCompletions("").setPermission("animeboard")));

        // Check for soft dependencies.
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) MessageUtil.setPlacerHolderAPI(true);
        if (Bukkit.getPluginManager().getPlugin("BetterReload") != null) Bukkit.getPluginManager().registerEvents(new ReloadListener(), this);

        // Update the scoreboards every second.
        new BukkitRunnable() {
            public void run() {
                AnimeScoreboard.updateScoreboards();
            }
        }.runTaskTimer(this, 0, 20);

        saveDefaultConfig();
    }

    public static void reload() {
        AnimeBoard.plugin.saveDefaultConfig();
        AnimeBoard.plugin.reloadConfig();
        AnimeScoreboard.clearScoreboards();

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            AnimeScoreboard.addScoreboard(p, new BukkitHandler(p));
        }
    }
}