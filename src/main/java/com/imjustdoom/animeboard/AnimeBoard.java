package com.imjustdoom.animeboard;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.imjustdoom.animeboard.command.AnimeBoardCmd;
import com.imjustdoom.animeboard.command.subcommand.ReloadCmd;
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

public final class AnimeBoard extends JavaPlugin {

    //bStats id. Don't touch.
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

        // Check for other soft dependencies.
        if (Bukkit.getPluginManager().getPlugin("BetterReload") != null) Bukkit.getPluginManager().registerEvents(new ReloadListener(), this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) MessageUtil.setPlacerHolderAPI(true);

        // Update all scoreboards every second.
        new BukkitRunnable() {
            public void run() {
                AnimeScoreboard.updateScoreboards();
            }
        }.runTaskTimer(this, 0, 20);

        reload();
    }

    /**
     * Reloads all configuration settings.
     */
    public static void reload() {
        AnimeBoard.plugin.saveDefaultConfig();
        AnimeBoard.plugin.reloadConfig();

        /*
         * Packets are supported if:
         * 1. PacketEvents is installed.
         * 2. The option "packet-based" is set to true (or doesn't exist).
         * 3. The server is 1.20.3 or later (due to changes in the Update Score packet).
         */
        if (Bukkit.getPluginManager().getPlugin("packetevents") != null) {
            AnimeScoreboard.setPacketEvents(true);

            if (!AnimeBoard.getPlugin().getConfig().getBoolean("packet-based", true))
                AnimeScoreboard.setPacketEvents(false);
            if (PacketEvents.getAPI().getServerManager().getVersion().isOlderThan(ServerVersion.V_1_20_3))
                AnimeScoreboard.setPacketEvents(false);
        } else AnimeScoreboard.setPacketEvents(false);

        AnimeScoreboard.clearScoreboards();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) AnimeScoreboard.addScoreboard(player);
    }
}