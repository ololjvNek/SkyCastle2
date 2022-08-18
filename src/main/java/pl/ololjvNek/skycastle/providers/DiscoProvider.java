package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.concurrent.ThreadLocalRandom;

public class DiscoProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new DiscoProvider())
            .size(5, 9)
            .title(Util.fixColors("&cDisco armour"))
            .build();

    int smoothGlobal = 0;

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7)));
        ItemBuilder smooth = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromRGB(smoothGlobal, smoothGlobal, smoothGlobal)).setName(Util.fixColors("&8>> &7Tryb: &cSMOOTH"));
        ItemBuilder random = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255))).setName(Util.fixColors("&8>> &7Tryb: &aRANDOM"));
        ItemBuilder speed = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromRGB(smoothGlobal, smoothGlobal, smoothGlobal)).setName(Util.fixColors("&8>> &7Tryb: &bSPEED"));
        smoothGlobal++;
        if(smoothGlobal > 255){
            smoothGlobal = 0;
        }
        inventoryContents.set(2, 2, ClickableItem.of(smooth.toItemStack(), e -> {
            User u = UserManager.getUser(player);
            u.getDiscoArmor().setSmooth(!u.getDiscoArmor().isSmooth());
        }));
        inventoryContents.set(2, 4, ClickableItem.of(random.toItemStack(), e -> {
            User u = UserManager.getUser(player);
            u.getDiscoArmor().setRandom(!u.getDiscoArmor().isRandom());
        }));
        inventoryContents.set(2, 6, ClickableItem.of(speed.toItemStack(), e -> {
            User u = UserManager.getUser(player);
            u.getDiscoArmor().setSpeed(!u.getDiscoArmor().isSpeed());
        }));
    }
}
