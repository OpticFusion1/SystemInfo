package me.thevipershow.systeminfo;

import me.thevipershow.systeminfo.api.SysteminfoPlaceholder;
import me.thevipershow.systeminfo.gui.GuiClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.Locale;
import me.thevipershow.systeminfo.commands.CommandCpuLoad;
import me.thevipershow.systeminfo.commands.CommandDevices;
import me.thevipershow.systeminfo.commands.CommandDisks;
import me.thevipershow.systeminfo.commands.CommandHtop;
import me.thevipershow.systeminfo.commands.CommandLscpu;
import me.thevipershow.systeminfo.commands.CommandSensors;
import me.thevipershow.systeminfo.commands.CommandSystemInfo;
import me.thevipershow.systeminfo.commands.CommandUptime;
import me.thevipershow.systeminfo.commands.CommandVmstat;
import me.thevipershow.systeminfo.utils.I18n;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

public final class SystemInfo extends JavaPlugin {

    public static final LocalDateTime STARTUP_TIME = LocalDateTime.now();
    public static I18n I18N;
    public static final String PLUGIN_VERSION = "2";

    @Override
    public void onEnable() {
        saveResource("messages_en.properties", false);
        
        
        I18N = new I18n(this);
        I18N.updateLocale(Locale.getDefault().getLanguage());
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), this);

        registerCommand("systeminfo", new CommandSystemInfo(this));
        registerCommand("vmstat", new CommandVmstat());
        registerCommand("uptime", new CommandUptime());
        registerCommand("lscpu", new CommandLscpu());
        registerCommand("htop", new CommandHtop());
        registerCommand("disks", new CommandDisks());
        registerCommand("devices", new CommandDevices());
        registerCommand("cpuload", new CommandCpuLoad());
        registerCommand("sensors", new CommandSensors());
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().info("Could not find PlaceholderAPI, placeholders won't be available.");
            return;
        }

        new SysteminfoPlaceholder().register();
    }

    private void registerCommand(String name, CommandExecutor executor) {
        PluginCommand cmd = Bukkit.getPluginCommand(name);
        cmd.setExecutor(executor);
    }

}
