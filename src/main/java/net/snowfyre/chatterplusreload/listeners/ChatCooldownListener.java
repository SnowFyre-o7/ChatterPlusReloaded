package net.snowfyre.chatterplusreload.listeners;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

import static net.snowfyre.chatterplusreload.Utils.color;

public class ChatCooldownListener implements Listener {
    private final ChatterPlusReload plugin;

    public ChatCooldownListener(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("cplusreloaded.chat-cooldown-bypass")) {
            if (!cooldown.containsKey(p.getUniqueId())) {
                cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                return;
            }

            long timeElapsed = System.currentTimeMillis() - cooldown.get(p.getUniqueId());

            if (timeElapsed >= plugin.getConfig().getInt("chat-cooldown")) {
                cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                return;
            }

            long remaining = plugin.getConfig().getInt("chat-cooldown");


            e.setCancelled(true);
            remaining = remaining - timeElapsed;
            p.sendMessage(color(plugin.getConfig().getString("messages.chat-cooldown-reached").replace("%seconds%", Integer.toString(Math.round(remaining / 1000)))));
        } else {

        }

    }
}
