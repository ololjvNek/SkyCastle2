package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.enums.MemberType;
import pl.ololjvNek.skycastle.managers.UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Team {

    private String teamTag;
    private int points;
    private List<Member> memberList;
    private List<Player> invited;

    public Team(Player owner, String tag){
        teamTag = tag;
        points = 0;
        invited = new ArrayList<>();
        memberList = new ArrayList<>();
        memberList.add(new Member(owner, this, MemberType.LEADER));
        insert();
    }

    public Team(ResultSet rs) throws SQLException {
        teamTag = rs.getString("teamTag");
        points = 0;
        memberList = new ArrayList<>();
        invited = new ArrayList<>();
    }

    public boolean isInvited(Player player){
        return invited.contains(player);
    }

    public MemberType getMentionOfPlayer(Player player){
        for(Member member : memberList){
            if(member.getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString())){
                return member.getMemberType();
            }
        }
        return null;
    }

    public int getPointsCalculated(){
        points = 0;
        if(memberList.size() == 10){
            for(Member member : memberList){
                User u = UserManager.getUser(member.getUuid());
                points += (u.getPoints()/ memberList.size());
            }
        }
        return points;
    }

    public void insert(){
        Main.getStore().update("INSERT INTO `teams`(`id`, `teamTag`) VALUES (NULL, '" + getTeamTag() + "');");
    }
}
