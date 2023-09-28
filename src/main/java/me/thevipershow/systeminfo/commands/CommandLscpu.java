package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class CommandLscpu implements CommandExecutor {

    private void printLscpu(CommandSender sender) {
        sender.sendMessage(Utils.color("&2«« &7Cpu info &2»»"));
        sender.sendMessage(Utils.color(String.format(" &7OS: &a%s %s %s", SystemValues.getOSFamily(), SystemValues.getOSManufacturer(), SystemValues.getOSVersion())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu vendor: &a%s", SystemValues.getCpuVendor())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu model: &a%s %s", SystemValues.getCpuModel(), SystemValues.getCpuModelName())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu clock speed: &a%s GHz", SystemValues.getCpuMaxFrequency())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu stepping: &a%s", SystemValues.getCpuStepping())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu c/t: &a%s/%s", SystemValues.getCpuCores(), SystemValues.getCpuThreads())));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("systeminfo.commands.lscpu")) {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            return true;
        }
        printLscpu(sender);
        return true;
    }

}
