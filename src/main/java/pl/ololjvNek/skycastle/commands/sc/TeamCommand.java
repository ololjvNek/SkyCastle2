package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.data.Team;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.MemberType;
import pl.ololjvNek.skycastle.managers.TeamManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.Util;

public class TeamCommand extends PlayerCommand {

    public TeamCommand() {
        super("team", "team", "team", "core.teams", "druzyna", "druzyny", "teams");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if(args.length < 1){
            return Util.sendMessage(p, "\n\n\n&8&m===============&8 ( &6Druzyny &8) &8&m===============" +
                    "\n&7&m==&8> &c/team stworz <tag> &8=|= &7Tworzenie druzyny" +
                    "\n&7&m==&8> &c/team zapros <gracz> &8=|= &7Zapraszanie gracza do druzyny" +
                    "\n&7&m==&8> &c/team dolacz <tag> &8=|= &7Dolaczanie do druzyny" +
                    "\n&7&m==&8> &c/team zastepca <gracz> &8=|= &7Ustawianie zastepcy druzyny" +
                    "\n&8&m===============&8 ( &2Informacje &8) &8&m===============" +
                    "\n&7&m==&8> &7Druzyna zalicza sie do rankingu dopiero od &a10 &7czlonkow" +
                    "\n&7&m==&8> &7Tworzenie druzyny jest dostepne od &e3 gwiazdek" +
                    "\n&7&m==&8> &eGwiazdki &7zdobywa sie poprzez granie gier &aSkyCastle" +
                    "\n&7&m==&8> &7Sa one liczone na podstawie twoich statystyk z gry" +
                    "\n&8&m===============&8 ( &ewww.dineron.net &8) &8&m===============\n\n\n");

        }
        final User u = UserManager.getUser(p);
        if(args[0].equalsIgnoreCase("stworz")){
            if(args.length < 2){
                return Util.sendMessage(p, "&cPoprawne uzycie: &a/team stworz <tag>");
            }
            if(u.getStars() >= 3 && u.getTeam() == null){
                TeamManager.createTeam(p, args[1].toUpperCase());
                u.setTeamTag(args[1].toUpperCase());
                u.setTeam(TeamManager.getTeam(args[1].toUpperCase()));
                return Util.sendMessage(Bukkit.getOnlinePlayers(), "&6&lDRUZYNY &8&m=||=&7 Gracz &a" + p.getName() + " &7stworzyl druzyne o tagu &c" + args[1].toUpperCase());
            }else if(u.getStars() < 3){
                return Util.sendMessage(p, "&cNie posiadasz wystarczajaco gwiazdek by stworzyc druzyne!");
            }else{
                return Util.sendMessage(p, "&cPosiadasz juz swoja druzyne!");
            }
        }
        if(args[0].equalsIgnoreCase("zapros")){
            if(args.length < 2){
                return Util.sendMessage(p, "&cPoprawne uzycie: &a/team zapros <gracz>");
            }
            if(u.getTeam() != null && u.getTeam().getMentionOfPlayer(p) == MemberType.LEADER){
                Player target = Bukkit.getPlayer(args[1]);
                if(target != null){
                    User targetUser = UserManager.getUser(target);
                    if(targetUser.getTeam() == null){
                        Util.sendMessage(target, "&6&lDRUZYNY &8&m=||=&7 Druzyna &a" + u.getTeam().getTeamTag() + " &7zaprosila cie abys dolaczyl do nich\n&7Wpisz /team dolacz &a" + u.getTeam().getTeamTag() + " &7aby dolaczyc");
                        Util.sendMessage(p, "&6&lDRUZYNY &8&m=||=&7 Wyslano zaproszenie do druzyny graczowi &2" + target.getName());
                        u.getTeam().getInvited().add(target);
                        return true;
                    }else{
                        return Util.sendMessage(p, "&6&lDRUZYNY &8&m=||=&c Ten gracz posiada swoja druzyne!");
                    }
                }else{
                    return Util.sendMessage(p, "&6&lDRUZYNY &8&m=||=&c Ten gracz jest Offline!");
                }
            }else{
                return Util.sendMessage(p, "&6&lDRUZYNY &8&m=||=&c Nie posiadasz lidera gildii!");
            }
        }
        if(args[0].equalsIgnoreCase("dolacz")){
            if(args.length < 2){
                return Util.sendMessage(p, "&cPoprawne uzycie: &a/team dolacz <tag>");
            }
            if(u.getTeam() == null){
                Team team = TeamManager.getTeam(args[1].toUpperCase());
                if(team != null){
                    if(team.isInvited(p)){
                        team.getInvited().remove(p);
                        u.setTeam(team);
                        u.setTeamTag(team.getTeamTag());
                        return Util.sendMessage(Bukkit.getOnlinePlayers(), "&6&lDRUZYNY &8&m=||=&7 Gracz &a" + p.getName() + " &7dolaczyl do druzyny o tagu &c" + team.getTeamTag());
                    }else{
                        return Util.sendMessage(p, "&6&lDRUZYNY &8&m=||=&c Nie jestes zaproszony do tej druzyny!");
                    }
                }else{
                    return Util.sendMessage(p, "&6&lDRUZYNY &8&m=||=&c Taka druzyna nie istnieje!");
                }
            }else{
                return Util.sendMessage(p, "&6&lDRUZYNY &8&m=||=&c Nalezysz juz do jakiejs druzyny!");
            }
        }
        return false;
    }
}
