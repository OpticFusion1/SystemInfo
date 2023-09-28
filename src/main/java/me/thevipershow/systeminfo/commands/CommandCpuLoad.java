package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.oshi.SystemValues;
import static me.thevipershow.systeminfo.utils.I18n.tl;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import oshi.util.Util;

public final class CommandCpuLoad implements CommandExecutor {

    private long[] previousTicks;
    private long[][] previousMultiTicks;

    private double getCpuLoad() {
        previousTicks = SystemValues.getSystemCpuLoadTicks();
        previousMultiTicks = SystemValues.getProcessorCpuLoadTicks();
        Util.sleep(1000);
        return SystemValues.getSystemCpuLoadBetweenTicks(previousTicks) * 100;
    }

    private String getAverageLoads() {
        StringBuilder cpuLoads = new StringBuilder("&7Load per core:&a");
        double[] load = SystemValues.getProcessorCpuLoadBetweenTicks(previousMultiTicks);
        for (double average : load) {
            cpuLoads.append(String.format(" %.1f%%", average * 100));
        }
        return cpuLoads.toString();
    }

    private void printCpuLoad(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7System load: &2«"));
        sender.sendMessage(Utils.color(String.format("&7Cpu load: &a%.2f%%", getCpuLoad())));
        sender.sendMessage(Utils.color(getAverageLoads()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("systeminfo.commands.cpuload")) {
            sender.sendMessage(tl("not-enough-args"));
            return true;
        }
        printCpuLoad(sender);
        return true;
    }

}
