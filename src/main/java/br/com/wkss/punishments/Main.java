package package br.com.wkss.punishments;

import br.com.wkss.punishments.command.PunishCommand;
import br.com.wkss.punishments.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    private static Plugin plugin;
    public static Plugin getPlugin() {
        return plugin;
    }

    private Manager manager;
    public Manager getManager() {
        return manager;
    }

    @Override
    public void onLoad() {
        return;
    }

    @Override
    public void onEnable() {
        getLogger().info(Level.ALL + "plugin started successfully.");

        for(Player players : Bukkit.getServer().getOnlinePlayers()) {
            players.kickPlayer(ChatColor.RED + "Este servidor está sendo reinicido.");
        }
        instance = this; plugin = this;
        manager = new Manager(false);

        getCommand("punir").setExecutor(new PunishCommand());
    }
    @Override
    public void onDisable() {
        getLogger().info(Level.ALL + "Plugin disable successfully.");
        manager = null;
        plugin = null; instance = null;
        return;
    }

}
