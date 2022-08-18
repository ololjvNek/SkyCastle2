package pl.ololjvNek.skycastle.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.managers.VillagerShopManager;
import pl.ololjvNek.skycastle.providers.MenuProvider;
import pl.ololjvNek.skycastle.providers.VillagerShopProvider;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem() != null){
            Player p = e.getPlayer();
            if (e.getItem().isSimilar(ItemUtil.menu)) {
                MenuProvider.INVENTORY.open(p);
            }
            if(e.getItem().isSimilar(ItemUtil.leaveGame)){
                User u = UserManager.getUser(p);
                if(u.getSkyCastle() != null){
                    SkyCastleManager.leaveGame(u.getSkyCastle(), p);
                }
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null && u.getSkyCastle().getStatus() == ArenaStatus.STARTED){
            if(VillagerShopManager.getVillagerShop(e.getRightClicked()) != null){
                e.setCancelled(true);
                VillagerShopProvider.INVENTORY.open(p);
            }
        }else if(u.getSkyCastle() == null){
            if(Main.getGameVillager().getCustomName().equalsIgnoreCase(Util.fixColors(Main.getGlobalConfig().getConfig().getString("globalSettings.playVillagerName")))){
                e.setCancelled(true);
                MenuProvider.INVENTORY.open(p);
            }
        }
    }
}
