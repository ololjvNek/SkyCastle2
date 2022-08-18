package pl.ololjvNek.skycastle.utils;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.SkyCastle;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ArenaStatus;
import pl.ololjvNek.skycastle.managers.UserManager;

public class BoardUtil {

    static String animationSkyCastle = "&6&lSKYCASTLE";

    public static void update(Player player){
        User u = UserManager.getUser(player);
        if(u.getSkyCastle() != null){
            SkyCastle skyCastle = u.getSkyCastle();
            if(skyCastle.getStatus() == ArenaStatus.WAITING){
                if(!Netherboard.instance().hasBoard(player)){
                    BPlayerBoard board = Netherboard.instance().createBoard(player, Util.fixColors(animationSkyCastle));
                    board.setAll(Util.fixColors("&0"), Util.fixColors("&aOczekiwanie na graczy"), Util.fixColors("&e" + skyCastle.getPlayers().size() + "&7/&c" + skyCastle.getMaxPlayers()), Util.fixColors("&2"), Util.fixColors("&cwww.dineron.net"));
                }else{
                    BPlayerBoard board = Netherboard.instance().getBoard(player);
                    board.setAll(Util.fixColors("&0"), Util.fixColors("&aOczekiwanie na graczy"), Util.fixColors("&e" + skyCastle.getPlayers().size() + "&7/&c" + skyCastle.getMaxPlayers()), Util.fixColors("&2"), Util.fixColors("&cwww.dineron.net"));
                }
            }else if(skyCastle.getStatus() == ArenaStatus.STARTING){
                if(!Netherboard.instance().hasBoard(player)){
                    BPlayerBoard board = Netherboard.instance().createBoard(player, Util.fixColors(animationSkyCastle));
                    board.setAll(Util.fixColors("&0"), Util.fixColors("&6Gra startuje"), Util.fixColors("&e" + skyCastle.getPlayers().size() + "&7/&c" + skyCastle.getMaxPlayers()), Util.fixColors("&7Czas: &c" + DataUtil.secondsToString(skyCastle.getStartTime())), Util.fixColors("&2"), Util.fixColors("&cwww.dineron.net"));
                }else{
                    BPlayerBoard board = Netherboard.instance().getBoard(player);
                    board.setAll(Util.fixColors("&0"), Util.fixColors("&6Gra startuje"), Util.fixColors("&e" + skyCastle.getPlayers().size() + "&7/&c" + skyCastle.getMaxPlayers()), Util.fixColors("&7Czas: &c" + DataUtil.secondsToString(skyCastle.getStartTime())), Util.fixColors("&2"), Util.fixColors("&cwww.dineron.net"));
                }
            }else{
                if(!Netherboard.instance().hasBoard(player)){
                    BPlayerBoard board = Netherboard.instance().createBoard(player, Util.fixColors(animationSkyCastle));
                    board.setAll(Util.fixColors("&0"), Util.fixColors("&9&l{o} &8- &7" + skyCastle.getBlueTeam().size()), Util.fixColors("&c&l{o} &8- &7" + skyCastle.getRedTeam().size()), Util.fixColors("&1"), Util.fixColors("&6Coins&8: &c" + u.getCoins()), Util.fixColors("&2"), Util.fixColors("&cdineron.net"));
                }else{
                    BPlayerBoard board = Netherboard.instance().getBoard(player);
                    board.setAll(Util.fixColors("&0"), Util.fixColors("&9&l{o} &8- &7" + skyCastle.getBlueTeam().size()), Util.fixColors("&c&l{o} &8- &7" + skyCastle.getRedTeam().size()), Util.fixColors("&1"), Util.fixColors("&6Coins&8: &c" + u.getCoins()), Util.fixColors("&2"), Util.fixColors("&cdineron.net"));
                }
            }
        }else{
            if(Netherboard.instance().hasBoard(player)){
                Netherboard.instance().deleteBoard(player);
            }
        }
    }
}
