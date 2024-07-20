package com.imjustdoom.animeboard.util;

import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides a few basic utilities for converting messages to client-usable versions.
 */
public class MessageUtil {

    /**
     * This is a static method class, it should not be possible to initialize.
     */
    private MessageUtil() {}

    @Setter
    private static boolean placerHolderAPI = false;

    public final static Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]){6}");
    public final static Pattern HEX_PATTERN_2 = Pattern.compile("#([A-Fa-f0-9]){6}");

    /**
     * Convert all placeholders, including both PAPI and built-in placeholders.
     */
    public static String setPlacerholders(Player player, String text) {
        text = text.replaceAll("%playername%", player.getName())
                .replaceAll("%playerdisplayname%", player.getDisplayName())
                .replaceAll("%maxplayers%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                .replaceAll("%players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
        if (!placerHolderAPI) return text;
        return PlaceholderAPI.setPlaceholders(player, text);
    }

    /**
     * Translate color codes to client-usable versions.
     */
    public static String translate(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);

        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("&#", "x");

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = HEX_PATTERN.matcher(message);
        }

        matcher = HEX_PATTERN_2.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = HEX_PATTERN_2.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
