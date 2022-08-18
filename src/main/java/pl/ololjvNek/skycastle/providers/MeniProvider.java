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

public class MeniProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new MeniProvider())
            .size(5, 9)
            .title(Util.fixColors("&cMenu"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        inventoryContents.fill(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7)));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        User u = UserManager.getUser(player);
        ItemBuilder statystyki = new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner(player.getName()).setName(Util.fixColors("&8>> &cYour statistics")).setLore("", Util.fixColors("  &8>> &7Kills: &6" + u.getKills()), Util.fixColors("  &8>> &7Deaths: &6" + u.getDeaths()), Util.fixColors("  &8>> &7Assists: &6" + u.getAssists()), Util.fixColors("  &8>> &7KD/A: &6" + u.getKDA()), Util.fixColors("  &8>> &7Stars: &6" + u.getStars()), Util.fixColors("  &8>> &7Coins: &6" + u.getCoins()));
        ItemBuilder disco = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255))).setName(Util.fixColors("&8>> &aDisco Armour")).setLore("", Util.fixColors("&8>> &7For &6PREMIUM &7ranks"));
        ItemBuilder sklep = new ItemBuilder(Material.EMERALD, 1).setName(Util.fixColors("&8>> &6Shop")).setLore(Util.fixColors("&7Click to open &6shop &aSkyCastle&7!"));
        ItemBuilder opcje = new ItemBuilder(Material.REDSTONE_COMPARATOR, 1).setName(Util.fixColors("&8>> &6Options")).setLore(Util.fixColors("&7Click to open options"));
        inventoryContents.set(0, 4, ClickableItem.empty(statystyki.toItemStack()));
        inventoryContents.set(4, 4, ClickableItem.empty(statystyki.toItemStack()));
        inventoryContents.set(2, 2, ClickableItem.of(disco.toItemStack(), e -> {
            if(!player.hasPermission("disco.vip")){
                Util.sendTitle(player, "&cNo permissions");
                player.closeInventory();
                return;
            }
            player.closeInventory();
            DiscoProvider.INVENTORY.open(player);
        }));
        inventoryContents.set(2, 4, ClickableItem.of(sklep.toItemStack(), e -> {
            player.closeInventory();
            ShopProvider.INVENTORY.open(player);
        }));
        inventoryContents.set(2, 6, ClickableItem.of(opcje.toItemStack(), e -> {
            player.closeInventory();
            OptionsProvider.INVENTORY.open(player);
        }));
    }
}
