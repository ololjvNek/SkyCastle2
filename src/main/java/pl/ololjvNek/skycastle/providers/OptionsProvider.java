package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

public class OptionsProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new OptionsProvider())
            .size(3, 9)
            .title(Util.fixColors("&cUstawienia"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        User u = UserManager.getUser(player);
        ItemBuilder chat = new ItemBuilder(Material.PAPER, 1).setName(Util.fixColors("&8>> &6Osiagniecia")).setLore(Util.fixColors("&8>> &7Kliknij, aby przejsc do swoich osiagniec"));
        ItemBuilder wedka = new ItemBuilder(Material.FISHING_ROD, 1).setName(Util.fixColors("&6/wedka"));
        ItemBuilder widocznosc = new ItemBuilder(Material.SLIME_BALL, 1).setName(Util.fixColors("&8>> &6Widocznosc graczy")).setLore(Util.fixColors("&8>> &7Widocznosc: " + (u.isVision() ? "&aTAK" : "&cNIE")));
        inventoryContents.set(1, 2, ClickableItem.of(chat.toItemStack(), e -> {

        }));
        inventoryContents.set(1, 4, ClickableItem.of(wedka.toItemStack(), e -> {
            player.closeInventory();
            WedkaProvider.INVENTORY.open(player);
        }));
        inventoryContents.set(1, 6, ClickableItem.of(widocznosc.toItemStack(), e -> {
            u.setVision(!u.isVision());
            if(u.isVision()){
                for(Player pOnline : Bukkit.getOnlinePlayers()){
                    if (player.canSee(pOnline)) {
                        player.hidePlayer(pOnline);
                    }
                }
            }else{
                for(Player pOnline : Bukkit.getOnlinePlayers()){
                    if (!player.canSee(pOnline)) {
                        player.showPlayer(pOnline);
                    }
                }
            }
        }));
    }
}
