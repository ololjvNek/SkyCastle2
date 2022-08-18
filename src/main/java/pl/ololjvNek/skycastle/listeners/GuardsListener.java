package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.GuardUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class GuardsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTarget(EntityTargetLivingEntityEvent e){
        if(e.getTarget() instanceof Player){
            Player target = (Player) e.getTarget();
            User u = UserManager.getUser(target);
            if(u.getSkyCastle() != null){
                SkyCastle skyCastle = u.getSkyCastle();
                String teamEntity = e.getEntity().getMetadata("team").get(0).value().toString();

                if(e.getEntity() instanceof Skeleton){
                        if(skyCastle.getTeamIn(target).equals(teamEntity)){
                            e.setCancelled(true);
                        }

                }
                if(e.getEntity() instanceof IronGolem){
                        if(skyCastle.getTeamIn(target).equals(teamEntity)){
                            e.setCancelled(true);
                        }

                }
                if(e.getEntity() instanceof Ghast){
                        if(skyCastle.getTeamIn(target).equals(teamEntity)){
                            e.setCancelled(true);
                        }

                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if((e.getDamager() instanceof Skeleton || e.getDamager() instanceof Arrow) && e.getEntity() instanceof Player){
            Entity damager = e.getDamager();
            if(e.getDamager() instanceof Arrow){
                damager = (Entity) ((Arrow)e.getDamager()).getShooter();
            }
            if(!(damager instanceof Skeleton)){
                return;
            }
            Player victim = (Player) e.getEntity();
            if(!GuardUtil.isOnCooldown(damager.getUniqueId())){
                GuardUtil.putOnCooldown(damager.getUniqueId(), 6);
                victim.setVelocity(damager.getLocation().getDirection().multiply(3));
            }
        }
        if((e.getDamager() instanceof Ghast || e.getDamager() instanceof Fireball) && e.getEntity() instanceof Player){
            Entity damager = e.getDamager();
            if(e.getDamager() instanceof Fireball){
                damager = (Entity) ((Fireball)e.getDamager()).getShooter();
            }
            Player victim = (Player) e.getEntity();
            if(!GuardUtil.isOnCooldown(damager.getUniqueId())){
                GuardUtil.putOnCooldown(damager.getUniqueId(), 13);
                victim.setFireTicks(200);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 1));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*5, 1));
            }
        }
        if(e.getDamager() instanceof IronGolem && e.getEntity() instanceof Player){
            Entity damager = e.getDamager();
            Player victim = (Player) e.getEntity();
            if(!GuardUtil.isOnCooldown(damager.getUniqueId())){
                GuardUtil.putOnCooldown(damager.getUniqueId(), 10);
                for(int i = 0; i < 3; i++){
                    Silverfish silverfish = (Silverfish) damager.getWorld().spawnEntity(damager.getLocation(), EntityType.SILVERFISH);
                    silverfish.setMaxHealth(30);
                    silverfish.setHealth(30);
                    silverfish.setTarget(victim);
                }
                victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20*5, 0));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20*5, 0));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5, 1));
                Util.sendTitle(victim, "&cCritical HIT!");
                Util.sendSubTitle(victim, "&7You got critical hit and now you are weak");
            }
        }
    }
}
