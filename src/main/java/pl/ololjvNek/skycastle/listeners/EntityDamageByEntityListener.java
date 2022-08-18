package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.ololjvNek.skycastle.combats.Combat;
import pl.ololjvNek.skycastle.combats.CombatManager;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.TimeUtil;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getDamager() instanceof Player){
                Player victim = (Player) e.getEntity();
                Player attacker = (Player) e.getDamager();

                User vU = UserManager.getUser(victim);
                User aU = UserManager.getUser(attacker);

                SkyCastle skyCastle = aU.getSkyCastle();
                if(skyCastle != null && skyCastle.getStatus() == ArenaStatus.STARTED){
                    if(skyCastle.isSpectator(attacker)){
                        e.setCancelled(true);
                        return;
                    }
                    if(skyCastle.isTeamMate(attacker, victim)){
                        e.setCancelled(true);
                        return;
                    }
                    if(CombatManager.getCombat(attacker) == null){
                        CombatManager.createCombat(attacker, victim);
                    }
                    Combat CAttacker = CombatManager.getCombat(attacker);
                    if(CAttacker == null){
                        return;
                    }
                    if(CAttacker.getLastPlayerHit() == null){
                        CAttacker.setLastPlayerHit(victim);
                    }
                    CAttacker.setLogout(System.currentTimeMillis()+ TimeUtil.SECOND.getTime(10));
                    if(!CAttacker.getLastPlayerHit().getUniqueId().toString().equals(attacker.getUniqueId().toString())){
                        if(!CAttacker.isInAssisted(CAttacker.getLastPlayerHit())) {
                            CAttacker.getAssisted().add(CAttacker.getLastPlayerHit());
                        }
                        if(CAttacker.isInAssisted(attacker)){
                            CAttacker.getAssisted().remove(attacker);
                        }
                        CAttacker.setLastPlayerHit(attacker);
                    }
                }else if(skyCastle != null && (skyCastle.getStatus() == ArenaStatus.STARTING || skyCastle.getStatus() == ArenaStatus.WAITING)){
                    e.setCancelled(true);
                }
            }
        }else if(e.getEntity() instanceof Villager){
            e.setCancelled(true);
        }
    }
}
