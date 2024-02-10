package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

public class WedkaProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Wedka")
            .type(InventoryType.HOPPER)
            .provider(new WedkaProvider())
            .title(Util.fixColors("&cMode magic rod"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        ItemBuilder pulling = new ItemBuilder(Material.FISHING_ROD, 1).setName(Util.fixColors("&cMode: &6Pulling players"));
        ItemBuilder pushing = new ItemBuilder(Material.FISHING_ROD, 1).setName(Util.fixColors("&cMode: &aPushing players"));
        ItemBuilder leaping = new ItemBuilder(Material.FISHING_ROD, 1).setName(Util.fixColors("&cMode: &cLeaping to player"));
        final User u = UserManager.getUser(player);
        switch (u.getWedka()){
            case "PRZYCIAGANIE":
                pulling.addEnchant(Enchantment.DURABILITY, 10);
                pulling.addLoreLine(Util.fixColors("&cCurrently set"));
                break;
            case "DOSKOK":
                leaping.addEnchant(Enchantment.DURABILITY, 10);
                leaping.addLoreLine(Util.fixColors("&cCurrently set"));
                break;
            case "ODPYCHANIE":
                pushing.addEnchant(Enchantment.DURABILITY, 10);
                pushing.addLoreLine(Util.fixColors("&cCurrently set"));
                break;
        }
        inventoryContents.set(0, 0, ClickableItem.of(pulling.toItemStack(), e-> {
            u.setWedka("PRZYCIAGANIE");
            Util.sendTitle(player, "&8{o} &cMagic Fishing Rod &8{o}");
            Util.sendSubTitle(player, "&8>> &7Mode changed to " + (u.getWedka().equals("PRZYCIAGANIE") ? "&6pulling players to you" : (u.getWedka().equals("DOSKOK") ? "&aleaping to a player" : "&cpushing players away")));
            player.closeInventory();
        }));
        inventoryContents.set(0, 2, ClickableItem.of(leaping.toItemStack(), e-> {
            u.setWedka("DOSKOK");
            Util.sendTitle(player, "&8{o} &cMagic Fishing Rod &8{o}");
            Util.sendSubTitle(player, "&8>> &7Mode changed to " + (u.getWedka().equals("PRZYCIAGANIE") ? "&6pulling players to you" : (u.getWedka().equals("DOSKOK") ? "&aleaping to a player" : "&cpushing players away")));
            player.closeInventory();
        }));
        inventoryContents.set(0, 4, ClickableItem.of(pushing.toItemStack(), e-> {
            u.setWedka("ODPYCHANIE");
            Util.sendTitle(player, "&8{o} &cMagic Fishing Rod &8{o}");
            Util.sendSubTitle(player, "&8>> &7Mode changed to " + (u.getWedka().equals("PRZYCIAGANIE") ? "&6pulling players to you" : (u.getWedka().equals("DOSKOK") ? "&aleaping to a player" : "&cpushing players away")));
            player.closeInventory();
        }));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
