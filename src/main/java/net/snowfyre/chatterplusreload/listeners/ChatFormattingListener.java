package net.snowfyre.chatterplusreload.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static net.snowfyre.chatterplusreload.Utils.color;

public class ChatFormattingListener implements Listener {
    private final ChatterPlusReload plugin;

    public ChatFormattingListener(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void ChatFormatting(AsyncPlayerChatEvent e) {
        if (plugin.getConfig().getBoolean("enable-chat-formatting")) {
            String msg = e.getMessage();
            String format = plugin.getConfig().getString("chat-format");
            Player p = e.getPlayer();
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                e.setMessage(msg);
                e.setFormat(color(format).replace("%player_name%", p.getDisplayName()).replace("%message%", msg).replace("%", "%%"));
            } else {
                e.setFormat(color(format).replace("%player_name%", p.getDisplayName()).replace("%message%", msg));
            }
        }
    }
}
