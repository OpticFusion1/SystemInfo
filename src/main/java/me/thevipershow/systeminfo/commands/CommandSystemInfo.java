package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.gui.SystemInfoGui;
import static me.thevipershow.systeminfo.utils.I18n.tl;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandSystemInfo implements CommandExecutor {

    private SystemInfo systemInfo;

    public CommandSystemInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    private void systemInfo1(CommandSender sender) {
        sender.sendMessage(Utils.color("&7&m&l--------------------------------------"));
        sender.spigot().sendMessage(Utils.builderHover("&7»» &fSystemInfo Help &7««", "This is the help page."));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/lscpu &aget processor info! &8[*]", "this returns processor info"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/vmstat &aget memory info! &8[*]", "this gets memory usage\nof the entire host"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/cputemp &aget sensors info! &8[*]", "gets various info from sensors"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/disks &aget disks info! &8[*]", "prints out a map of disks."));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/benchmark &aget CPU-benchmark score! &8[*]", "Makes a CPU benchmark and gives a score!"));
        sender.spigot().sendMessage(Utils.builderClick("&7»» Click here for second page &f[*]", "/systeminfo 2"));
        sender.sendMessage(Utils.color("&7&m&l--------------------------------------"));
    }

    private void systemInfo2(CommandSender sender) {
        sender.sendMessage(Utils.color("&7&l&m---------------------------------"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/htop &aget processes list! &8[*]", "get a list of processes"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/systeminfo <arg> &amain command &8[*]", "args = stats, version, easteregg"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/uptime &aget the machine uptime! &8[*]", "get the total uptime of machine"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/devices &aget devices list! &8[*]", "get every attached device"));
        sender.sendMessage(Utils.color("&7&l&m---------------------------------"));
    }

    private void stats(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7Server stats &2«"));
        sender.sendMessage(String.format(Utils.color("&2» &7Overworld Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.NORMAL), Utils.loadedChunksInWorlds(World.Environment.NORMAL)));
        sender.sendMessage(String.format(Utils.color("&2» &7Nether Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.NETHER), Utils.loadedChunksInWorlds(World.Environment.NETHER)));
        sender.sendMessage(String.format(Utils.color("&2» &7End Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.THE_END), Utils.loadedChunksInWorlds(World.Environment.THE_END)));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("systeminfo.command.help")) {
            sender.sendMessage(tl("no-perms"));
            return true;
        }
        if (args.length == 0) {
            systemInfo1(sender);
            return true;
        }
        if (args[0].equals("2")) {
            systemInfo2(sender);
            return true;
        }
        if (args[0].equals("version")) {
            sender.sendMessage(String.format(Utils.color("&2» &7SystemInfo version: &a%s"), systemInfo.getDescription().getVersion()));
            return true;
        }
        if (args[0].equals("stats")) {
            stats(sender);
            return true;
        }
        if (args[0].equals("gui")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to run this command");
                return true;
            }
            new SystemInfoGui(systemInfo, player);
        }
        return true;
    }

}
