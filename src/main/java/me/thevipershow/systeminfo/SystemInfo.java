package me.thevipershow.systeminfo;

import me.thevipershow.systeminfo.api.SysteminfoPlaceholder;
import me.thevipershow.systeminfo.gui.GuiClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import me.thevipershow.systeminfo.commands.CommandCpuLoad;
import me.thevipershow.systeminfo.commands.CommandDevices;
import me.thevipershow.systeminfo.commands.CommandDisks;
import me.thevipershow.systeminfo.commands.CommandHtop;
import me.thevipershow.systeminfo.commands.CommandLscpu;
import me.thevipershow.systeminfo.commands.CommandSensors;
import me.thevipershow.systeminfo.commands.CommandSystemInfo;
import me.thevipershow.systeminfo.commands.CommandUptime;
import me.thevipershow.systeminfo.commands.CommandVmstat;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;

public final class SystemInfo extends JavaPlugin {

    private static final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();
    public static final String PLUGIN_VERSION = "2";
    public static Plugin instance;
    public static LocalDateTime time;
    public static Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = instance.getLogger();
        time = LocalDateTime.now();
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), instance);

        registerCommand("systeminfo", new CommandSystemInfo());
        registerCommand("vmstat", new CommandVmstat());
        registerCommand("uptime", new CommandUptime());
        registerCommand("lscpu", new CommandLscpu());
        registerCommand("htop", new CommandHtop());
        registerCommand("disks", new CommandDisks());
        registerCommand("devices", new CommandDevices());
        registerCommand("cpuload", new CommandCpuLoad());
        registerCommand("sensors", new CommandSensors());
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.info("Could not find PlaceholderAPI, placeholders won't be available.");
            return;
        }

        new SysteminfoPlaceholder().register();
    }

    private void registerCommand(String name, CommandExecutor executor) {
        PluginCommand cmd = Bukkit.getPluginCommand(name);
        cmd.setExecutor(executor);
    }

}
