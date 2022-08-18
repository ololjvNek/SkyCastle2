package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.data.VillagerShop;
import pl.ololjvNek.skycastle.managers.SkyCastleManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.managers.VillagerShopManager;
import pl.ololjvNek.skycastle.providers.VillagerShopProvider;
import pl.ololjvNek.skycastle.utils.ItemUtil;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.concurrent.ThreadLocalRandom;

public class DebugCommand extends PlayerCommand {

    public DebugCommand() {
        super("debug", "debug", "debug", "core.debug");
    }

    public boolean onCommand(Player p, String[] args) {
        User u = UserManager.getUser(p);
        u.addStars(1);
        p.getInventory().addItem(ItemUtil.debugStick);
        VillagerShopProvider.INVENTORY.open(p);
        return Util.sendMessage(p, "{PREFIX} &7Dodano gwiazdke");
    }
}
