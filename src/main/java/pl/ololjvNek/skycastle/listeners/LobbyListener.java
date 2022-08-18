package pl.ololjvNek.skycastle.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.providers.MeniProvider;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.concurrent.ThreadLocalRandom;

public class LobbyListener implements Listener {

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e){
        if(e.getEntity().getShooter() instanceof Player){
            Player p = (Player) e.getEntity().getShooter();
            User u = UserManager.getUser(p);
            if(e.getEntity() instanceof EnderPearl){
                if(u.isMagicPearl()){
                    e.getEntity().setPassenger(p);
                }
            }
        }
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            User u = UserManager.getUser(p);
            if(u.getSkyCastle() == null){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            User u = UserManager.getUser(p);
            if(u.getSkyCastle() == null){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e){
        Player p = (Player) e.getWhoClicked();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e){
        if(e.getEntity() instanceof EnderPearl){
            if(e.getEntity().getShooter() instanceof Player){
                Player p = (Player) e.getEntity().getShooter();
                User u = UserManager.getUser(p);
                if(u.isMagicPearl()){
                    ItemStack is = ItemUtil.magicPearl.clone();
                    ItemMeta im = is.getItemMeta();
                    im.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
                    is.setItemMeta(im);
                    p.getInventory().setItem(6, is);
                }else{
                    p.getInventory().setItem(6, ItemUtil.magicPearl);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(p.isOp()){
            return;
        }
        if(u.getSkyCastle() == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(p.isOp()){
            return;
        }
        if(u.getSkyCastle() == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(p.isOp()){
            return;
        }
        if(u.getSkyCastle() == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(p.getFoodLevel() < 20){
            p.setFoodLevel(20);
        }
        if(UserManager.getUser(p).getSkyCastle() == null && p.getLocation().getBlockY() < 0){
            p.teleport(p.getWorld().getSpawnLocation());
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(p.isOp()){
            return;
        }
        if(u.getSkyCastle() == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(e.getItem() != null && e.getItem().isSimilar(ItemUtil.gadzety)){
            e.setCancelled(true);
            MeniProvider.INVENTORY.open(p);
            return;
        }
        if(e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Util.fixColors("&5Magiczna Perla")) && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)){
            u.setMagicPearl(!u.isMagicPearl());
            if(u.isMagicPearl()){
                ItemMeta im = p.getItemInHand().getItemMeta();
                im.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
                p.getItemInHand().setItemMeta(im);
            }else{
                ItemMeta im = p.getItemInHand().getItemMeta();
                im.removeEnchant(Enchantment.LOOT_BONUS_BLOCKS);
                p.getItemInHand().setItemMeta(im);
            }
        }
        if(e.getItem() != null && e.getItem().isSimilar(ItemUtil.debugStick)){
            if(e.getClickedBlock() != null){
                e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.values()[ThreadLocalRandom.current().nextInt(Material.values().length)], (byte)0);
            }
        }
    }
}
