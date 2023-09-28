package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.oshi.SystemValues;
import static me.thevipershow.systeminfo.utils.I18n.tl;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class CommandSensors implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("systeminfo.commands.sensors")) {
            sender.sendMessage(tl("no-perms"));
            return true;
        }
        sender.sendMessage(Utils.color(String.format("&7Fans RPM: &a%s", SystemValues.getFansRPM())));
        sender.sendMessage(Utils.color(String.format("&7Cpu Voltage: &a%s", SystemValues.getCpuVoltage())));
        sender.sendMessage(Utils.color(String.format("&7Cpu Temperature: %s", SystemValues.getCpuTemperatureStatus())));
        return true;
    }

}
