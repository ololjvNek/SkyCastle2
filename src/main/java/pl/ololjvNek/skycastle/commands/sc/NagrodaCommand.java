package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.LPUtil;
import pl.ololjvNek.skycastle.utils.Util;

public class NagrodaCommand extends PlayerCommand {

    public NagrodaCommand() {
        super("nagroda", "nagroda", "nagroda", "core.default");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        User u = UserManager.getUser(p);
        if(args.length == 0){

        }
        if(args[0].equalsIgnoreCase("odbierz")){
            u.setNagroda(true);
            LPUtil.addPlayerGroup(p, "vip");
            return Util.sendMessage(p, "&c&lDZORDZ BOT &8>> &7Gratulacje! &6Otrzymales nagrode w postaci &eVIP &7na &b3 dni");
        }
        return false;
    }
}
