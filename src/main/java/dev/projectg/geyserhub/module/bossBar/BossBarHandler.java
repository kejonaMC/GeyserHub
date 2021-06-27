package dev.projectg.geyserhub.module.bossBar;

import dev.projectg.geyserhub.GeyserHubMain;
import dev.projectg.geyserhub.config.ConfigId;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Objects;

public class BossBarHandler {

    public static HashMap<Integer, String> color = new HashMap<>();
    public static HashMap<Integer, String> text = new HashMap<>();
    public static HashMap<Integer, String> style = new HashMap<>();

    public void bossBarSetup() {
        FileConfiguration config = GeyserHubMain.getInstance().getConfigManager().getFileConfiguration(ConfigId.MAIN);
        int i = 1;
        while (config.getString("BossBar.Bar" + i + ".Text") != null) {
            color.put(i, Objects.requireNonNull(config.getString("BossBar.Bar" + i + ".Color")).replace("&", "§"));
            text.put(i, Objects.requireNonNull(config.getString("BossBar.Bar" + i + ".Text")).replace("&", "§"));
            style.put(i, config.getString("BossBar.Bar" + i + ".Type"));
            i++;
        }
    }
}
