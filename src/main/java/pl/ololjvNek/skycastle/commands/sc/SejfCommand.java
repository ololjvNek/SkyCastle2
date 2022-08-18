package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.providers.SejfProvider;
import pl.ololjvNek.skycastle.utils.Util;

public class SejfCommand extends PlayerCommand {

    public SejfCommand() {
        super("safe", "safe", "safe", "core.safe");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        User u = UserManager.getUser(p);
        if(u.getSkyCastle() != null){
            SkyCastle skyCastle = u.getSkyCastle();
            if(skyCastle.getTeamIn(p).equals("RED")){
                if(p.getLocation().distance(skyCastle.getRedSpawn()) < 30){
                    SejfProvider.INVENTORY.open(p);
                }else{
                    Util.sendTitle(p, "&4ERROR");
                    Util.sendSubTitle(p, Main.getMessagesConfig().getConfig().getString("messages.errors.safe.islandterrain"));
                }
            }else if(skyCastle.getTeamIn(p).equals("BLUE")){
                if(p.getLocation().distance(skyCastle.getBlueSpawn()) < 30){
                    SejfProvider.INVENTORY.open(p);
                }else{
                    Util.sendTitle(p, "&4ERROR");
                    Util.sendSubTitle(p, Main.getMessagesConfig().getConfig().getString("messages.errors.safe.islandterrain"));
                }
            }
        }
        return false;
    }
}
