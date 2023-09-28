package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static me.thevipershow.systeminfo.SystemInfo.STARTUP_TIME;
import static me.thevipershow.systeminfo.utils.I18n.tl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

public final class CommandUptime implements CommandExecutor {

    private void uptime(CommandSender sender) {
        final long uptime = ChronoUnit.MINUTES.between(STARTUP_TIME, LocalDateTime.now());
        sender.sendMessage(Utils.color("&2»» &7Machine uptime &2««"));
        sender.sendMessage(String.format(Utils.color("&2» &7JVM Uptime: &a%s min."), uptime));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(tl("not-enough-args"));
            return true;
        }
        if (!sender.hasPermission("systeminfo.commands.uptime")) {
            sender.sendMessage(tl("no-perms"));
            return true;
        }
        uptime(sender);
        return true;
    }

}
