package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.enums.MemberType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Data
public class Member {

    private UUID uuid;
    private String name, teamTag;
    private MemberType memberType;

    public Member(Player player, Team team, MemberType memberType){
        uuid = player.getUniqueId();
        name = player.getName();
        teamTag = team.getTeamTag();
        this.memberType = memberType;
        insert();
    }

    public Member(ResultSet rs) throws SQLException {
        uuid = UUID.fromString(rs.getString("uuid"));
        name = rs.getString("name");
        teamTag = rs.getString("teamTag");
        memberType = MemberType.valueOf(rs.getString("mention"));
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public void insert(){
        Main.getStore().update("INSERT INTO `members`(`id`, `uuid`, `name`, `teamTag`, `mention`) VALUES (NULL, '" + getUuid().toString() + "', '" + getName() + "', '" + getTeamTag() + "', '" + getMemberType().toString() + "');");
    }

    public void update(){
        Main.getStore().update("UPDATE `members` SET `mention` = '" + getMemberType().toString() + "';");
    }
}
