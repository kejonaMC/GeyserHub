package dev.projectg.geyserhub;

import dev.projectg.geyserhub.config.ConfigManager;
import dev.projectg.geyserhub.message.Broadcast;
import dev.projectg.geyserhub.message.MessageJoin;
import dev.projectg.geyserhub.utils.FileUtils;
import dev.projectg.geyserhub.utils.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class GeyserHub extends JavaPlugin {
    private static GeyserHub plugin;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        plugin = this;
        // getting the logger forces the config to load before our loadConfiguration() is called...
        Logger logger = Logger.getLogger();

        logger.warn("THIS PLUGIN HAS BEEN SUPERSEDED BY CROSSPLATFORMS: https://github.com/kejonaMC/CrossplatForms");
        logger.warn("FORM, MENU, AND ITEM FEATURES ARE NO LONGER PRESENT IN GEYSERHUB");
        logger.warn("Place your selector.yml in the config folder of CrossplatForms for it to be automatically converted.");

        try {
            Properties gitProperties = new Properties();
            gitProperties.load(FileUtils.getResource("git.properties"));
            logger.info("Branch: " + gitProperties.getProperty("git.branch", "Unknown") + ", Commit: " + gitProperties.getProperty("git.commit.id.abbrev", "Unknown"));
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            logger.warn("Unable to load resource: git.properties");
            e.printStackTrace();
        }

        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            logger.warn("This plugin works best with PlaceholderAPI! Since you don't have it installed, only %player_name% and %player_uuid% will work in the GeyserHub config!");
        }

        configManager = new ConfigManager();
        if (!configManager.loadAllConfigs()) {
            logger.severe("Disabling due to configuration error.");
            return;
        }

        // Bungee channel for selector
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Objects.requireNonNull(getCommand("ghub")).setExecutor(new GeyserHubCommand());

        Bukkit.getServer().getPluginManager().registerEvents(new JoinTeleporter(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new WorldSettings(), this);

        // load the scoreboard if enabled
        if (getConfig().getBoolean("Scoreboard.Enable", false)) {
            initializeScoreboard();
        }

        // Enable the join message if enabled
        if (getConfig().getBoolean("Enable-Join-Message", false)) {
            Bukkit.getServer().getPluginManager().registerEvents(new MessageJoin(), this);
        }

        // The random interval broadcast module
        Broadcast.startBroadcastTimer(getServer().getScheduler());

        if (getConfig().getBoolean("Bstats", true)) {
            new Metrics(this, 13469);
        }

        logger.info("Took " + (System.currentTimeMillis() - start) + "ms to boot GeyserHub.");
    }

    public void initializeScoreboard() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            try {
                ScoreboardManager.addScoreboard();
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }, 20L, ScoreboardManager.REFRESH_RATE * 20L);
    }

    public static GeyserHub getInstance() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
