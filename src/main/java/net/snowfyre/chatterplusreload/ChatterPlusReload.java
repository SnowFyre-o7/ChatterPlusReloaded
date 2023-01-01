package net.snowfyre.chatterplusreload;

import net.snowfyre.chatterplusreload.TabCompleters.CPlusTabCompletion;
import net.snowfyre.chatterplusreload.commands.*;
import net.snowfyre.chatterplusreload.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatterPlusReload extends JavaPlugin implements CommandExecutor, Listener {
    public static ChatterPlusReload instance;

    public static ChatterPlusReload get() {
        return instance;
    }

    public static String prefix = ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "CPlusReloaded" + ChatColor.GRAY + "" + ChatColor.BOLD + "] ";
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getLogger().info("ChatterPlusReloaded starting with " + Bukkit.getBukkitVersion() + " and " + Bukkit.getVersion());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("PlaceholderAPI not detected, this might end up with errors!");
        }

        instance = this;

        getCommand("chatclear").setExecutor(new ClearChatCMD(this));
        getCommand("cplus").setExecutor(new CPlusCMD(this));
        getCommand("sudoall").setExecutor(new SudoAllCMD(this));
        getCommand("sudo").setExecutor(new SudoCMD(this));
        getCommand("broadcast").setExecutor(new BroadcastCMD(this));
        getCommand("staffchat").setExecutor(new StaffChatCMD(this));
        getCommand("mention").setExecutor(new MentionCMD(this));
        getCommand("lockchat").setExecutor(new LockCMD(this));
        getCommand("unlockchat").setExecutor(new UnlockCMD(this));
        getCommand("cplus").setTabCompleter(new CPlusTabCompletion());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new AntiBadwordListener(this), this);
        pm.registerEvents(new ChatCooldownListener(this), this);
        pm.registerEvents(new AntyLinkListener(this), this);
        pm.registerEvents(new ChatFormattingListener(this), this);
        pm.registerEvents(new PingListener(this), this);
        pm.registerEvents(this, this);
        pm.registerEvents(new ChatLockListener(this), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ChatterPlusReloaded has ended it's job with " + Bukkit.getBukkitVersion() + " and " + Bukkit.getVersion());
        instance = null;
    }
}