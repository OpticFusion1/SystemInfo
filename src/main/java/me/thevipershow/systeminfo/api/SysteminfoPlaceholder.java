package me.thevipershow.systeminfo.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.OfflinePlayer;

public class SysteminfoPlaceholder extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "systeminfo";
    }

    @Override
    public String getAuthor() {
        return "TheViperShow";
    }

    @Override
    public String getVersion() {
        return SystemInfo.PLUGIN_VERSION;
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        StringBuilder toReturnString = new StringBuilder();
        switch (params) {
            case "cpu-model" -> toReturnString.append(String.format("%s %s", SystemValues.getCpuModel(), SystemValues.getCpuModelName()));
            case "cpu-frequency" -> toReturnString.append(SystemValues.getCpuMaxFrequency());
            case "cpu-temperature" -> toReturnString.append(SystemValues.getCpuTemperature());
            case "swap-max" -> toReturnString.append(SystemValues.getTotalSwap());
            case "swap-used" -> toReturnString.append(SystemValues.getUsedSwap());
            case "memory-max" -> toReturnString.append(SystemValues.getMaxMemory());
            case "memory-available" -> toReturnString.append(SystemValues.getAvailableMemory());
            case "memory-used" -> toReturnString.append(SystemValues.getUsedMemory());
            case "processes" -> toReturnString.append(SystemValues.getRunningProcesses());
            default -> {
            }
        }
        return toReturnString.toString();
    }
}