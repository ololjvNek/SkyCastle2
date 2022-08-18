package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.Member;
import pl.ololjvNek.skycastle.data.Team;
import pl.ololjvNek.skycastle.enums.SavingType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class TeamManager {

    @Getter private static HashMap<String, Team> teams = new HashMap<>();
    @Getter private static HashMap<UUID, Member> members = new HashMap<>();

    public static void createTeam(Player player, String tag){
        Team team = new Team(player, tag);
        teams.put(team.getTeamTag(), team);
        RankingManager.getTeamRankings().add(team);
    }

    public static Team getTeam(String name){
        return teams.get(name);
    }

    public static Member getMember(UUID uuid){
        return members.get(uuid);
    }

    public static void loadMembers(){
        if(Main.getSavingType() == SavingType.FLAT){
            return;
        }
        ResultSet rs = Main.getStore().query("SELECT * FROM `members`");
        try{
            while(rs.next()){
                Member member = new Member(rs);
                members.put(member.getUuid(), member);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void loadTeams(){
        if(Main.getSavingType() == SavingType.FLAT){
            return;
        }
        ResultSet rs = Main.getStore().query("SELECT * FROM `teams`");
        try{
            while(rs.next()){
                Team team = new Team(rs);
                teams.put(team.getTeamTag(), team);
                RankingManager.getTeamRankings().add(team);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        RankingManager.sortTeamRankings();
    }
}
