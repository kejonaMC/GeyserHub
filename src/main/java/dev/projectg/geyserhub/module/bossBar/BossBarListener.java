package dev.projectg.geyserhub.module.bossBar;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BossBarListener implements Listener {

    public void onJoin(PlayerJoinEvent e){
        for(Integer index : BossBarHandler.color.keySet()) {
            if(getColor(BossBarHandler.color.get(index)) == null) {
                BossBar bossBar = Bukkit.createBossBar("§cThis color is not available!", BarColor.WHITE, BarStyle.SOLID);
                bossBar.addPlayer(e.getPlayer());
            }else {

                if(getBarStyle(BossBarHandler.style.get(index)) == null) {
                    BossBar bossBar = Bukkit.createBossBar("§cThis BarType is not available!", BarColor.WHITE, BarStyle.SOLID);
                    bossBar.addPlayer(e.getPlayer());
                    return;
                }

                BossBar bossBar = Bukkit.createBossBar(BossBarHandler.text.get(index), getColor(BossBarHandler.color.get(index)), getBarStyle(BossBarHandler.style.get(index)));
                bossBar.addPlayer(e.getPlayer());
            }
        }

    }

    public BarColor getColor(String color_as_string) {
        if(color_as_string.equalsIgnoreCase("blue")) {
            return BarColor.BLUE;
        }else if(color_as_string.equalsIgnoreCase("green")) {
            return BarColor.GREEN;
        }else if(color_as_string.equalsIgnoreCase("pink")) {
            return BarColor.PINK;
        }else if(color_as_string.equalsIgnoreCase("purple")) {
            return BarColor.PURPLE;
        }else if(color_as_string.equalsIgnoreCase("red")) {
            return BarColor.RED;
        }else if(color_as_string.equalsIgnoreCase("white")) {
            return BarColor.WHITE;
        }else if(color_as_string.equalsIgnoreCase("yellow")) {
            return BarColor.YELLOW;
        }else {
            return null;
        }
    }

    public BarStyle getBarStyle(String barstyle_as_string) {
        if(barstyle_as_string.equalsIgnoreCase("solid")) {
            return BarStyle.SOLID;
        }else if(barstyle_as_string.equalsIgnoreCase("segmented_6")) {
            return BarStyle.SEGMENTED_6;
        }else if(barstyle_as_string.equalsIgnoreCase("segmented_20")) {
            return BarStyle.SEGMENTED_20;
        }else if(barstyle_as_string.equalsIgnoreCase("segmented_12")) {
            return BarStyle.SEGMENTED_12;
        }else if(barstyle_as_string.equalsIgnoreCase("segmented_10")) {
            return BarStyle.SEGMENTED_10;
        }else {
            return null;
        }
    }

}
