package pl.ololjvNek.skycastle.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.UserManager;

public class SpectatorListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(u.getSkyCastle().isSpectator(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(u.getSkyCastle().isSpectator(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(u.getSkyCastle().isSpectator(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(u.getSkyCastle().isSpectator(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e){
        if(!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity().getShooter();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(u.getSkyCastle().isSpectator(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(u.getSkyCastle().isSpectator(p)){
                e.setCancelled(true);
            }
        }
    }
}
