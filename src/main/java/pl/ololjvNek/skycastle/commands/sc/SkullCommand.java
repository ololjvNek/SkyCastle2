package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.utils.ItemBuilder;

public class SkullCommand extends PlayerCommand {

    public SkullCommand() {
        super("skull", "skull", "skull", "core.*");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        p.getInventory().addItem(new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setSkullOwner(args[0]).toItemStack());
        return false;
    }
}
