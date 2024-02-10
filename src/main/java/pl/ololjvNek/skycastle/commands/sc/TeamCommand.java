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
            return Util.sendMessage(p, "\n\n\n&8&m===============&8 ( &6Teams &8) &8&m===============" +
                    "\n&7&m==&8> &c/team create <tag> &8=|= &7Creating a team" +
                    "\n&7&m==&8> &c/team invite <player> &8=|= &7Inviting a player to the team" +
                    "\n&7&m==&8> &c/team join <tag> &8=|= &7Joining a team" +
                    "\n&7&m==&8> &c/team deputy <player> &8=|= &7Setting a team deputy" +
                    "\n&8&m===============&8 ( &2Information &8) &8&m===============" +
                    "\n&7&m==&8> &7A team is ranked only if it has &a10 &7members" +
                    "\n&7&m==&8> &7Creating a team is available from &e3 stars" +
                    "\n&7&m==&8> &eStars &7are earned by playing &aSkyCastle &7games" +
                    "\n&7&m==&8> &7They are counted based on your game statistics" +
                    "\n&8&m===============&8 ( &ewww.dineron.net &8) &8&m===============\n\n\n");

        }
        final User u = UserManager.getUser(p);
        if(args[0].equalsIgnoreCase("create")){
            if(args.length < 2){
                return Util.sendMessage(p, "&cCorrect usage: &a/team create <tag>");
            }
            if(u.getStars() >= 3 && u.getTeam() == null){
                TeamManager.createTeam(p, args[1].toUpperCase());
                u.setTeamTag(args[1].toUpperCase());
                u.setTeam(TeamManager.getTeam(args[1].toUpperCase()));
                return Util.sendMessage(Bukkit.getOnlinePlayers(), "&6&lTEAMS &8&m=||=&7 Player &a" + p.getName() + " &7created a team with tag &c" + args[1].toUpperCase());
            }else if(u.getStars() < 3){
                return Util.sendMessage(p, "&cYou don't have enough stars to create a team!");
            }else{
                return Util.sendMessage(p, "&cYou already have a team!");
            }
        }
        if(args[0].equalsIgnoreCase("invite")){
            if(args.length < 2){
                return Util.sendMessage(p, "&cCorrect usage: &a/team invite <player>");
            }
            if(u.getTeam() != null && u.getTeam().getMentionOfPlayer(p) == MemberType.LEADER){
                Player target = Bukkit.getPlayer(args[1]);
                if(target != null){
                    User targetUser = UserManager.getUser(target);
                    if(targetUser.getTeam() == null){
                        Util.sendMessage(target, "&6&lTEAMS &8&m=||=&7 Team &a" + u.getTeam().getTeamTag() + " &7invited you to join them\n&7Type /team join &a" + u.getTeam().getTeamTag() + " &7to accept the invitation");
                        Util.sendMessage(p, "&6&lTEAMS &8&m=||=&7 Sent an invitation to join the team to player &2" + target.getName());
                        u.getTeam().getInvited().add(target);
                        return true;
                    }else{
                        return Util.sendMessage(p, "&6&lTEAMS &8&m=||=&c This player already has a team!");
                    }
                }else{
                    return Util.sendMessage(p, "&6&lTEAMS &8&m=||=&c This player is offline!");
                }
            }else{
                return Util.sendMessage(p, "&6&lTEAMS &8&m=||=&c You are not a team leader!");
            }
        }
        if(args[0].equalsIgnoreCase("join")){
            if(args.length < 2){
                return Util.sendMessage(p, "&cCorrect usage: &a/team join <tag>");
            }
            if(u.getTeam() == null){
                Team team = TeamManager.getTeam(args[1].toUpperCase());
                if(team != null){
                    if(team.isInvited(p)){
                        team.getInvited().remove(p);
                        u.setTeam(team);
                        u.setTeamTag(team.getTeamTag());
                        return Util.sendMessage(Bukkit.getOnlinePlayers(), "&6&lTEAMS &8&m=||=&7 Player &a" + p.getName() + " &7joined the team with tag &c" + team.getTeamTag());
                    }else{
                        return Util.sendMessage(p, "&6&lTEAMS &8&m=||=&c You are not invited to this team!");
                    }
                }else{
                    return Util.sendMessage(p, "&6&lTEAMS &8&m=||=&c Such team does not exist!");
                }
            }else{
                return Util.sendMessage(p, "&6&lTEAMS &8&m=||=&c You already belong to a team!");
            }
        }
        return false;
    }
}
