package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.RandomUtil;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.List;

public class GeneratorListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null){
            SkyCastle skyCastle = u.getSkyCastle();
            Block block = e.getBlock();
            if(skyCastle.getStatus() == ArenaStatus.WAITING || skyCastle.getStatus() == ArenaStatus.STARTING){
                e.setCancelled(true);
            }
            if(e.isCancelled()){
                return;
            }
            for(Location generator : skyCastle.getGenerators()){
                if(e.getBlock().getLocation().distance(generator) < 8){
                    if(block.getType() == Material.COBBLESTONE || block.getType() == Material.LOG || block.getType() == Material.STONE){
                        e.setCancelled(true);
                        List<ItemStack> itemlist = (List<ItemStack>) e.getBlock().getDrops();
                        double chance = ((skyCastle.getEvent() != null && skyCastle.getEvent().isEnabled() && skyCastle.getEvent().hasEvent("Szczescie")) ? 60.0 : 30.0 );
                        if(!RandomUtil.getChance(chance)){
                            Util.dropToEquipment(itemlist, p);
                        }else{
                            itemlist.add(ItemUtil.runa);
                            Util.dropToEquipment(itemlist, p);
                            Util.sendTitle(p, "");
                            Util.sendSubTitle(p, "&8>> &bYou're dropped a rune from block!");
                        }
                        return;
                    }
                }
            }
            if(!skyCastle.getPlacedBlocks().contains(e.getBlock().getLocation())){
                Util.sendMessage(p, "&4Error: &cYou cannot destroy map!");
                e.setCancelled(true);
                return;
            }
            if(skyCastle.getKapliczka().distance(block.getLocation()) < 9){
                e.setCancelled(true);
                Util.sendTitle(p, "&4Error");
                Util.sendSubTitle(p, "&cYou cannot build that here!");
            }
            for(Entity entity : e.getPlayer().getNearbyEntities(6, 6, 6)){
                if(entity instanceof Player){
                    if(entity != e.getPlayer()){
                        Player target = (Player) entity;
                        Location targetBlock = target.getLocation().subtract(0, 1, 0);
                        Location blockDestroy = e.getBlock().getLocation();
                        if(targetBlock.getBlockX() == blockDestroy.getBlockX() && targetBlock.getBlockZ() == blockDestroy.getBlockZ() && targetBlock.getBlockY() == blockDestroy.getBlockY()){
                            if(skyCastle.isTeamMate(p, target)){
                                e.setCancelled(true);
                                Util.sendTitle(p, "&cWooah! Stop undermine your teammates!");
                                break;
                            }
                        }
                    }
                }
            }
            skyCastle.getBlockStates().add(e.getBlock().getState());
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e){
        if(e.getEntityType() == EntityType.PRIMED_TNT){
            World world = e.getEntity().getWorld();
            if(SkyCastleManager.getSkyCastle(world.getName()) != null){
                SkyCastle skyCastle = SkyCastleManager.getSkyCastle(world.getName());
                if(skyCastle.getStatus() == ArenaStatus.STARTED){
                    for(Block block : e.blockList()){
                        skyCastle.getBlockStates().add(block.getState());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null){
            SkyCastle skyCastle = u.getSkyCastle();
            if(skyCastle.getStatus() == ArenaStatus.WAITING || skyCastle.getStatus() == ArenaStatus.STARTING){
                e.setCancelled(true);
                return;
            }
            Block block = e.getBlockPlaced();
            if(skyCastle.getKapliczka().distance(block.getLocation()) < 9){
                e.setCancelled(true);
                Util.sendTitle(p, "&4Error");
                Util.sendSubTitle(p, "&cYou cannot build that here!");
            }
            skyCastle.getPlacedBlocks().add(e.getBlockPlaced().getLocation());
        }
    }
}
