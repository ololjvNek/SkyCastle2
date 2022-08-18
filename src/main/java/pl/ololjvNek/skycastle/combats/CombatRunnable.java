package pl.ololjvNek.skycastle.combats;

import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.skycastle.utils.DataUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class CombatRunnable extends BukkitRunnable {

    @Override
    public void run() {
        for(Combat combat : CombatManager.getCombats()){
            if(!combat.getWhoHitted().isOnline() || !combat.getLastPlayerHit().isOnline()){
                CombatManager.getCombats().remove(combat);
                continue;
            }
            if(combat.getLogout() > System.currentTimeMillis()){
                if(combat.getLastPlayerHit() != null){
                    Util.sendActionBar(combat.getLastPlayerHit(), Util.fixColors("&cAntyLogout &8&m=||= &a" + DataUtil.secondsToString(combat.getLogout())));
                }
                if(combat.getWhoHitted() != null){
                    Util.sendActionBar(combat.getWhoHitted(), Util.fixColors("&cAntyLogout &8&m=||= &a" + DataUtil.secondsToString(combat.getLogout())));
                }
            }else{
                CombatManager.getCombats().remove(combat);
            }
        }
    }
}
