package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.providers.WedkaProvider;
import pl.ololjvNek.skycastle.utils.Util;

public class WedkaCommand extends PlayerCommand {

    public WedkaCommand() {
        super("magicrod", "wedka", "wedka", "core.magicrod");
    }

    @Override
    public boolean onCommand(final Player p, String[] args) {
        final User u = UserManager.getUser(p);
        WedkaProvider.INVENTORY.open(p);
        return true;
    }
}
