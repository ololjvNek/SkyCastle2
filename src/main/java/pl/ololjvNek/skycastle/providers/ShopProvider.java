package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.PermissionShop;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.PermissionShopManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.LPUtil;
import pl.ololjvNek.skycastle.utils.Logger;
import pl.ololjvNek.skycastle.utils.Util;

public class ShopProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new ShopProvider())
            .size(5, 9)
            .title(Util.fixColors("&cSklep"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        int slot = 0;
        int row = 0;
        final User u = UserManager.getUser(player);
        for(PermissionShop permissionShop : PermissionShopManager.getPermissionShops().values()){
            ItemBuilder ib = new ItemBuilder(permissionShop.getGuiItem(), permissionShop.getGuiAmount(), permissionShop.getGuiData()).setName(Util.fixColors(permissionShop.getGuiName())).setLore(Util.fixColors(permissionShop.getGuiLore()));
            boolean posiadane = false;
            if(permissionShop.getPermission().isEmpty()){
                if(u.hasAddon(permissionShop.getAddon())){
                    posiadane = true;
                }
            }else{
                if(player.hasPermission(permissionShop.getPermission())){
                    posiadane = true;
                }
            }
            if(posiadane){
                ib.addLoreLine("");
                ib.addLoreLine(Util.fixColors("&aPosiadane"));
                inventoryContents.set(row,slot, ClickableItem.empty(ib.toItemStack()));
            }else{
                ib.addLoreLine("");
                ib.addLoreLine(Util.fixColors("&8>> &7Kliknij LPM, aby &6zakupic"));
                inventoryContents.set(row,slot, ClickableItem.of(ib.toItemStack(), e-> {
                    if(u.getCoins() >= permissionShop.getCost()){
                        u.removeCoins(permissionShop.getCost());
                        if(!permissionShop.getPermission().isEmpty()){
                            LPUtil.addPlayerPermission(player, permissionShop.getPermission());
                        }else{
                            u.getAddons().add(permissionShop.getAddon());
                        }
                        Util.sendTitle(player, "&a&lSKLEP");
                        Util.sendSubTitle(player, "&8>> &7Zakupiles przedmiot: " + permissionShop.getGuiName());
                        player.closeInventory();
                    }else{
                        Util.sendTitle(player, "&cNie stac cie!");
                    }
                }));
            }
            slot++;
            if(slot >= 8){
                row++;
                slot = 0;
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
