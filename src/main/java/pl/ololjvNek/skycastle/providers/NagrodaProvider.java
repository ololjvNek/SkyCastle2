package pl.ololjvNek.skycastle.providers;

import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.utils.ItemBuilder;
import pl.ololjvNek.skycastle.utils.Util;

public class NagrodaProvider implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .manager(Main.getInventoryManager())
            .id("Menu")
            .provider(new NagrodaProvider())
            .size(5, 9)
            .title(Util.fixColors("&cNagroda"))
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        ItemBuilder discord = new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner("");
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
