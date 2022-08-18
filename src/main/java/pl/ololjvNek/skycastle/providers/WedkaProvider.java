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
            .title(Util.fixColors("&cTryb wedki"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        ItemBuilder przyciaganie = new ItemBuilder(Material.FISHING_ROD, 1).setName(Util.fixColors("&cTryb: &6Przyciaganie graczy"));
        ItemBuilder odpychanie = new ItemBuilder(Material.FISHING_ROD, 1).setName(Util.fixColors("&cTryb: &aOdpychanie graczy"));
        ItemBuilder doskok = new ItemBuilder(Material.FISHING_ROD, 1).setName(Util.fixColors("&cTryb: &cDoskok do gracza"));
        final User u = UserManager.getUser(player);
        switch (u.getWedka()){
            case "PRZYCIAGANIE":
                przyciaganie.addEnchant(Enchantment.DURABILITY, 10);
                przyciaganie.addLoreLine(Util.fixColors("&cAktualnie ustawione"));
                break;
            case "DOSKOK":
                doskok.addEnchant(Enchantment.DURABILITY, 10);
                doskok.addLoreLine(Util.fixColors("&cAktualnie ustawione"));
                break;
            case "ODPYCHANIE":
                odpychanie.addEnchant(Enchantment.DURABILITY, 10);
                odpychanie.addLoreLine(Util.fixColors("&cAktualnie ustawione"));
                break;
        }
        inventoryContents.set(0, 0, ClickableItem.of(przyciaganie.toItemStack(), e-> {
            u.setWedka("PRZYCIAGANIE");
            Util.sendTitle(player, "&8{o} &cMagiczna Wedka &8{o}");
            Util.sendSubTitle(player, "&8>> &7Tryb zmieniony na " + (u.getWedka().equals("PRZYCIAGANIE") ? "&6przyciaganie gracza do Ciebie" : (u.getWedka().equals("DOSKOK") ? "&aprzyciagniecie sie do gracza" : "&bodpychanie gracza")));
            player.closeInventory();
        }));
        inventoryContents.set(0, 2, ClickableItem.of(doskok.toItemStack(), e-> {
            u.setWedka("DOSKOK");
            Util.sendTitle(player, "&8{o} &cMagiczna Wedka &8{o}");
            Util.sendSubTitle(player, "&8>> &7Tryb zmieniony na " + (u.getWedka().equals("PRZYCIAGANIE") ? "&6przyciaganie gracza do Ciebie" : (u.getWedka().equals("DOSKOK") ? "&aprzyciagniecie sie do gracza" : "&bodpychanie gracza")));
            player.closeInventory();
        }));
        inventoryContents.set(0, 4, ClickableItem.of(odpychanie.toItemStack(), e-> {
            u.setWedka("ODPYCHANIE");
            Util.sendTitle(player, "&8{o} &cMagiczna Wedka &8{o}");
            Util.sendSubTitle(player, "&8>> &7Tryb zmieniony na " + (u.getWedka().equals("PRZYCIAGANIE") ? "&6przyciaganie gracza do Ciebie" : (u.getWedka().equals("DOSKOK") ? "&aprzyciagniecie sie do gracza" : "&bodpychanie gracza")));
            player.closeInventory();
        }));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
