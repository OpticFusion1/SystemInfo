package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.oshi.SystemValues;
import static me.thevipershow.systeminfo.utils.I18n.tl;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;

public final class CommandDisks implements CommandExecutor {

    private void printDisks(CommandSender sender) {
        for (HWDiskStore disk : SystemValues.getDiskStores()) {
            sender.spigot().sendMessage(Utils.builderHover(String.format("&7[%s %s", disk.getName(), disk.getModel()),
                    String.format("&7Serial: &a%s\n&7Disk Read: &a%s\n&7Disk Written: &a%s\n", disk.getSerial(), Utils.formatData(disk.getReadBytes()), Utils.formatData(disk.getWriteBytes()))));
            for (HWPartition part : disk.getPartitions()) {
                sender.spigot().sendMessage(Utils.builderHover(String.format("  &7|-- &a%s &7Size: &a%s", part.getIdentification() + " " + part.getType(), Utils.formatData(part.getSize())), String.format("&7Mount point: &a%s &7Uuid: &a%s", part.getMountPoint(), part.getUuid())));
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("systeminfo.commands.disks")) {
            sender.sendMessage(tl("no-perms"));
            return true;
        }
        printDisks(sender);
        return true;
    }

}
