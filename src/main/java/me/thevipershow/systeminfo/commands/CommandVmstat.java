package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.oshi.SystemValues;
import static me.thevipershow.systeminfo.utils.I18n.tl;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class CommandVmstat implements CommandExecutor {

    private void vmstat(CommandSender sender) {
        sender.sendMessage(Utils.color("&2«« &7Memory info &2»»"));
        sender.sendMessage(String.format(Utils.color("&7Available memory: &a%s"), SystemValues.getAvailableMemory()));
        sender.sendMessage(String.format(Utils.color("&7Allocated memory: &a%s"), SystemValues.getUsedMemory()));
        sender.sendMessage(String.format(Utils.color("&7Total memory: &a%s"), SystemValues.getMaxMemory()));
        sender.sendMessage(String.format(Utils.color("&7Swap total memory: &a%s"), SystemValues.getTotalSwap()));
        sender.sendMessage(Utils.color(String.format("&7Swap used memory: &a%s", SystemValues.getUsedSwap())));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(tl("not-enough-args"));
            return true;
        }
        if (!sender.hasPermission("systeminfo.commands.vmstat")) {
            sender.sendMessage(tl("no-perms"));
            return true;
        }
        vmstat(sender);
        return true;
    }

}
