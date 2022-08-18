package pl.ololjvNek.skycastle.runnable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsRunnable extends BukkitRunnable {

    static List<String> messages = Main.getGlobalConfig().getConfig().getStringList("globalSettings.autoMessages");

    static int i = 0;

    @Override
    public void run() {
        if(i < messages.size()){
            Util.sendMessage(Bukkit.getOnlinePlayers(), "&f&lSKYCASTLE &8>> " + messages.get(i));
            i++;
        }else{
            i = 0;
        }
    }
}
