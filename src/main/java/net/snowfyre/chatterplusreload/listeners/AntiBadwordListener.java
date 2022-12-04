package net.snowfyre.chatterplusreload.listeners;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class AntiBadwordListener implements Listener {
    private final ChatterPlusReload plugin;

    public AntiBadwordListener(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage().toLowerCase();
        List<String> badWords = plugin.getConfig().getStringList("badwords");

        for (String word : badWords) {
            message = message.replace(" ", "");
            if (message.contains(word)) {
                if (e.getPlayer().hasPermission("cplusreloaded.anti-badword-bypass")) return;
                e.setCancelled(true);
                e.getPlayer().sendMessage(plugin.getConfig().getString("messages.contains-badword"));

            }
        }
    }

}
