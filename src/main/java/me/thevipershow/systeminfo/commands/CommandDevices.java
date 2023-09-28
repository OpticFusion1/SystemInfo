package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.oshi.SystemValues;
import static me.thevipershow.systeminfo.utils.I18n.tl;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import oshi.hardware.UsbDevice;

public final class CommandDevices implements CommandExecutor {

    private void printDevices(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7Attached devices &2«"));
        sender.sendMessage(Utils.color("&2» &7List:"));
        for (UsbDevice usb : SystemValues.getUsbDevices()) {
            sender.sendMessage(Utils.color(String.format("&7- &a%s %s", usb.getVendor(), usb.getSerialNumber())));
            sender.spigot().sendMessage(Utils.builderHover(" &7Serial-ID &8[&a*&8]&r", usb.getSerialNumber()));
            if (usb.getConnectedDevices().length != 0) {
                for (UsbDevice subUsb : usb.getConnectedDevices()) {
                    sender.sendMessage(Utils.color(String.format(" &7|- &a%s %s", subUsb.getVendor(), subUsb.getName())));
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("systeminfo.commands.devices")) {
            sender.sendMessage(tl("no-perms"));
            return true;
        }
        printDevices(sender);
        return true;
    }

}
