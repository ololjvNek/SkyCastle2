package pl.ololjvNek.skycastle.listeners;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerInventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(p.getOpenInventory().getType() == InventoryType.CHEST){
                if(isInvEmpty(p.getOpenInventory().getTopInventory())){
                    for(Location loc : Util.sphere(p.getLocation(), 5, 5, false, true, 0)){
                        if(loc.getBlock().getType() == Material.CHEST){
                            loc.getBlock().setType(Material.AIR);
                            loc.getWorld().spigot().playEffect(loc, Effect.CLOUD, 1, 0, 0.4f, 0.4f, 0.4f, 1, 20, 1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null){
            if(e.getCurrentItem().getType() == Material.GOLD_NUGGET){
                e.setCancelled(true);
                e.getClickedInventory().removeItem(e.getCurrentItem());
                double coins = ThreadLocalRandom.current().nextDouble(40);
                if(p.hasPermission("skycastle.vip")){
                    coins *= 1.50;
                }
                coins = Util.round(coins, 0);
                UserManager.getUser(p).addCoins((int)coins);
                Util.sendActionBar(p, "&7You got &6" + coins + " &7coins!");
            }
        }
    }

    public boolean isInvEmpty(Inventory inv) {
        boolean toRet = false;
        for (ItemStack item : inv.getContents()) {
            toRet = item == null;
        }
        return toRet;
    }
}
