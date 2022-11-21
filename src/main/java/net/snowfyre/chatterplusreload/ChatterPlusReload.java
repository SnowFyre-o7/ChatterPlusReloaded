package net.snowfyre.chatterplusreload;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.libs.kyori.adventure.platform.facet.Facet;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static net.snowfyre.chatterplusreload.Utils.color;

public final class ChatterPlusReload extends JavaPlugin implements CommandExecutor, Listener {

    public static HashMap<Player, Boolean> enabled = new HashMap<Player, Boolean>();
    boolean isChatLocked = false;
    public static ChatterPlusReload instance;
    public static ChatterPlusReload get() { return instance; }
    public static String prefix = ChatColor.DARK_PURPLE + "[" + ChatColor.GREEN + "ChatterPlusReloaded" + ChatColor.DARK_PURPLE + "] ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getLogger().info("ChatterPlusReloaded starting with " + Bukkit.getBukkitVersion() + " and " + Bukkit.getVersion());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("PlaceholderAPI not detected, this might end up with errors!");
        }
        getServer().getPluginManager().registerEvents(this, this);

        reload();
        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ChatterPlusReloaded has ended it's job with " + Bukkit.getBukkitVersion() + " and " + Bukkit.getVersion());
        instance = null;
    }


    @EventHandler
    public void UpdateChecker(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            if (p.hasPermission("cplusreloaded.check-updates{")) {
                HttpURLConnection con = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=106347").openConnection();
                con.setRequestMethod("GET");
                String onlineVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                boolean availableUpdate = !onlineVersion.equals(getDescription().getVersion());

                if (availableUpdate) {
                    p.sendMessage(ChatColor.GREEN + "New version of ChatterPlus available: " + onlineVersion);
                }
            }
        } catch (MalformedURLException exception) {

        } catch (IOException exception) {

        }
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("chatclear")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("cplusreloaded.chatclear")) {
                    for (int i = 0; i < 100; ++i) {
                        for (Player people : Bukkit.getOnlinePlayers()) {
                            people.sendMessage(ChatColor.BLUE + "");

                        }
                    }
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.chat-cleared-global").replace("%player%", p.getDisplayName())));
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    }
                } else {
                    p.sendMessage(color(getConfig().getString("messages.noperms")));
                }
            } else {
                System.out.println("Sorry, but you can only execute /chatclear as a player.");
            }
        } else if (command.getName().equalsIgnoreCase("cplus")) {
            Player p = (Player) sender;

            if (args.length == 0) {
                if (p.hasPermission("cplusreloaded.view")) {
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/cplus" + ChatColor.GRAY + " - Shows the list of commands");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/cplus reload" + ChatColor.GRAY + " - Reloads the config");
                } else {
                    p.sendMessage(color(getConfig().getString("messages.noperms")));
                }

            } else if (args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("cplusreloaded.reload")) {
                    reload();
                    p.sendMessage(ChatColor.GREEN + "Reloaded the config of ChatterPlus");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                } else {
                    p.sendMessage(color(getConfig().getString("messages.noperms")));
                }

            } else {
                p.sendMessage(ChatColor.RED + "Please enter a valid command!");
            }

        } else if (command.getName().equalsIgnoreCase("sudoall")) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.sudoall")) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }

                    String message = sb.toString().trim();
                    target.chat(message);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                }
            } else {

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noperms")));

            }

        } else if (command.getName().equalsIgnoreCase("sudo")) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.sudo")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.sudo-less-args")));
                } else if (args.length == 1) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.sudo-less-args")));
                } else {
                    Player target = getServer().getPlayer(args[0]);
                    if (target != null && target.isOnline()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }
                        String message = sb.toString().trim();
                        target.chat(message);
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.sudo-player-offline")));
                    }
                }


            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noperms")));
            }
        } else if (command.getName().equalsIgnoreCase("broadcast")) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.broadcast")) {
                if (args.length == 0) {
                    p.sendMessage(color("broadcast-less-args"));
                } else if (args.length >= 1) {

                    for (Player target : Bukkit.getOnlinePlayers()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }

                        String message = sb.toString().trim();
                        target.sendMessage(color(getConfig().getString("broadcast-format").replace("%broadcast_msg%", message)));
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    }

                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noperms")));
            }

        } else if(command.getName().equalsIgnoreCase("staffchat")) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.staffchat")) {
                if (args.length >= 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.hasPermission(getConfig().getString("staff-chat-permission"))) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                sb.append(args[i]).append(" ");
                            }

                            String message = sb.toString().trim();

                            all.sendMessage(color(getConfig().getString("staff-chat-format")).replace("%message%", message).replace("%player%", p.getDisplayName()));
                        }
                    }
                } else {
                    p.sendMessage(color(getConfig().getString("staffchat-less-args")));
                }
            } else {
                p.sendMessage(color(getConfig().getString("noperms")));
            }
        }
        if (command.getName().equalsIgnoreCase("mention")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (args.length == 0) {

                    if (player.hasPermission("cplusreloaded.mention")) {

                        togglePluginState(player);

                    } else {
                        player.sendMessage(color(getConfig().getString("messages.noperms")));
                    }

                }
                }
            }


        return true;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void LockChecker(AsyncPlayerChatEvent e) {
        if (isChatLocked == true) {
            Player p = e.getPlayer();


            if (!p.hasPermission("cplusreloaded.bypasschatlock")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.message-during-lock")));
                e.setCancelled(true);
            }
        }
    }

    List<String> badwords = null;

    @EventHandler(priority = EventPriority.LOWEST)
    public void BadWordDetect(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("cplusreloaded.anti-badword-bypass")) {
            for (String s : badwords) {
                if (e.getMessage().contains(s)) {
                    e.setCancelled(true);

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.contains-badword")));
                }
            }
        } else {

        }


    }

    public void reload() {
        reloadConfig();
        badwords = getConfig().getStringList("badwords");
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

            if (timeElapsed >= getConfig().getInt("chat-cooldown")) {
                cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                return;
            }

            long remaining = getConfig().getInt("chat-cooldown");


            e.setCancelled(true);
            remaining = remaining - timeElapsed;
            p.sendMessage(color(getConfig().getString("messages.chat-cooldown-reached").replace("%seconds%", Integer.toString(Math.round(remaining / 1000)))));
        } else {

        }

    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void Antylink(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        Player p = e.getPlayer();
        if (message.contains("https://") || message.contains(".org") || message.contains(".com") || message.contains(".net") || message.contains(".pl") || message.contains(".tk") || message.contains(".ml") || message.contains(".eu") || message.contains(".xyz") || message.contains(".party") || message.contains(".us") || message.contains(".de") || message.contains(".biz") || message.contains(".art")) {
            if (!p.hasPermission("cplusreloaded.bypassantylink")) {
                if (getConfig().getBoolean("antylink-remove-dots")) {

                    e.setMessage(message.replace(".", " "));

                } else if (getConfig().getBoolean("antylink-cancel-send-if-link-detected")) {
                    e.setCancelled(true);

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.contains-link")));
                }
            }

        }
    }

    @EventHandler
    public void ChatFormatting(AsyncPlayerChatEvent e) {
        if (getConfig().getBoolean("enable-chat-formatting")) {
            String msg = e.getMessage();
            String format = getConfig().getString("chat-format");
            Player p = e.getPlayer();
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                e.setMessage(msg);
                e.setFormat(color(format).replace("%player%", p.getDisplayName()).replace("%message%", msg).replace("%", "%%"));
            } else {
                e.setFormat(color(format).replace("%player%", p.getDisplayName()).replace("%message%", msg));
            }
        }
    }
    public void togglePluginState(Player player) {

        if (enabled.containsKey(player)) {
            if (enabled.get(player)) {
                enabled.put(player, false);
                player.sendMessage(prefix + ChatColor.RED + "Pings disabled.");

            } else {

                enabled.put(player, true);
                player.sendMessage(prefix + ChatColor.GREEN + "Pings enabled.");

            }

        } else {
            enabled.put(player, false); // If you want plugin enabled by default change this value to false.
            player.sendMessage(prefix + ChatColor.RED + "Pings disabled.");
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        for (Player pinged : Bukkit.getServer().getOnlinePlayers()) {

            if (event.getMessage().contains("@" + pinged.getName())) {
                if (enabled.get(player)) {
                    if (pinged.hasPermission("cplusreloaded.mention")) {

                        String msg = event.getMessage().replace("@" + pinged.getName(), ChatColor.GOLD + pinged.getName() + ChatColor.RESET);
                        event.setMessage(msg);

                    }
                    pinged.getWorld().playSound(pinged.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 3.0F, 0.5F);
                    pinged.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "✯ You have been pinged by " + player.getDisplayName() + " ✯"));

                    return;
                }
            }
        }
    }

}
